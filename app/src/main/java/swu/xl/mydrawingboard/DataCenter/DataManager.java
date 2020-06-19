package swu.xl.mydrawingboard.DataCenter;

import android.content.Context;

import java.util.List;

import swu.xl.mydrawingboard.Bean.PictureBean;
import swu.xl.mydrawingboard.Utils.DataUtil;

public class DataManager {
    //数据
    private List<PictureBean> beans;

    //单例模式
    private static DataManager instance = null;
    //私有化构造方法
    private DataManager(Context context){
        loadData(context);
    }
    public static synchronized DataManager getDataManager(Context context){
        if (instance == null){
            instance = new DataManager(context);
        }
        return instance;
    }

    //加载数据
    public void loadData(Context context) {
        beans = DataUtil.loadDataBySql(context);
    }

    //get方法
    public List<PictureBean> getBeans() {

        return beans;
    }
}
