package swu.xl.mydrawingboard.Application;

import android.app.Application;

import swu.xl.mydrawingboard.DB.DBManager;
import swu.xl.mydrawingboard.DataCenter.DataManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化单例类
        DBManager.getDataManager(this);
        DataManager.getDataManager(this);
    }
}
