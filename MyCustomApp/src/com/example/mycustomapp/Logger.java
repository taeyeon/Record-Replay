package com.example.mycustomapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.os.Environment;
import android.util.Log;

public class Logger {
	static final File recordFile=new File(Environment.getExternalStorageDirectory()+"/"+"recFile.txt");
	ConcurrentLinkedQueue<Structure> queue=new ConcurrentLinkedQueue<Structure>();
	int uId;

	public Logger(int uId){
		this.uId=uId;
		Log.v("LOGGER",recordFile.getAbsolutePath());
		try{
			PrintWriter writer = new PrintWriter(recordFile);
			writer.print("");
			writer.close();
		}catch(IOException e){
			
		}
	}

	public void log(Structure struct){
		queue.offer(struct);
		if(queue.size()>1000){
			Thread thread=new Thread(new Runnable(){

				@Override
				public void run() {
					cleanup();
					
				}
				
			});
		}
	}
	
	public void cleanup(){
		while(!queue.isEmpty()){
			FileWriter outToFile=null;
			BufferedWriter writer=null;
			try {
				outToFile=new FileWriter(Logger.recordFile,true);
				writer=new BufferedWriter(outToFile);
			} catch (IOException e) {
				Log.e("LOGGER", e.toString());
			}

			while(!queue.isEmpty()){
				Structure ob=queue.poll();
				StringBuilder tuple=new StringBuilder("");
				if(ob.viewId==0){
					tuple.append(ob.logId+" "+ob.MethodName+" "+ob.timeStamp+" "+ob.pid+" "+ob.tid+" "+ob.eventFloat[0]+" "+ob.eventFloat[1]+" "+
							ob.eventFloat[2]+" "+ob.sensor+" "+ob.eventAccuracy+"\n");
					try {
						writer.append(tuple.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else{
					tuple.append(ob.logId+" "+ob.MethodName+" "+ob.timeStamp+" "+ob.pid+" "+ob.tid+ob.viewId+"\n");
					try {
						writer.append(tuple.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			try{
				writer.close();
			}catch(IOException e){
				Log.e("LOGGER",e.toString());
			}
		}
	}

}


