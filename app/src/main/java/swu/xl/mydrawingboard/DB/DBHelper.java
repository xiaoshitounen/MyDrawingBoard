package swu.xl.mydrawingboard.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    //表名
    public static String TABLE = "picture";
    //列明
    public static String TABLE_ID = "id";
    public static String TABLE_NAME = "name";
    public static String TABLE_PATH = "path";
    public static String TABLE_DATE = "date";

    public DBHelper(@Nullable Context context) {
        super(context, "draw.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表的sql语句
        String sql = String.format(
                "create table %s(" +
                        "%s integer primary key autoincrement," +
                        "%s varchar(20) unique not null," +
                        "%s varchar(50) ," +
                        "%s varchar(50) unique not null)",
                TABLE, TABLE_ID, TABLE_NAME, TABLE_PATH, TABLE_DATE);

        //执行
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
