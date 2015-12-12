package io.vertx.Symantec_VM;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

/**
 * Hello world!
 *
 */
public class Server extends AbstractVerticle {
	private JDBCClient client;
	@Override
	public void start() {
		HttpServer server = vertx.createHttpServer();
		client = JDBCClient.createShared(vertx, new JsonObject()
		.put("url", "jdbc:mysql://localhost:3306/test")
        .put("driver_class", "com.mysql.jdbc.Driver")
        .put("user", "root")
        .put("password", "root")
        .put("initial_pool_size", 30)
        .put("max_pool_size", 80));
		
		server.requestHandler(request -> {
			request.response().headers().set("Content-Type", "text/html");
			String pathName = request.path();
			//System.out.println(pathName);
			
			if(pathName.equals("/")) {
				request.response().end("<h>Hello World!</h>");
			}
			else if(pathName.equals("/add")) {
				invokeAdd(request);
			}
			else if(pathName.equals("/clear")) {
				invokeClean(request);
			}
			else if(pathName.equals("/microphone")) {
				invokeMicrophone(request);
			}
			else if(pathName.equals("/show")) {
				invokeShow(request);
			}
			else {
				System.out.println("Invalid path!");
				request.response().end();
			}
		});
		server.listen(8080);
	}
	
	private void invokeAdd(HttpServerRequest req) {
		System.err.println("Path was: " + req.path());
		
		req.bodyHandler(totalBuffer -> {
			JsonArray arr = new JsonArray(totalBuffer.toString());
			//JsonObject body = new JsonObject(totalBuffer.toString());
			//System.out.println(arr);
			
			for(int i=0;i<arr.size();i++) {
				JsonObject obj= arr.getJsonObject(i);
			
				float p1 = obj.getFloat("digital_temp");
				String p2 = obj.getString("id");
				int p3 = obj.getInteger("light");
				int p4 = obj.getInteger("timestamp");
				int p5 = obj.getInteger("acc_z");
				int p6 = obj.getInteger("acc_y");
				int p7 = obj.getInteger("acc_x");
				int p8 = obj.getInteger("motion");
				int p9 = obj.getInteger("pressure");
				int p10 = obj.getInteger("humid");
				SensorBean bean = new SensorBean(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
				//System.out.println("Bean created.");
				new MySQL().add(client, bean);
			}
		});
		req.response().end();
	}
	
	private void invokeClean(HttpServerRequest req) {
		System.err.println("Path was: " + req.path());
		req.bodyHandler(totalBuffer -> {
			new MySQL().clear(client);
		});
		req.response().end();
	}
	
	private void invokeMicrophone(HttpServerRequest req) {
		System.err.println("Path was: " + req.path());
		MySQL mysql = new MySQL();
		req.bodyHandler(totalBuffer -> {
			//JsonArray arr = new JsonArray(totalBuffer.toString());
			//System.out.println(arr);
			//System.out.println("Content" + totalBuffer.toString());
			String str = totalBuffer.toString();
			String sstr = str.substring(1, str.length() - 1);
			//System.out.println(sstr);
			byte[] decoded = Base64.getDecoder().decode(sstr.getBytes());
			
			int[] data = new int[20];
			int num_set = decoded.length/2/20;
			for(int i=0;i<num_set;i++) {
				Arrays.fill(data, 0);
				for(int j=0;j<20;j++) {
					data[j] = (0x00ff &  decoded[(i*40 + j*2)]) + (0xff00 & (decoded[(i*40 + j*2 + 1)] << 8));
					System.out.print(data[j] + " ");
				}
				System.out.println();
				mysql.mic(client, data);
			}
		});
		req.response().end();
	}
	
	private void invokeShow(HttpServerRequest req) {
		System.err.println("Path was: " + req.path());
		
		client.getConnection(conn -> {
			if (conn.failed()) {
				System.err.println("Database Connection Failed.");
				System.err.println(conn.cause().getMessage());
				return;
			}
			
			SQLConnection connection = conn.result();
			connection.query("select * from data", res -> {
				if (res.succeeded()) {
					StringBuffer sb = new StringBuffer("<table boarder=\"1\">");
					sb.append("<tr><td>Digit_temp</td><td>id</td><td>light</td><td>timestamp"
							+ "</td><td>acc_z</td><td>acc_y</td><td>acc_x</td><td>motion</td><td>"
							+ "pressure</td><td>humid</td></tr>");
					
					ResultSet resultSet = res.result();
					List<JsonArray> results = resultSet.getResults();
					for(JsonArray row  : results) {
						sb.append(new SensorBean(row.getFloat(0),
											row.getString(1),
											row.getInteger(2),
											row.getInteger(3),
											row.getInteger(4),
											row.getInteger(5),
											row.getInteger(6),
											row.getInteger(7),
											row.getInteger(8),
											row.getInteger(9)).toString());
					}
					sb.append("</table>");
					//System.out.println(sb.toString());
					req.response().end(sb.toString());
				} else {
					System.err.println("Insertion failed.");
				}
			});
			connection.close();
		});
	}
}
