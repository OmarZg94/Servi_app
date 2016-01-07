package net.servi.dao;

import java.util.List;

import net.servi.database.APPDB;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DAO {
	protected String TAG="DAO";
	
	protected APPDB helper;
	protected SQLiteDatabase db;
	protected Cursor cursor;
	protected CursorWrapper cursorWrapper;
	
	public String TABLE_NAME;
	public String PK_FIELD;
	public String REPORT_FIELD;
	
	public DAO(String TABLE_NAME,String PK_FIELD){
		this.TABLE_NAME=TABLE_NAME;
		this.PK_FIELD=PK_FIELD;
		helper=new APPDB();
	}
	
	public DAO(String TABLE_NAME,String PK_FIELD, String REPORT_FIELD){
		this(TABLE_NAME,PK_FIELD);
		this.REPORT_FIELD=REPORT_FIELD;
	}
	
	/**
	 * Insert
	 */
	public long insert(ContentValues cv)
	{
		db=helper.getWritableDatabase();
		long resp=0;
		try {
			db.beginTransaction();
			resp=db.insert(TABLE_NAME, null, cv);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e(TAG,"error inserting cv into db");
			e.printStackTrace();
		} finally	{
			db.endTransaction();
			db.close();
		}
		
		return resp;
	}
	
	
	/**
	 * Insert All
	 */
	public boolean insertAll(List<ContentValues> cvs)
	{
		db=helper.getWritableDatabase();
		try {
			db.beginTransaction();
			for (ContentValues cv:cvs)
				db.insert(TABLE_NAME, null, cv);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e(TAG,"error inserting cvs into db");
			e.printStackTrace();
			return false;
		} finally	{
			db.endTransaction();
			db.close();
		}
		
		return true;
	}
	
	/**
	 * Delete
	 */
	public int deleteById(int _id)
	{
		int resp=0;
		try {
			db=helper.getWritableDatabase();
			resp=db.delete(TABLE_NAME, PK_FIELD + "=?", new String[]{String.valueOf(_id)});
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return resp;
	}
	/**
	 * Delete
	 */
	public int delete()
	{
		
		int resp=0;
		try {
			db = helper.getWritableDatabase();
			resp = db.delete(TABLE_NAME, null, null);
		} catch (Exception e) {}
		finally{
			db.close();
		}
		return resp;
	}
	/**
	 * Cursor
	 */
	public Cursor selectAll(){
		db = helper.getReadableDatabase();
		return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
	}
	
	public Cursor selectBy(String column, String value){
		db = helper.getReadableDatabase();
		return db.query(TABLE_NAME, null, column+"="+value, null, null, null, null);
	}
	
	public Cursor selectBy(String column, int value){
		return selectBy(column, String.valueOf(value));
	}
	
	/**
	 * Count
	 */
	public int count()
	{
		db = helper.getReadableDatabase();
		cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
	
	/**
	 * The specified id exists in the DB?
	 * @param id Primary key
	 * @return
	 */
	public boolean exists(int id){
		db = helper.getReadableDatabase();
		cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE "+PK_FIELD+"="+id, null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		
		return count>0?true:false;
	}
	
	public boolean existsByIdReport(int idReport){
		if (REPORT_FIELD==null){
			Log.e("DAO", "REPORT_FIELD null for "+TABLE_NAME);
			return false;
		}
		db = helper.getReadableDatabase();
		cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE "+REPORT_FIELD+"="+idReport, null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		
		return count>0?true:false;
	}
	
	public void markAsSent(int idReport){
		String query="UPDATE "+TABLE_NAME+" SET enviado=1 WHERE "+REPORT_FIELD+"="+idReport;
		Log.d(TAG,query);
		db=helper.getWritableDatabase();
		try {
			db.beginTransaction();
			db.execSQL(query);
			db.setTransactionSuccessful();
		}catch (Exception e) {
			Log.e(TAG,"error db");
		} finally	{
			db.endTransaction();
			db.close();
		}
		return;
	}
}
