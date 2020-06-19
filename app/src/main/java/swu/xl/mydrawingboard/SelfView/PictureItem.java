package swu.xl.mydrawingboard.SelfView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import swu.xl.mydrawingboard.Bean.PictureBean;
import swu.xl.mydrawingboard.R;
import swu.xl.mydrawingboard.Utils.ExternalStorageUtil;

public class PictureItem {
    //item对应的模型
    private PictureBean bean;
    //item对应的视图
    private int layout;

    //存储关联好的视图
    private View item_view;

    /**
     * 构造方法
     */
    public PictureItem(PictureBean bean, Context context, View convertView) {
        this.bean = bean;
        this.layout = R.layout.picture_item_layout;

        //关联
        initView(context,convertView);
    }

    //将模型和视图关联
    private void initView(Context context,View convertView){
        //1.获取布局
        ViewHolder viewHolder;
        if (convertView == null){
            //2.获取子视图

            //加载布局
            convertView = LayoutInflater.from(context).inflate(layout, null);
            //完善ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.img = convertView.findViewWithTag(context.getResources().getString(R.string.IMG));
            viewHolder.name = convertView.findViewWithTag(context.getResources().getString(R.string.NAME));
            viewHolder.date = convertView.findViewWithTag(context.getResources().getString(R.string.DATE));
            viewHolder.delete = convertView.findViewWithTag(context.getResources().getString(R.string.DELETE));

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (bean != null){
            //3.设置数据
            if (bean.getPath() != null) {
                viewHolder.img.setImageBitmap(ExternalStorageUtil.loadBitmapFromExternalStorage(bean.getPath()));
            }else {
                viewHolder.img.setImageResource(R.drawable.empty);
            }

            viewHolder.name.setText(bean.getName());
            viewHolder.date.setText(bean.getDate());

            if (bean.isDelete()) {
                viewHolder.delete.setVisibility(View.VISIBLE);
            }else {
                viewHolder.delete.setVisibility(View.INVISIBLE);
            }
        }

        //4.保存视图
        item_view = convertView;
    }

    /**
     * 内部类：ViewHolder
     */
    private class ViewHolder{
        public ImageView img;
        public TextView name;
        public TextView date;
        public ImageView delete;
    }

    //get方法
    public View getItem_view() {
        return item_view;
    }
}
