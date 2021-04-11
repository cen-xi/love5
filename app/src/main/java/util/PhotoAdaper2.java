package util;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.love5.R;

import java.util.List;



public class PhotoAdaper2 extends RecyclerView.Adapter<PhotoAdaper2.ViewHolder> {
    //新定义一个list容器，用来装传过来的list的参数并存储供这里调用
    private List<Photo> mPhotolist;
    //存储父Java后台
    private Context context;
    //自定义点击事件
    private OnRecyclerItemClickListener listener;
//
    private RecyclerView recyclerView;

//进行步骤：3
    //实列化小面板的控件，功onCreateViewHolder方法实列使用，view是onCreateViewHolder方法实列时传进来的小面板实列参数
    static class ViewHolder extends RecyclerView.ViewHolder {
        View photoView;
        ImageView photoImage;
        TextView photoName;

        //实列相应的局部小面板控件
        public ViewHolder(View view) {
            super(view);
            photoView =view;
            //是在photo_view.xml这个文件里,是局部小面板的元素id，不要写错
            photoImage = view.findViewById(R.id.imagekkk);
            photoName = view.findViewById(R.id.photoname);

        }
    }

    //在为RecyclerView提供数据的时候调用
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }



//进行步骤：1
    //photoList是上个页面实列PhotoAdaper2传进来的包含泛型数据的list，这里赋值给新容器使用
    //Context context这里的Context context是上个页面传进来的面板控制后台，可有可没有，若没有，就把上个页面的this参数去掉
    public PhotoAdaper2(Context context,List<Photo> photoList) {
        //装传过来的list的参数并存储供这里调用
        mPhotolist = photoList;
        //装传过来的context并存储供这里调用
        this.context = context;
    }

//进行步骤：2
    //这个方法必须有，不然类会报错，名字千万不要写错啊，photo_view是指photo_view.xml这个文件的名字，里面是局部小面板
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int x) {
        // //这是整个局部秒面板的文件实列，id就是这个xml文件面板的名字
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_view, parent, false);
        //实列化ViewHolder类，并传进view，然后获取实例参数
       final ViewHolder holder = new ViewHolder(view);


       //在这里设置各个小控件的点击事件
       holder.photoView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //或者用这个getChildAdapterPosition(v)
               int position =holder.getAdapterPosition();

               Photo photo = mPhotolist.get(position);
               Toast.makeText(v.getContext(),"这是view"+photo.getName(),Toast.LENGTH_SHORT).show();


           }
       });

       holder.photoImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int position =holder.getAdapterPosition();
//               int position = recyclerView.getChildAdapterPosition(v);



               Photo photo = mPhotolist.get(position);
               Toast.makeText(v.getContext(),"这是图片"+photo.getName(),Toast.LENGTH_SHORT).show();
               listener.OnRecyclerItemClick("66666666666666666666");
//               NsdManager.ResolveListener h =context.getContentResolver();
//               LinearLayout t = new LinearLayout(context);


           }
       });

        return holder;

    }
    /**
     * 自定义RecyclerView的点击事件
     */
    public interface OnRecyclerItemClickListener{
        void OnRecyclerItemClick(String data);
    }




    //进行步骤：5
    //bind是绑定的意思，也就是绑定小面板，给每个小面板提供信息，小面板排列的顺序是list的加入先后顺序，顺序早已记录在position里
    public void onBindViewHolder(ViewHolder holder, int position) {
        //mPhotolist通过position提供的key找到了相应的单个Photo数据，然后实列化赋给photo
        Photo photo = mPhotolist.get(position);
        //给小面板绑定刚获取的photo里相应的数据
        holder.photoImage.setImageResource(photo.getImageid());
        holder.photoName.setText(photo.getName());
    }

//进行步骤：4
    //获取大面板里小面板的个数，也就是list容器里面的个数
    public int getItemCount() {
        return mPhotolist.size();
    }


    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }



}
