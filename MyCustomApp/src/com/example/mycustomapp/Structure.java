package com.example.mycustomapp;

public class Structure{
	long timeStamp;
	int pid;
	int tid;
	String MethodName;
	float[] eventFloat;
	int eventAccuracy;
	int viewId;
	int logId;
	static int idGen=0;
	int sensor;
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getMethodName() {
		return MethodName;
	}
	public void setMethodName(String methodName) {
		MethodName = methodName;
	}
	public float[] getEventFloat() {
		return eventFloat;
	}
	public void setEventFloat(float[] eventFloat) {
		this.eventFloat = eventFloat;
	}
	public int getEventAccuracy() {
		return eventAccuracy;
	}
	public void setEventAccuracy(int eventAccuracy) {
		this.eventAccuracy = eventAccuracy;
	}
	public int getViewId() {
		return viewId;
	}
	public void setViewId(int viewId) {
		this.viewId = viewId;
	}
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	
	public int getSensor() {
		return sensor;
	}
	public void setSensor(int sensor) {
		this.sensor = sensor;
	}
	
	public static synchronized int genId(){
		return idGen++;
	}
}