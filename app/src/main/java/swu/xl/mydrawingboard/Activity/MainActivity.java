package swu.xl.mydrawingboard.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import swu.xl.mydrawingboard.Bean.PictureBean;
import swu.xl.mydrawingboard.Constant.Constant;
import swu.xl.mydrawingboard.DB.DBManager;
import swu.xl.mydrawingboard.DataCenter.DataManager;
import swu.xl.mydrawingboard.Dialog.AddDialog;
import swu.xl.mydrawingboard.R;
import swu.xl.mydrawingboard.SelfView.XLListView;
import swu.xl.mydrawingboard.Utils.ExternalStorageUtil;
import swu.xl.xltoolbar.XLToolBar;

public class MainActivity extends AppCompatActivity {

    private XLToolBar toolBar;
    private XLListView listView;
    private AddDialog addDialog;

    int REQUEST_CODE = 10086;

    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //视图绑定
        toolBar = findViewById(R.id.tool_bar);
        listView = findViewById(R.id.list);

        //打开弹窗
        toolBar.getLeft_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //改变状态
                isEdit = ! isEdit;

                //改变图标
                if (!isEdit) {
                    v.setBackgroundResource(R.drawable.edit);
                }else {
                    v.setBackgroundResource(R.drawable.done);
                }

                //改变item显示
                changeItemStatus(isEdit);
            }
        });
        toolBar.getRight_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建空的画作：此时需要记录，名称，时间，空白的图片信息
                addDialog = new AddDialog();
                addDialog.show(getSupportFragmentManager(),"add");

                //刷新数据
                addDialog.setUpdateBeanListener(new AddDialog.UpdateBeanListener() {
                    @Override
                    public void updateListView() {
                        listView.getAdapter().notifyDataSetChanged();
                    }
                });
            }
        });

        //listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isEdit){
                    //跳转界面
                    Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                    intent.putExtra("position",position);
                    startActivityForResult(intent,REQUEST_CODE);
                }else {
                    //删除数据
                    PictureBean pictureBean = DataManager.getDataManager(MainActivity.this).getBeans().get(position);

                    //1.删除数据库中的数据
                    DBManager.getDataManager(MainActivity.this).deleteBean(pictureBean);

                    //2.删除模型中的数据
                    DataManager.getDataManager(MainActivity.this).getBeans().remove(position);

                    //3.删除相册中的数据
                    ExternalStorageUtil.removeFileFromExternalStorage(pictureBean.getPath());

                    //4.刷新数据
                    listView.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //判断信息的回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            listView.getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * 改变item的状态
     */
    public void changeItemStatus(boolean status){
        //找到数据
        List<PictureBean> beans = DataManager.getDataManager(this).getBeans();

        //更新数据
        for (PictureBean bean : beans) {
            bean.setDelete(status);
        }

        //刷新
        listView.getAdapter().notifyDataSetChanged();
    }
}