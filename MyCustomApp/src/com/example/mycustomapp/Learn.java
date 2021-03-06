package com.example.mycustomapp;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Learn extends Activity implements SensorEventListener {
	
	private SensorManager sensorManager;
	private long lastUpdate;
	public static TextView tv;
	private boolean color=false;
	Logger logger=new Logger(android.os.Process.myUid());
	int back=0;
	static int click=0;
	Activity present=this;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        tv=(TextView)findViewById(R.id.textView1);
        tv.setText("");
        
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Structure struct=new Structure();
				struct.setLogId(Structure.genId());
				struct.setViewId(v.getId());
				struct.setTimeStamp(System.currentTimeMillis());
				struct.setTid(android.os.Process.myTid());
				struct.setPid(android.os.Process.myPid());
				struct.setMethodName("setOnClickListener");
				logger.log(struct);
				tv.append("click No."+Integer.toString(click++));
				
			}
		});
        
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
        
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.learn, menu);
        return true;
    }


    public void onSensorChanged(SensorEvent event) {
    	Structure struct=new Structure();
    	struct.setLogId(Structure.genId());
    	struct.setTimeStamp(System.currentTimeMillis());
		struct.setTid(android.os.Process.myTid());
		struct.setPid(android.os.Process.myPid());
		struct.setEventFloat(event.values);
		struct.setMethodName("onSensorChanged");
		struct.setEventAccuracy(event.accuracy);
		struct.setSensor(event.sensor.getType());
		logger.log(struct);
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
          getAccelerometer(event);
        }
      }

      private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
            / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot >= 2) //
        {
          if (actualTime - lastUpdate < 200) {
            return;
          }
          lastUpdate = actualTime;
          Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
              .show();
          if (color) {
            tv.setBackgroundColor(Color.GREEN);

          } else {
            tv.setBackgroundColor(Color.RED);
          }
          color = !color;
        }
      }

      @Override
      public void onAccuracyChanged(Sensor sensor, int accuracy) {

      }

      @Override
      protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL);
      }

      @Override
      protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
      } 
      
      @Override
      public void onBackPressed(){
    	  if(back==0){
    		  sensorManager.unregisterListener(this);
    		  logger.cleanup();
    		  back++;
    	  }
    	  else{
    		  sensorManager.unregisterListener(this);
    		  super.onBackPressed();
    	  }
      }
      
    
}


