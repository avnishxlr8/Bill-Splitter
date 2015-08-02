package com.avnishtanmay.simpleshare;


import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Process extends Activity {
	Context context=this;
	Button camB,ToStart,ToTr;
	//ImageView campic;
	int n,total=0,bill,req,toPay,rem;
	static int notpay=0;
	double init;
	int[] have,extra,totalgiven;
	int[][] amtgiven;
	boolean[] canpay;
	boolean[][] givenTo;
	boolean noProcess=false;
	String names[];
	String finalOp="";
	TextView op;
	String temp="";
	static int TAKE_PIC =1;
	Uri outPutfileUri;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processop);
        op=(TextView)findViewById(R.id.textView1);
        camB=(Button)findViewById(R.id.camBtn);
        ToStart=(Button)findViewById(R.id.ToStart);
        ToTr=(Button)findViewById(R.id.ToCheck);
        
       // campic=(ImageView)findViewById(R.id.camPic);
        Intent retr=getIntent();
        n=retr.getIntExtra("no", 1);
        bill=retr.getIntExtra("billval", 0);
        System.out.println(n);
        System.out.println(bill);
        
        names=retr.getStringArrayExtra("namelist");
       
        
        have=retr.getIntArrayExtra("moneylist");
        for(int i=0;i<n;i++)
        {
        	System.out.println(names[i]);
        	System.out.println(have[i]);
        	//dataInsert(names[j],0,names[i]);
        }
       // op.setText(""+n);
        
        
        doProcess();//processing Of Share
		if(notpay==0)					//if able to pay
			output();					//display share and owed
		else
			finalOp=finalOp+"Not enough money...Time to Wash dishes!!!!!"; //else not enough money
	
		op.setText(finalOp);
		System.out.println(finalOp);
		notpay=0;
		
		
		camB.setOnClickListener(new View.OnClickListener() {
	           public void onClick(View v) {
	        	   Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    		   File file = new File(Environment.getExternalStorageDirectory()+ "/DCIM",
	    				"SimpleShare.jpg");
	    				outPutfileUri = Uri.fromFile(file);
	    				intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
	    				startActivityForResult(intent, TAKE_PIC);
	           }
	        });
       
    
    
    }
    
    
    public void ToStart(View v)
    {
    	Intent intent = new Intent(this, SimpleShareActivity.class);
        startActivity(intent);
    }
    
    public void ToCheck(View v)
    {
    	Intent intent = new Intent(this, PplOweActivity.class);
        startActivity(intent);
    	
    }
    
        
        
    
    
    void doProcess()
	{	
		givenTo=new boolean[n][n];	
		amtgiven=new int[n][n];
		canpay=new boolean[n];
		totalgiven=new int[n];
		extra=new int[n];
		int req;
		
		
		for(int i=0;i<n;i++)
		{
			total=have[i]+total;	//calculate total amount with all to find if can pay
		}
	
	
		
		if(n>0 && (bill%n)!=0)			//if share for each member cannot be equal 
		{
			if(n>0 && bill/n<1)		//if share for each member is less than 1
			{
				noProcess=true;	//no processing required as some can pay Rs 1 each
				return;			//return to main
			}
			else				//else if share is greater than 1
			{
			int real1=bill/n;	//calculate share which can be equally assigned
			toPay=real1;		//assign to toPay var
			rem=bill%n;			//ignored extra amount someone has to pay extra in rem variable
			}
		}
		else					//if bill can be equally divided
		{
			double real2=(double)bill/n; //calculate the share
			toPay=(int)Math.ceil(real2);
			rem=0;						//since share can be equally divided no remaining amount
		}
		
		
		
		if(total<(toPay*n+rem)) //see if bill is payable 
		{
			notpay=1;			//if not notpay=1
			return;				//cannot paid so no processing required and return
		}
		
		System.out.println("");
		for(int i=0;i<n;i++)	//loop through money with each member
		{
			
			if(toPay<have[i])	//if money member has is sufficient to pay share
			{
				
				canpay[i]=true;		//mark canpay for that member true
			}
			extra[i]=have[i]-toPay; //calculate extra money with each member
									//if extra[i] +ve than member has extra money otherwise no.
		}
		System.out.println("");
		for(int i=0;i<n;i++)	//loop for finding money for members who cannot pay
		{
			if(canpay[i]==false) //if member cannot pay
			{
				req=toPay-have[i]; //calculate money required by member to be able to pay share
				for(int j=0;j<n;j++) //loop for finding members who can give money to member and money given
				{
					if(i!=j)		//avoid considering same member 
					{
						if(extra[j]>0) //check whether member being considered to give money has some extra money
						{
							givenTo[j][i]=true; //mark that jth member has given money to ith member
							if(extra[j]>req)	//if all the required amount for ith member can be obtained from
												//extra amount with jth member 
							{
								extra[j]=extra[j]-req;	//reduce the extra amount with jth member by money given
								amtgiven[j][i]=req;		//make entry about amount of money given by jth member to ith member
								req=0;					// all required money obtained so req=0
								totalgiven[j]=totalgiven[j]+amtgiven[j][i]; //update the total money given by jth member overall
								//System.out.println(names[j]+" "+extra[j]);
							}
							else						//if entire required money cannot be obtained then take all extra money to fulfill some part
							{
								
								req=req-extra[j];		//required money for i reduced by  taken extra amount of j 		
								amtgiven[j][i]=extra[j];	//entry about amount given from j to i
								totalgiven[j]=totalgiven[j]+amtgiven[j][i]; //update the total money given by jth member overall
								extra[j]=0;								//since all extra money taken from j
							}
						}
					}
					
					if(req==0)							//if no more money required to fulfil ith member share then break out of inner loop
						break;
			
				}
			}
		}
					
	}
	
	void output()
	{
		
		if(n>0 && bill/n<1 && noProcess) //if no processing done and share less than 1
		{
			finalOp=finalOp+"\nActual per share=Rs.1 and "+(n-bill)+" friend/s don't have to pay anything"; //some pay some do not pay
		}
		else if(rem==0)		//if no remaining amount then case of equal share
		{
			finalOp=finalOp+"\nActual per share=Rs."+toPay;	
		}
		else			//third case where one member to pay extra amount  (case of unequal share)
		{
			finalOp=finalOp+"\nActual per share=Rs."+toPay+" and one of the friends has to pay Rs."+rem+" more";
		}
		finalOp=finalOp+"\n";
		
		finalOp=finalOp+"Money payment for Bill:";
		
		
		if(n>0 && bill/n<n && noProcess) // when share less than 1
		{
			int k=0,done=0;
			for(k=0;k<n;k++)	//loop for members
			{
				if(have[k]>=1) // if members has more than rs 1
				{
					finalOp=finalOp+"\n"+names[k]+" has to pay Rs.1";
					done++;		//counter for checking whether payment done after considering upto this members
					
				}
				else		//if member has no money
				{
					finalOp=finalOp+"\n"+names[k]+" has to pay nothing....Got lucky I guess..";
				}
				if(done==bill) //break out if bill settled
					break;
			}
			
			if(k<n && done==bill) // if loop break occurs without processing all members.
			{
				for(k=k+1;k<n;k++) //loop through remaining to print that they dont have to pay anything
				{
					finalOp=finalOp+"\n"+names[k]+" has to pay nothing....Got lucky I guess..";
				}
			}
			return; //return back
		}
		
		
		for(int i=0;i<n;i++)// loop for members
		{
			if(canpay[i]) //if member can pay full share without help
			{
				if(extra[i]>=rem && rem!=0) //if extra money left with the member is sufficient to pay rem amount and if amount is rem to be paid
				{
					finalOp=finalOp+"\n"+names[i]+" has to pay Rs."+(toPay+totalgiven[i]+rem)+"(one who also pays the above stated extra money)";
					//print total amount for the member to pay = share + money given to others +rem amount
				rem=0;	//since remaining amount paid
				}
				else //if extra money left with the member is not sufficient to pay the rem amount 
				{
					finalOp=finalOp+"\n"+names[i]+" has to pay Rs."+(toPay+totalgiven[i]);
					//print total amount for the member to pay = share + money given to others
				}
			}
			
			else //if member cannot pay full share without help
				finalOp=finalOp+"\n"+names[i]+" has to pay Rs."+have[i]; //has to pay all the money with him
				
		}
		finalOp=finalOp+"\n";
		finalOp=finalOp+"Money owed:"; 
		
		boolean isOwe=false; //initially assume no one owes anything
		for(int i=0;i<n;i++) //loops for members comparison 
		{
			temp = "";
			for(int j=0;j<n;j++)
			{
				
				if(givenTo[i][j] && amtgiven[i][j]!=0) //is amountgiven from i to j and amountgiven is not zero
				{
					isOwe=true;	//somebody owes someone
					finalOp=finalOp+"\n"+names[i]+" needs to get Rs."+amtgiven[i][j]+ " from "+names[j]+" "; //print money owed
					
					temp = temp+" "+names[j];
					System.out.println("---------------------------");
					System.out.println(temp);
					dataInsert(names[j],amtgiven[i][j],names[i],0);
				}
			
			}
		}
		if(!isOwe) //if no one owes anything
			finalOp=finalOp+"No one owes anyone...Hurray...."; //print it
	}
	
	public void dataInsert(String nameOwes,int x,String nameOwed,int isOver){
		
		String amtowe = x+"";
		String isOvers = isOver+"";
		
		//String amttake = y+"";
		PplOwe entry = new PplOwe(this);
		entry.open();
		entry.insert(nameOwes,amtowe,nameOwed,isOvers);
		entry.close();
	}
	
	
   
        
        protected void onActivityResult(int requestCode, int resultCode,Intent data)
		{
	    	if (requestCode == TAKE_PIC && resultCode==RESULT_OK){
				
				Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
		        // Vibrate for 500 milliseconds
		        v.vibrate(500);
				Toast.makeText(this, "Image saved at location "+outPutfileUri.toString(),Toast.LENGTH_LONG).show();
			}
		}
	    
	    
			
}
