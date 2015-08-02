package com.avnishtanmay.simpleshare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class helpActivity extends Activity {
  
	Button back;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.help);
	       // getWindow().getDecorView().setBackgroundColor(Color.WHITE);
	        back = (Button)findViewById(R.id.Back);
	        back.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(helpActivity.this,SimpleShareActivity.class);
			    	startActivity(intent);	
				}
			});
	 }
}
