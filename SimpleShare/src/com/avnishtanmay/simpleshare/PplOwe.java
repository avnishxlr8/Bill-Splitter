package com.avnishtanmay.simpleshare;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class PplOwe {
	
	public static final String ROW_ID = "_id";
	public static final String ROW_NAME = "name";
	public static final String ROW_AMOUNT_TO_GIVE = "amt_to_give";
	public static final String ROW_AMOUNT_TO_WHOM = "amt_to_whom";
	public static final String IS_DONE = "Is_Done";
	
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_NAME="simplesharedb";
	private static final String DATABASE_TABLE="hisaab";
	private static final String CREATE_QUERY="create table hisaab (_id integer primary key autoincrement,name text not null,amt_to_give text not null,"+
                                             "amt_to_whom text not null,Is_Done text not null)";
    
	String[] columns = new String[]{ROW_ID,ROW_NAME,ROW_AMOUNT_TO_GIVE,ROW_AMOUNT_TO_WHOM,IS_DONE};
	
	public Context ourContext;
	public DbHelper ourHelper;
	public SQLiteDatabase ourDatabase;
	
	private  class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
		  db.execSQL(CREATE_QUERY);	
		 // Toast.makeText(ourContext, "onCreate called", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
			Toast.makeText(ourContext, "onUpdate called", Toast.LENGTH_SHORT).show();
			onCreate(db);
		}
}
		
	public PplOwe(Context c){
	
		ourContext = c;
	}
	
	public PplOwe open() throws SQLException{
		 ourHelper = new DbHelper(ourContext);
		 ourDatabase = ourHelper.getWritableDatabase();
		 //Toast.makeText(ourContext, "PPLowe open called", Toast.LENGTH_SHORT).show();
		 return this;
		
	}
	
     public void close(){
    	 ourHelper.close();
     }
     
 	public void insert(String u,String p,String c,String d){
		ContentValues cv =  new ContentValues();
		cv.put(ROW_NAME,u);
		cv.put(ROW_AMOUNT_TO_GIVE,p);
		cv.put(ROW_AMOUNT_TO_WHOM,c);
		cv.put(IS_DONE,d);
		
		long  i = ourDatabase.insertWithOnConflict(DATABASE_TABLE, null, cv, ourDatabase.CONFLICT_REPLACE);
		System.out.println("---------------------------");
		System.out.println("i="+i);
		if(i!=-1){
			Toast.makeText(ourContext, "Data inserted Successfully ", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(ourContext, "Data not inserted Successfully ", Toast.LENGTH_SHORT).show();
		}
		
	}
 	
 	public String[] getData(int index){
 		
 		//System.out.println("Getdata called");
 		
 	     
 		//String[] columns = new String[]{ROW_NAME,ROW_AMOUNT_TO_GIVE,ROW_AMOUNT_TO_WHOM,IS_DONE};
 		//Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null,null);
 
 		Cursor c;
 		
 		c = ourDatabase.rawQuery("SELECT * from hisaab", null);
 		
 		int n;
 		String[] result  = null;
 		
        int i = 0;
 		/*int iname = c.getColumnIndex("name");
 		int igive = c.getColumnIndex("amt_to_give");
 		int iwhom = c.getColumnIndex("amt_to_whom");
 		int idone = c.getColumnIndex("Is_Done");*/
        try{
 		switch(index){
 		
 		case 1:
 			   c = null;
 			   c = ourDatabase.rawQuery("SELECT name from hisaab", null);
 			   n = c.getCount();//n is initialized in each switch with respect to the query and so the result array size
 			   result = new String[n];
		       if(c.moveToFirst()){
		 		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
		            result[i] = c.getString(c.getColumnIndex("name"));
		            i++;
		           // Toast.makeText(ourContext, "In for loop", Toast.LENGTH_SHORT).show();
		            }
		 		}
		       //for(int j=0;j<result.length;j++)
				//	System.out.println("result= "+result[j]);
		       return result;
 		case 2:
 			   c = null;
			   c = ourDatabase.rawQuery("SELECT amt_to_give from hisaab", null);
			   n = c.getCount();
			   result = new String[n];
		       if(c.moveToFirst())
		 		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
		 			 result[i++] = c.getString(c.getColumnIndex("amt_to_give")); 			
		 		  }
		       //for(int j=0;j<result.length;j++)
				//	System.out.println("result= "+result[j]);
		       return result;

 		case 3:
 			   c = null;
 			  String  zero = "0";
			   c = ourDatabase.rawQuery("SELECT DISTINCT amt_to_whom from hisaab where Is_Done = '"+zero+"'", null);
			   n = c.getCount();
			   result = new String[n];
		       if(c.moveToFirst())
		 		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
		 			 result[i++] = c.getString(c.getColumnIndex("amt_to_whom"));
		 			// Toast.makeText(ourContext, "i= "+i, Toast.LENGTH_SHORT).show();
		 		  }
		       //for(int j=0;j<result.length;j++)
				//	System.out.println("result= "+result[j]);
		       return result;
 		case 4:
 			   c = null;
			   c = ourDatabase.rawQuery("SELECT Is_Done from hisaab", null);
			   n = c.getCount();
			   result = new String[n];
		       if(c.moveToFirst())
		 		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
		 			 result[i++] = c.getString(c.getColumnIndex("Is_Done"));  			
		 		  }
		       //for(int j=0;j<result.length;j++)
				//	System.out.println("result= "+result[j]);
		       return result;
 		 }
 		
 		}catch(Exception e){
        	System.out.println("error= "+e);
        }
      return result;
 	}
 	
 	
 	public String[] getSpecific(String name){
 		 
 		   String zero = "0";
 		   System.out.println("Get specific called");
 		   Cursor c = null;
		   c = ourDatabase.rawQuery("SELECT name FROM hisaab WHERE amt_to_whom = '"+name+"' AND Is_Done = '"+zero+"' ", null);
 		  // c = ourDatabase.query(DATABASE_TABLE, columns, ROW_AMOUNT_TO_WHOM +"= ' "+name+" ' ", null, null, null, null);
		   int n = c.getCount();//n is initialized in each switch with respect to the query and so the result array size
		   int i=0;
		   String[] result = new String[n];
	       if(c!=null){
	 		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
	            result[i] = c.getString(c.getColumnIndex("name"));
	            i++;
	           // Toast.makeText(ourContext, "In for loop", Toast.LENGTH_SHORT).show();
	            }
	 		}
	      // for(int j=0;j<result.length;j++)
				//Toast.makeText(ourContext, result[j]+"", Toast.LENGTH_SHORT).show();
	       return result;
 	}
 	
 	public String getTuple(String name){
 		   System.out.println("getTuple called");
		   Cursor c = null;
		   c = ourDatabase.rawQuery("SELECT amt_to_give FROM hisaab WHERE name = '"+name+"' ", null);
		  // c = ourDatabase.query(DATABASE_TABLE, columns, ROW_AMOUNT_TO_WHOM +"= ' "+name+" ' ", null, null, null, null);
		   int n = c.getCount();//n is initialized in each switch with respect to the query and so the result array size
		   int i=0;
		   String result = "";
	       if(c!=null){
	 		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
	            result = c.getString(c.getColumnIndex("amt_to_give"));
	        
	            //Toast.makeText(ourContext, "In for loop", Toast.LENGTH_SHORT).show();
	            }
	 		}
	       
	       return result;
 		
 	}
 	
 	public void updateStatus(String name){
 		ContentValues cv =  new ContentValues();
		
		cv.put(IS_DONE,"1");
		
		long  i = ourDatabase.update(DATABASE_TABLE, cv, "name = '"+name+"'", null);
		
		
		System.out.println("---------------------------");
		System.out.println("i="+i);
		if(i!=-1){
			Toast.makeText(ourContext, "Data inserted Successfully ", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(ourContext, "Data not inserted Successfully ", Toast.LENGTH_SHORT).show();
		}
		
	}
 	
 	public int delete(String name) {
		return ourDatabase.delete(DATABASE_TABLE, "name = '"+name+"'", null);
	}
		
}
 	
	
	

