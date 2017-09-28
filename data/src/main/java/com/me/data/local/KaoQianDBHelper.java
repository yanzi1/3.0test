package com.me.data.local;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.me.core.app.AppContext;
import com.me.data.model.main.MainTypeDetailBean;
import com.yunqing.core.db.database.DBHelper;
import com.yunqing.core.db.table.TableFactory;

/**
 * 实现DBHelper 继承SQLiteOpenHelper<br/>
 * 子类可以通过继承该类进行监听数据库版本的变化，和数据库创建,用法参照(
 * {@link SQLiteOpenHelper})
 * @author Administrator
 * 
 */
public class KaoQianDBHelper extends SQLiteOpenHelper implements DBHelper {

	public static final String DEFAULT_DBNAME = "dongao.db";
	public static final int DEFAULT_DBVERION = 2; //初始版本为1

	private String path;
	private static KaoQianDBHelper kaoQianDBHelper;

	public static KaoQianDBHelper getInstance() {
		if(kaoQianDBHelper == null){
			kaoQianDBHelper = new KaoQianDBHelper();
		}
		return kaoQianDBHelper;
	}

	public KaoQianDBHelper() {
		super(AppContext.getInstance(), DEFAULT_DBNAME, null, DEFAULT_DBVERION);
		path = AppContext.getInstance().getDatabasePath(DEFAULT_DBNAME).getPath();
	}

	public KaoQianDBHelper( String name, int version) {
		super(AppContext.getInstance(), name, null, version);
		path = AppContext.getInstance().getDatabasePath(name).getPath();
	}



	@Override
	public SQLiteDatabase getDatabase() {
		SQLiteDatabase database = null;
		try {
			database = getWritableDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return database;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//更新操作 例如course 需要更新 新加的实体无需在此写入，会自动添加一个表的
		if(oldVersion==1){
			String course_table_name = TableFactory.getTableName(MainTypeDetailBean.class);
//			String course_table_name = "t_maintypedetail";
//			String sql = "alter table "+ course_table_name + " add column examImg varchar(30);";
			String sql = "drop table "+ course_table_name ;
			db.execSQL(sql);
		}
	}

	@Override
	public String getDatabasePath() {
		return path;
	}
}
