package swu.xl.mydrawingboard.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import java.text.SimpleDateFormat;
import java.util.Date;

import swu.xl.mydrawingboard.Bean.PictureBean;
import swu.xl.mydrawingboard.Constant.Constant;
import swu.xl.mydrawingboard.DB.DBManager;
import swu.xl.mydrawingboard.DataCenter.DataManager;
import swu.xl.mydrawingboard.R;
import swu.xl.mydrawingboard.SelfView.XLListView;

public class AddDialog extends DialogFragment implements View.OnClickListener {

    private UpdateBeanListener listener;
    private EditText name;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //创建构建者
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //加载布局
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.picture_add_layout, null);

        //找到控件
        name = inflate.findViewById(R.id.name);
        Button sure = inflate.findViewById(R.id.sure);

        //回调事件
        sure.setOnClickListener(this);

        //设置给builder
        builder.setView(inflate);

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sure) {
            //格式化时间
            SimpleDateFormat sdf = new SimpleDateFormat();
            //a为am/pm的标记
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
            //获取当前时间
            Date date = new Date();
            //格式化后的时间
            String time = sdf.format(date);

            //获取数据
            PictureBean pictureBean = new PictureBean(0, name.getText().toString(), null, time);
            Log.d(Constant.TAG,pictureBean.toString());
            //插入数据库中
            pictureBean.setId(DBManager.getDataManager(getContext()).insertBean(pictureBean));
            //刷新数据
            DataManager.getDataManager(getContext()).getBeans().add(pictureBean);
            //刷新数据
            if (listener != null) {
                listener.updateListView();
            }
            //关闭
            this.dismiss();
        }
    }

    /**
     * 刷新的接口
     */
    public interface UpdateBeanListener {
        void updateListView();
    }

    //设置监听接口
    public void setUpdateBeanListener(UpdateBeanListener listener) {
        this.listener = listener;
    }
}
