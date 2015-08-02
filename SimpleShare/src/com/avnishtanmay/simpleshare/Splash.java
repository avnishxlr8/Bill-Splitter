package com.avnishtanmay.simpleshare;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class Splash extends Activity {
   
  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		final Splash s = this;
	    Thread timer = new Thread(){
	    	
	    	
			public void run(){
	    		try{
	    			  sleep(2000);
	    			
	    		}catch(InterruptedException e){
	    			e.printStackTrace();
	    		}
	    		finally{
	    			finish();//it is called such that on pressing back button splash is not displayed again
	    			
	    			Intent intent = new Intent(Splash.this,SimpleShareActivity.class);
	    			startActivity(intent);
	    		}
	    	}
	    };
	     timer.start();
	
	 
   }
}

