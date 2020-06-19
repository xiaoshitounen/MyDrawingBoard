package swu.xl.mydrawingboard.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import swu.xl.mydrawingboard.Bean.PictureBean;

public class DBManager {
    //数据库database
    public static SQLiteDatabase database;

    //单例模式
    private static DBManager instance = null;
    //私有化构造方法
    private DBManager(Context context){
        initDB(context);
    }
    public static synchronized DBManager getDataManager(Context context){
        if (instance == null){
            instance = new DBManager(context);
        }
        return instance;
    }

    //初始化数据库信息
    public static void initDB(Context context){
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    //查询数据库中的所有数据
    public List<PictureBean> queryAllBean(){
        //1.查找出所有数据的集合
        Cursor cursor = database.query(DBHelper.TABLE, null, null, null, null, null, null);

        //2.创建集合用于存储数据
        List<PictureBean> beans = new ArrayList<>();

        //3.装入集合中
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_NAME));
            String path = cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_PATH));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_DATE));

            beans.add(new PictureBean(id,name,path,date));
        }

        //释放
        cursor.close();

        return beans;
    }

    //向数据库中插入一条数据
    public long insertBean(PictureBean bean){
        //1.创建数据集合
        ContentValues values = new ContentValues();
        values.put(DBHelper.TABLE_NAME,bean.getName());
        values.put(DBHelper.TABLE_PATH,bean.getPath());
        values.put(DBHelper.TABLE_DATE,bean.getDate());

        //2.插入数据
        return database.insert(DBHelper.TABLE, null,values);
    }

    //数据库更新数据
    public void updateBean(PictureBean bean){
        //ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TABLE_PATH,bean.getPath());

        //插入的位置
        String whereClaus = DBHelper.TABLE_DATE+"=?";
        String[] whereArgs = {bean.getDate()};

        //插入
        database.update(DBHelper.TABLE,contentValues,whereClaus,whereArgs);
    }

    //数据库删除数据
    public void deleteBean(PictureBean bean){
        //删除的位置
        String whereClaus = DBHelper.TABLE_DATE+"=?";
        String[] whereArgs = {bean.getDate()};

        //删除
        database.delete(DBHelper.TABLE,whereClaus,whereArgs);
    }
}
