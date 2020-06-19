package swu.xl.mydrawingboard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import swu.xl.bottomview.XLBottomView;
import swu.xl.mydrawingboard.Bean.PictureBean;
import swu.xl.mydrawingboard.DB.DBManager;
import swu.xl.mydrawingboard.DataCenter.DataManager;
import swu.xl.mydrawingboard.R;
import swu.xl.mydrawingboard.Utils.ExternalStorageUtil;
import swu.xl.utils.SaveToAlbumUtil;
import swu.xl.xldrawboard.XLDrawBoard;
import swu.xl.xlseekbar.XLSeekBar;

public class DrawActivity extends AppCompatActivity {

    private XLSeekBar size_controller;
    private XLDrawBoard draw_board;
    private XLBottomView operation_controller;
    private PictureBean pictureBean;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //初始化操作
        init();

        //接受数据
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        //找到模型
        pictureBean = DataManager.getDataManager(this).getBeans().get(position);

        //判断是否是新的视图
        if (pictureBean.getPath() != null){
            addOldPicture(pictureBean.getPath());
        }
    }

    /**
     * 初始化操作
     */
    private void init() {
        size_controller = findViewById(R.id.size_controller);
        draw_board = findViewById(R.id.draw_board);
        operation_controller = findViewById(R.id.bottom_view);

        //字体大小控制器
        size_controller.setCurrentProgress(draw_board.getLineWidth());
        size_controller.setProgressChangeListener(new XLSeekBar.OnProgressChangeListener() {
            @Override
            public void progressChanged(float progress) {
                draw_board.setLineWidth((int) progress);
            }
        });

        //画板
        draw_board.setLineColor(Color.BLACK);

        //底部控制器
        List<XLBottomView.BottomViewItem> items = new ArrayList<>();
        int[] icon_ids = {R.drawable.pen,R.drawable.eraser,R.drawable.undo,R.drawable.redo,R.drawable.clear,R.drawable.save};
        String[] titles = {"画笔","橡皮","撤销","重做","清空","保存"};
        for (int i = 0; i < 6; i++) {
            XLBottomView.BottomViewItem item = new XLBottomView.BottomViewItem(icon_ids[i], titles[i]);
            items.add(item);
        }
        operation_controller.setItems(items);
        operation_controller.setXLBottomViewItemListener(new XLBottomView.XLBottomViewItemListener() {
            @Override
            public void itemStatusDidChange(int index) {
                switch (index){
                    case 0:
                        //橡皮
                        draw_board.setBoard_state(XLDrawBoard.BOARD_STATE_PEN);
                        break;
                    case 1:
                        //橡皮
                        draw_board.setBoard_state(XLDrawBoard.BOARD_STATE_ERASER);
                        break;
                    case 2:
                        //撤销
                        draw_board.removeLast();
                        break;
                    case 3:
                        //重做
                        draw_board.resumeLast();
                        break;
                    case 4:
                        //清空
                        draw_board.removeAll();
                        break;
                    case 5:
                        String fileName = pictureBean.getName();

                        //保存
                        Bitmap bitmap = draw_board.save();

                        //保存图片到外部存储
                        String path = ExternalStorageUtil.saveBitmapToExternalStoragePrivateFilesDir(bitmap, fileName+".png", DrawActivity.this);

                        //更新数据
                        pictureBean.setPath(path);
                        DataManager.getDataManager(DrawActivity.this).getBeans().get(position).setPath(path);

                        //更新数据库
                        DBManager.getDataManager(DrawActivity.this).updateBean(pictureBean);

                        //回调
                        Intent intent = new Intent();
                        setResult(RESULT_OK,intent);
                        finish();

                        break;
                }
            }
        });
    }

    //设置画笔颜色
    public void changeColor(View v){
        //获取按钮的背景颜色
        ColorDrawable drawable = (ColorDrawable) v.getBackground();

        //设置背景颜色
        draw_board.setLineColor(drawable.getColor());
    }

    /**
     * 将之前的结果绘制在面板上
     * @param path
     */
    private void addOldPicture(String path) {
        draw_board.drawOldBitmap(ExternalStorageUtil.loadBitmapFromExternalStorage(path));
    }
}