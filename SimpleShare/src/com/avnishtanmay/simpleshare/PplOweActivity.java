package com.avnishtanmay.simpleshare;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PplOweActivity extends Activity{
 
	Button btnSubmit,toStart,NameHelp;
	TextView headingTv;
	Spinner spinner1,spinner2;
	//String r1 = "",r2 = "",r3="",r4 = "";
	String name[],amtgive[],amtwhom[],isdone[];
	
	String specname[]=null;
	String borrower,giver,amount;
	Context context=this;
	
	
	
	//ListView listView1;
	
	//Cursor c;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pplowe);
		
		boolean isNull = getInfo();
		if(!isNull){
			initialize();
	        setSpinner1();      
	}
		
		else
		{
		    noRecords();
		}
	}
	
	
	
	public void initialize(){
		btnSubmit = (Button)findViewById(R.id.submitbtn);
		spinner1 = (Spinner)findViewById(R.id.spinner1);
		spinner2 = (Spinner)findViewById(R.id.spinner2);
		toStart=(Button)findViewById(R.id.toStfrmOwe);
		NameHelp=(Button)findViewById(R.id.NameHelp);
        //listView1 = (ListView) findViewById(R.id.listView1);
		
	}
	
	
	
	public void noRecords(){
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		 
		// set title
		alertDialogBuilder.setTitle("Press OK");

		// set dialog message
		alertDialogBuilder
			.setMessage("No Records Found")
			.setCancelable(false)
			.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					Intent i =new Intent(getApplicationContext(),SimpleShareActivity.class);
			    	startActivity(i);
				}
			  });

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
	}
	
	

	public void setSpinner1(){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, amtwhom);
    //display the list using a spinner
   spinner1.setAdapter(adapter);

   spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

	public void onItemSelected(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
		// TODO Auto-generated method stub
		String selectedFromList =(String) (spinner1.getItemAtPosition(myItemInt));
		giver = selectedFromList;
        //Toast.makeText(getApplicationContext(), selectedFromList+"", Toast.LENGTH_SHORT).show();
        
        PplOwe info = new PplOwe(getApplicationContext());
		  info.open();
          specname = info.getSpecific(selectedFromList);
       // for(int j=0;j<specname.length;j++)
			//Toast.makeText(getApplicationContext(), specname[j]+"", Toast.LENGTH_SHORT).show();
        info.close();
		setSpinner2();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	     }
    });
		
	}
	
	
	
	public void setSpinner2(){
		    
	       ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
	               android.R.layout.simple_spinner_dropdown_item, specname);
	       spinner2.setAdapter(adapter2);
	       
	       
	       spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

	   		public void onItemSelected(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
	   			// TODO Auto-generated method stub
	   			String selectedFromList =(String) (spinner2.getItemAtPosition(myItemInt));
	   			borrower = selectedFromList;
	               //Toast.makeText(getApplicationContext(), selectedFromList+"", Toast.LENGTH_SHORT).show();
	               
	               PplOwe info = new PplOwe(getApplicationContext());
	   			   info.open();
	               amount = info.getTuple(selectedFromList);
	              
	   				//Toast.makeText(getApplicationContext(), amount+"", Toast.LENGTH_SHORT).show();
	                
	               info.close();
	   				
	               
	   		}

	   		public void onNothingSelected(AdapterView<?> arg0) {
	   			// TODO Auto-generated method stub
	   			
	   		     }
	           });
	   }
	
	
	public void nameHelp(View v){
		
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog);
		dialog.setTitle("Help");

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.Message);
		text.setText("In case your name not present in the first list,\npeople do not owe you any Money..!!!");
		//ImageView image = (ImageView) dialog.findViewById(R.id.star);
		//image.setImageResource(R.drawable.ic_launcher);

		Button dialogButton = (Button) dialog.findViewById(R.id.ButtoninBox);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				 
			}
		});

		dialog.show();
	}

	
	public void onClickInfo(View v) {
		// TODO Auto-generated method stub
				
         final String msg = borrower+" owes an amount of "+amount+" to "+giver;	
		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		 
			// set title
			alertDialogBuilder.setTitle("Transaction Info");

			
			// set dialog message
			alertDialogBuilder
				.setMessage(giver+" has to take Rs."+amount+" from "+borrower)
				.setCancelable(false)
				.setPositiveButton("Whatsapp "+borrower+"",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						 /* Intent smsIntent = new Intent(Intent.ACTION_VIEW);
					      smsIntent.setData(Uri.parse("smsto:"));
					      smsIntent.setType("vnd.android-dir/mms-sms");
					      smsIntent.putExtra("address"  , new String (borrower+""));
					      smsIntent.putExtra("sms_body"  , borrower+" owes Rs."+amount+" to "+giver);
					      startActivity(smsIntent);*/
						try{
						Intent sendIntent = new Intent();
						sendIntent.setAction(Intent.ACTION_SEND);
						sendIntent.putExtra(Intent.EXTRA_TEXT, borrower+" owes Rs."+amount+" to "+giver);
						//sendIntent.putExtra("address"  , new String (borrower+""));
					   // sendIntent.putExtra("sms_body"  , borrower+" owes Rs."+amount+" to "+giver);
						sendIntent.setType("text/plain");
						sendIntent.setPackage("com.whatsapp");
						startActivity(sendIntent);
						} catch (Exception e) {
					        Toast.makeText(getApplicationContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
			                .show();
					        }
					}
				  })
				  .setNegativeButton("Set Status To Paid",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						PplOwe info = new PplOwe(getApplicationContext());
						info.open();
					    info.delete(borrower);
					    amtwhom = info.getData(3);
					    if(amtwhom.length!=0){
					    setSpinner1();//updates Spinner dynamically
					    }
					    else{
					    	noRecords();
					    }
						info.close();
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "Status Changed", Toast.LENGTH_SHORT).show();
					}
				  })
				  .setNeutralButton("Dismiss",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.dismiss();
					}
				  });

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
	}
		

	
	
	  public boolean getInfo(){
		
		try{
			PplOwe info = new PplOwe(this);
			info.open();
			
			name = info.getData(1);
			amtgive = info.getData(2);
			amtwhom = info.getData(3); 
			isdone = info.getData(4); 
			
			info.close();
		  if(name.length==0 || amtgive.length==0 || amtwhom.length==0 || isdone.length==0){
	
			
						return true;
		   }
           
			}catch(Exception e){
				System.out.println("error in pplowe activity"+e);
			}
		return false;
		}
	
	  
	  
	  
	  public void ToStart(View v)
	    {
	    	Intent intent = new Intent(this, SimpleShareActivity.class);
	        startActivity(intent);
	    }
  
		
}
	
	
