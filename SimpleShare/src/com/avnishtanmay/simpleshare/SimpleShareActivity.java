package com.avnishtanmay.simpleshare;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;



public class SimpleShareActivity extends Activity {
	Button start,check,helpBtn;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        start=(Button)findViewById(R.id.start);
        check = (Button)findViewById(R.id.check);
        helpBtn = (Button)findViewById(R.id.helpBtn);
        
        helpBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SimpleShareActivity.this,helpActivity.class);
		    	startActivity(intent);
			}
		});
      
    }
    
    public void toCal(View v)
    {
    	Intent intent = new Intent(this, InputNameAmount.class);
        startActivity(intent);
    	
    }
    
    public void check(View v){
    	
    	Intent intent = new Intent(this,PplOweActivity.class);
    	startActivity(intent);
    }
    
   /* public void help(View v){
    	
    	Intent intent = new Intent(this,helpActivity.class);
    	startActivity(intent);
    	
    }*/
    
 public void about(View v){
    	
    	final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.about);
		dialog.setTitle("About");

		// set the custom dialog components - text, image and button
		//TextView text = (TextView)dialog.findViewById(R.id.);
		//ImageView image = (ImageView) dialog.findViewById(R.id.star);
		//image.setImageResource(R.drawable.ic_launcher);

		Button dialogButton = (Button) dialog.findViewById(R.id.abtdismissbtn);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				 
			}
		});

		dialog.show();
    	
    }
}