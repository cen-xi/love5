package util;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.love5.R;

import java.util.List;

public class Myadaper extends RecyclerView.Adapter<Myadaper.ViewHolder>{
    private Myadaper.OnRecyclerItemClickListener listener;
    private Context context;
    private List<Photo> list;
    private RecyclerView recyclerView;


    //接口点击监听
    public void setOnRecyclerItemClickListener(Myadaper.OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    //传入值存储处理
    public Myadaper(Context context, List<Photo> list) {
        this.context = context;
        this.list = list;
    }


    //实列子布局控件有哪些
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImage;
        TextView photoName;

        public ViewHolder(View itemView) {
            super(itemView);
            photoImage = itemView.findViewById(R.id.imagekkk);
            photoName = itemView.findViewById(R.id.photoname);
        }
    }

    //在为RecyclerView提供数据的时候调用
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }


    @Override
    public Myadaper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_view, parent, false);
//        view.setId();
//        view.setOnClickListener(this);
        final ViewHolder holder = new ViewHolder(view);
        holder.photoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = recyclerView.getChildAdapterPosition(v);
                int position = holder.getAdapterPosition();
                Photo p = list.get(position);
                listener.OnRecyclerItemClick2(position,p);
            }
        });

        holder.photoName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = recyclerView.getChildAdapterPosition(v);
//                int position = holder.getAdapterPosition();
//                Photo p = list.get(position);
//                listener.OnRecyclerItemClick2(p);
                listener.Oinia();
            }
        });








        return holder;









    }

    @Override
    public void onBindViewHolder(Myadaper.ViewHolder holder, int position) {
        Photo photo = list.get(position);
        holder.photoImage.setImageResource(photo.getImageid());
        holder.photoName.setText(photo.getName());

    }


//    @Override
//    public void onClick(View v) {
//        if (recyclerView != null && listener != null) {
//            ImageView v2 = v.findViewById(R.id.imagekkk);
//
//
//
////            switch (v.getId()) {
////                case R.id.photoname:
////                    listener.ainia();
////                case R.id.imagekkk:
////                    int position = recyclerView.getChildAdapterPosition(v);
////                    listener.OnRecyclerItemClick(recyclerView, v, position, list.get(position).getName());
////            }
//            int position = recyclerView.getChildAdapterPosition(v);
////            listener.OnRecyclerItemClick2(recyclerView, v, position, list.get(position));
//                listener.Oinia(list.get(position));
//
//
//        }
//    }
//

    @Override
    public int getItemCount() {
        return list.size();
    }


    //接口
    public interface OnRecyclerItemClickListener {
        //这里是适配器调用的方法可以返回值给主页，可以自定义
//        void OnRecyclerItemClick2(RecyclerView parent, View view, int position,Photo photo);
        void OnRecyclerItemClick2( int position,Photo photo);

        void Oinia();
    }


//方法，这是主页操控控件内部的方法，可以自定义
//插入新的数据，因为适配器设置的原因，这里是在地下顺序插入
public void insert(Photo photo){
    list.add(photo);
    int position = list.size();
    notifyItemInserted(position);
    //notifyItemRangeInserted,这个方法有两个参数，一个参数是需要插入的数据的位置，第二个参数是将插入的长度
//    mAdapter.notifyDataSetChanged()
}

//更新算法还有这种，把原来的饿替换掉
//传入List<Weather>，只不过这个List只有一个Weather对象，然后就可以每一次把原来的remove掉，再添加上新的Weather对象。如下：
// dataList.clear();   //去掉之前的数据
//dataList.add(weather);    //添加新的Weather对象
//mAdapter.notifyDataSetChanged();更改数据
    //notifyItemRemoved和notifyItemRangeRemoved都可以删除数据
//
    //自动定位到最底部scrollToPosition(int position)

    //用法

////添加数据
//
//    public void addItem(int position, Object data) {
//
//        mDatas.add(position, data);
//
//        notifyItemInserted(position);//通知演示插入动画
// //     notifyItemRangeInserted,这个方法有两个参数，一个参数是需要插入的数据的位置，第二个参数是将插入的长度，也是通知演示插入动画
//        notifyItemRangeChanged(position,mDatas.size()-position);//通知数据与界面重新绑定，更新绑定position
//如果notifiItemRangeInserted或者notifyItemInserted的第一个参数值是0 的话，也就是说新插入的数据在最上面
//插入后使用mRecyclerView.scrollToPosition(0);那这样就可以很明显的看到顶部插入动画了。
//
//
//    }

//    其实通过观察方法名字可以看出来，无论是notifyItemInserted还是notifyItemRemoved的方法名中，
//    都没有“Changed”这个单词。所有notify开头的方法中，
//    仅仅只有以下三个方法带有“Changed”单词且具有重新绑定数据与界面的功能：
//    notifyDataSetChanged();//通知重新绑定所有数据与界面
//
//    notifyItemChanged(int);//通知重新绑定某一个Item的数据与界面
//
//    notifyItemRangeChanged(int, int);//通知重新绑定某一范围内的的数据与界面





}
