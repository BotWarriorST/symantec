package io.vertx.Symantec_VM;

/**
 * @author simbatien
 *
 */
public class SensorBean {
	private float digital_temp;
	private String id;
	private int light;
	private int timestamp;
	private int acc_z;
	private int acc_y;
	private int acc_x;
	private int motion;
	private int pressure;
	private int humid;
	
	public SensorBean(float temp, String id, int light, int time, int z, int y, int x, int motion, int pressure, int humid) {
		this.digital_temp = temp;
		this.id = id;
		this.light = light;
		this.timestamp = time;
		this.acc_z = z;
		this.acc_y = y;
		this.acc_x = x;
		this.motion = motion;
		this.pressure = pressure;
		this.humid = humid;
	}
	
	public float getDigital_temp() {
		return digital_temp;
	}
	public void setDigital_temp(float digital_temp) {
		this.digital_temp = digital_temp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLight() {
		return light;
	}
	public void setLight(int light) {
		this.light = light;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	public int getAcc_z() {
		return acc_z;
	}
	public void setAcc_z(int acc_z) {
		this.acc_z = acc_z;
	}
	public int getAcc_y() {
		return acc_y;
	}
	public void setAcc_y(int acc_y) {
		this.acc_y = acc_y;
	}
	public int getAcc_x() {
		return acc_x;
	}
	public void setAcc_x(int acc_x) {
		this.acc_x = acc_x;
	}
	public int getMotion() {
		return motion;
	}
	public void setMotion(int motion) {
		this.motion = motion;
	}
	public int getPressure() {
		return pressure;
	}
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	public int getHumid() {
		return humid;
	}
	public void setHumid(int humid) {
		this.humid = humid;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("<tr>");
		sb.append("<td>" + this.getDigital_temp() + "</td>");
		sb.append("<td>" + this.getId() + "</td>");
		sb.append("<td>" + this.getLight() + "</td>");
		sb.append("<td>" + this.getTimestamp() + "</td>");
		sb.append("<td>" + this.getAcc_z() + "</td>");
		sb.append("<td>" + this.getAcc_y() + "</td>");
		sb.append("<td>" + this.getAcc_x() + "</td>");
		sb.append("<td>" + this.getMotion() + "</td>");
		sb.append("<td>" + this.getPressure() + "</td>");
		sb.append("<td>" + this.getHumid() + "</td></tr>");
		return sb.toString();
	}
}
