package io.vertx.Symantec_VM;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;

public class MySQL {
	public String query0 = "INSERT INTO data VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	public String query1 = "TRUNCATE data";
	public String query2 = "INSERT INTO mic VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	public void add(JDBCClient client, SensorBean bean) {
		JsonArray params = new JsonArray().add(bean.getDigital_temp())
				.add(bean.getId()).add(bean.getLight())
				.add(bean.getTimestamp()).add(bean.getAcc_z())
				.add(bean.getAcc_y()).add(bean.getAcc_x())
				.add(bean.getMotion()).add(bean.getPressure())
				.add(bean.getHumid());
		//System.out.println("JsonArr: " + params);
		client.getConnection(conn -> {
			if (conn.failed()) {
				System.err.println("Database Connection Failed.");
				System.err.println(conn.cause().getMessage());
				return;
			}
			//System.out.println("Database Connection Succeed.");
			SQLConnection connection = conn.result();
			connection.updateWithParams(query0,	params,	res -> {
				if (res.succeeded()) {
					UpdateResult updateResult = res.result();
					//System.out.println("No. of rows updated: " + updateResult.getUpdated());
				} else {
					System.err.println("Insertion failed.");
				}
			});
			connection.close();
		});
	}
	
	public void clear(JDBCClient client) {
		client.getConnection(conn -> {
			if(conn.failed()) {
				System.err.println("Database Connection Failed.");
				System.err.println(conn.cause().getMessage());
				return;
			}
			System.err.println("Database Connection Succeed.");
			SQLConnection connection = conn.result();
			connection.update(query1, res -> {
				if (res.succeeded()) {
					UpdateResult updateResult = res.result();
					System.err.println("No. of rows updated: "
							+ updateResult.getUpdated());
				} else {
					System.err.println("Truncation failed.");
				}
			});
			connection.close();
		});
	}
	
	public void mic(JDBCClient client, int[] data) {
		JsonArray params = new JsonArray();
		for(int i : data)
			params.add(i);
		client.getConnection(conn -> {
			if (conn.failed()) {
				System.err.println("Database Connection Failed.");
				System.err.println(conn.cause().getMessage());
				return;
			}
			//System.out.println("Database Connection Succeed.");
			SQLConnection connection = conn.result();
			connection.updateWithParams(query2,	params,	res -> {
				if (res.succeeded()) {
					UpdateResult updateResult = res.result();
					//System.out.println("No. of rows updated: " + updateResult.getUpdated());
				} else {
					System.err.println("Insertion failed.");
				}
			});
			connection.close();
		});
	}
}
