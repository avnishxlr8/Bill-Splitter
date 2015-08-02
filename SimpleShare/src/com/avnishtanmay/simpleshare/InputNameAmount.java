package com.avnishtanmay.simpleshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class InputNameAmount extends Activity{
	Button btncalc;
	EditText m1,m2,m3,m4,m5,a1,a2,a3,a4,a5,billamt;
	int n,total=0,bill,req,toPay,rem;
	static int notpay=0;
	double init;
	int[] have,extra,totalgiven;
	int[][] amtgiven;
	boolean[] canpay;
	boolean[][] givenTo;
	boolean noProcess=false;
	String names[];

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nameamtnew);
        
        m1=(EditText)findViewById(R.id.name1);
        m2=(EditText)findViewById(R.id.name2);
        m3=(EditText)findViewById(R.id.name3);
        m4=(EditText)findViewById(R.id.name4);
        m5=(EditText)findViewById(R.id.name5);
        
        
        a1=(EditText)findViewById(R.id.amt1);
        a2=(EditText)findViewById(R.id.amt2);
        a3=(EditText)findViewById(R.id.amt3);
        a4=(EditText)findViewById(R.id.amt4);
        a5=(EditText)findViewById(R.id.amt5);
        
       billamt=(EditText)findViewById(R.id.bill);
       
       btncalc = (Button)findViewById(R.id.button1);
    }
	
	
	public void calc(View v)
	{
	
		
		names=new String[5];
		names[0]=m1.getText().toString();
		names[1]=m2.getText().toString();
		names[2]=m3.getText().toString();
		names[3]=m4.getText().toString();
		names[4]=m5.getText().toString();
		
		String s[]=new String[5];
		 s[0]=a1.getText().toString();
		 s[1]=a2.getText().toString();
		 s[2]=a3.getText().toString();
		 s[3]=a4.getText().toString();
		 s[4]=a5.getText().toString();
		
		n=0;
		boolean err=false;
		int empty[]=new int[5];
		int k=0;
		for(int i=0;i<5;i++)
		{
			if(!names[i].equals("") && !s[i].equals(""))
				n++;
			else if(!names[i].equals("") && s[i].equals(""))
			{
				
				Toast.makeText(getApplicationContext(), "Enter amt for "+names[i], Toast.LENGTH_SHORT).show();
				err=true;
			}
			else if(names[i].equals("") && !s[i].equals(""))
			{
				//Toast.makeText(getApplicationContext(), s[i].length(), Toast.LENGTH_SHORT).show();
				System.out.println(s[i].length());
				Toast.makeText(getApplicationContext(), "Enter name for member "+(i+1), Toast.LENGTH_SHORT).show();
				err=true;
			}
			else
			{
				empty[k++]=i;
			}
			
		}
		if(!err)
		{
			System.out.println("n="+n);
			System.out.println("k="+k);
			for(int i=0;i<=k-1;i++)
			{
				if(empty[i]<=n-1){
					System.out.println("empty.field="+empty[i]);
					Toast.makeText(getApplicationContext(), "No entry done for member "+(empty[i]+1), Toast.LENGTH_SHORT).show();
					err=true;
				}
			}
			if(k-1==4)
			{
				Toast.makeText(getApplicationContext(), "No data entered", Toast.LENGTH_SHORT).show();
				err=true;
			}
		}
		
	
		
		if(!err)
		{
			 
				String init=billamt.getText().toString();//input as double due to using Math.ceil()
				if(!init.equals("")){
					bill=Integer.parseInt(init);
					System.out.println("Bill value="+bill);
					//bill=(int)Math.ceil(init);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Bill amount not proper or not present", Toast.LENGTH_SHORT).show();
					err=true;
				}
		}
			
			
		
		
		if(!err)
		
		{
		have=new int[5];
		try{
		if(0<n) have[0]=Integer.parseInt(a1.getText().toString());
		if(1<n) have[1]=Integer.parseInt(a2.getText().toString());
		if(2<n) have[2]=Integer.parseInt(a3.getText().toString());
		if(3<n) have[3]=Integer.parseInt(a4.getText().toString());
		if(4<n) have[4]=Integer.parseInt(a5.getText().toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		Intent i=new Intent(this, Process.class);
		i.putExtra("namelist", names);
		i.putExtra("moneylist", have);
		i.putExtra("no", n);
		i.putExtra("billval", bill);
		startActivity(i);
	}
		
		
		
	}
	
	
	
	
	
}


