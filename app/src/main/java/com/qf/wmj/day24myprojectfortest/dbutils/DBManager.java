package com.qf.wmj.day24myprojectfortest.dbutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private DBHelper dbHelper;

	public DBManager(Context context){
		dbHelper = new DBHelper(context);
	}
	public long insert(String table,ContentValues values){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		/**
		 * 如果返回值为-1表示插入失败
		 */
		long insert = db.insert(table, null, values);
		db.close();
		return insert;
	}
	public void delete(String table,String whereClause,String[] whereArgs){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.delete(table, whereClause, whereArgs);
		db.close();
	}
	public void clear(String table){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.delete(table, null, null);
		db.close();
	}
	public void upDate(String table,ContentValues values,String whereClause,String[] whereArgs){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.update(table, values, whereClause, whereArgs);
		db.close();
	}
	public Cursor query(String table,String selection,String[] selectionArgs){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(table, null, selection, selectionArgs, null, null, null);
		return cursor;
	}
	public Cursor queryAll(String table){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(table, null, null, null, null, null, null);
		return cursor;
	}
	public Cursor queryPage(String table,int page){
		Cursor cursorAll = queryAll(table);
		//数据库中总条数
		int count = cursorAll.getCount();
		//每页的条数
		int size = 5;
		//最大页数
		int maxPage = 0;
		if (count%size > 0) {
			maxPage = count/size +1;
		}else{
			maxPage = count/size;
		}
		String sql = "";
		if (page < maxPage) {
			int i = (page-1)*size;
			sql = "select * from "+table+" limit "+i+","+size;
		}else if(page == maxPage){
			int i = (page-1)*size;
			sql = "select * from "+table+" limit "+i+","+(count-i);
		}else{
			return null;
		}
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
//		String limit = "";
//		if (page < maxPage) {
//			int i = (page-1)*size;
//			limit = i+","+size;
//		}else if(page == maxPage){
//			int i = (page-1)*size;
//			limit = i+","+(count-i);
//		}else{
//			return null;
//		}
//		db.query(table, null, null, null, null, null, null, limit);
		return cursor;
	}
}
