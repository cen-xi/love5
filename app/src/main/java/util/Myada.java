package util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.love5.R;

import java.util.List;
//MYAdapter
public class Myada extends RecyclerView.Adapter<Myada.ViewHolder> implements View.OnClickListener  {
    private Context context;
    private List<Photo> list;
    private Myada.OnRecyclerItemClickListener listener;
    private RecyclerView recyclerView;





    public void setOnRecyclerItemClickListener(Myada.OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public Myada(Context context, List<Photo> list) {
        this.context = context;
        this.list = list;
    }




     static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView photoImage;
        TextView photoName;

         ViewHolder(View itemView) {
            super(itemView);
            photoImage = itemView.findViewById(R.id.imagekkk);
            photoName = itemView.findViewById(R.id.photoname);
            //如果是用同意监听接口
//             则需要为item及item内部控件添加点击事件
//             如  photoImage =setOnClickListener(this);
             //上面的类还要接入接口implements View.OnClickListener
        }

        //然后onClick()在这里写，上面的类还要接入接口implements View.OnClickListener
//        @Override
//        public void onClick(View v) {
//             if (mOnItemClickListener != null) {
//                 mOnItemClickListener.onItemClick(v, getAdapterPosition());
//             }
//         }



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
    public Myada.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //photo_view是指在文件的格式小页面模板文件名
        View view = LayoutInflater.from(context).inflate(R.layout.photo_view,parent,false);
        view.setOnClickListener(this);
        return new Myada.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Myada.ViewHolder holder, int position) {
        Photo photo = list.get(position);
        holder.photoImage.setImageResource(photo.getImageid());
        holder.photoName.setText(photo.getName());

    }



    @Override
    public void onClick(View v) {
        if (recyclerView != null && listener != null){
            //position是指加入的顺序，由0开始一直递增
            int position = recyclerView.getChildAdapterPosition(v);
            listener.OnRecyclerItemClick(recyclerView,v,position,list.get(position).getName());
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }



//    /**
//     * 删除指定数据
//     * @param position 数据位置
//     */
//    public void remove(int position){
//        list.remove(position);
////        notifyDataSetChanged();
//        notifyItemRemoved(position);//这样就只会删除这一条数据，而不会一直刷
//
//    }

//    /**
//     * 插入数据
//     * @param position 插入位置
//     * @param data 插入的数据
////     */
    //底下插入新的数据，因为适配器设置的原因，这里是在地下顺序插入
    public void insert(Photo photo ){
        list.add(photo);
        int position = list.size();
        notifyItemInserted(position);
        //插入后，直接滑动到底下插入的动画那里，很棒
        recyclerView.scrollToPosition(position-1);

    }

    //顶上插入数据
    public void inserttou(Photo photo ){
        list.add(photo);
        int position = list.size();
//        notifyItemInserted(0);
        //两个参数，第一个是插入的位置，0表示头顶插入，第二个指将插入数据的总条数
        notifyItemRangeInserted(0, 1);
        //插入后，直接滑动到顶上插入的动画那里，很棒
        recyclerView.scrollToPosition(0);
//        notifyItemRangeChanged(1, position-1);
    }

//    /**
//     * 自定义RecyclerView的点击事件
//     */
    //自定义接口
    public interface OnRecyclerItemClickListener{
        void OnRecyclerItemClick(RecyclerView parent,View view,int position,String data);
    }
//接口还有OnItemClickListener，也不知道是不是自定义的








}
