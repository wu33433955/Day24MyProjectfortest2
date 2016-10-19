package com.qf.wmj.day24myprojectfortest.dbutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 1,创建SQLiteOpenHelper的子类
 *
 */
public class DBHelper extends SQLiteOpenHelper{
	/**
	 * 数据库名称
	 * 一定要以.db结束
	 */
	private static final String DB_NAME = "date.db";
	/**
	 * 数据库版本号
	 * 必须从1开始
	 */
	private static final int DB_VERSION = 1;
	/**
	 * @param context 环境
	 * @param //name	数据库名称
	 * @param //factory 游标工厂
	 * @param //version 数据库的版本号
	 */
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	/**
	 * 数据库初始化
	 * 建表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table beans (_id integer primary key autoincrement," +
				"id text," +
				"title text," +
				"dp text," +
				"imgUrl text)";
		//数据库执行sql语句
		db.execSQL(sql);
	}
	/**
	 * 数据库升级
	 * 删除旧表，创建新表
	 * 
	 * oldVersion：旧版本号
	 * newVersion：新版本号
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
					
	}
	/**
	 * 还原数据库
	 */
//	@Override
//	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		super.onDowngrade(db, oldVersion, newVersion);
//	}

}
