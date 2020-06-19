package swu.xl.mydrawingboard.Utils;

import android.content.Context;

import java.util.List;

import swu.xl.mydrawingboard.Bean.PictureBean;
import swu.xl.mydrawingboard.DB.DBManager;

public class DataUtil {
    /**
     * 从数据库中加载数据
     */
    public static List<PictureBean> loadDataBySql(Context context){
        return DBManager.getDataManager(context).queryAllBean();
    }
}
