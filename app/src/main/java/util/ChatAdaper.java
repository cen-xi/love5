package util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.love5.R;

import java.util.List;


public class ChatAdaper extends RecyclerView.Adapter<ChatAdaper.ViewHolder> {
    private List<ChatTwoType> list;//数据源


    //实列化所有item的所有控件，包括布局实列化
    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftlayout;
        LinearLayout rightlayout;
        TextView lefttext;
        TextView righttext;
        ImageView lefyim;
        ImageView rightim;

        public ViewHolder(View view) {
            super(view);
            leftlayout = view.findViewById(R.id.iteml);
            lefttext = view.findViewById(R.id.item1t);
            lefyim = view.findViewById(R.id.item1i);
            rightlayout = view.findViewById(R.id.itemr);
            righttext = view.findViewById(R.id.item2t);
            rightim = view.findViewById(R.id.item2i);
        }
    }

//activity传进来的数据，参数可以根据需要修改，需要context才传，不用则不传，参数与在activity实列适配器的参数对应
    public ChatAdaper(List<ChatTwoType> list) {
        this.list = list;
        Log.d("ChatAdaper", "============" + list.get(0).getMessage());

    }


    //计算有多少个item
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    //获取item并实列ViewHolder，把所有触发事件监控写出来，然后调用接口的方法执行你要做的事情，一般是用来传值到context然后再处理
    @Override
    public ChatAdaper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //parent.getContext()可用context替换，前提是context需要由activity传进来
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item1, parent, false);


        final ViewHolder holder =new ViewHolder(view);
        holder.lefyim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ChatAdaper","7777777777777777777777");
                listener.a55(1);
            }
        });

        holder.rightim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ChatAdaper","444444444444444444444444444444444444477");
                listener.a55(2);
            }
        });
        return holder;
    }



    //绑定控件根据泛型类型的参数类型判断哪个布局显示，并将该布局的所有控件绑定相应的数据，不要的布局item一定要隐藏
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatTwoType chatTwoType = list.get(position);
        if (chatTwoType.getType() == ChatTwoType.TYPE_ONE) {
//收到的消息

            //item布局显示
            holder.leftlayout.setVisibility(View.VISIBLE);
            //item布局隐藏
            holder.rightlayout.setVisibility(View.GONE);
//            控件绑定相应的数据
            holder.lefyim.setImageResource(chatTwoType.getIcon());
            holder.lefttext.setText(chatTwoType.getMessage());

        } else if (chatTwoType.getType() == ChatTwoType.TYPE_TWO) {
            //item布局显示
            holder.rightlayout.setVisibility(View.VISIBLE);
            //item布局隐藏
            holder.leftlayout.setVisibility(View.GONE);
//            控件绑定相应的数据
            holder.rightim.setImageResource(chatTwoType.getIcon());
            holder.righttext.setText(chatTwoType.getMessage());
        }


    }



    //自定义实列
    private ChatAdaper.OnRecyclerItemClickListener listener;
    //实列接口setOnRecyclerItemClickListener要在activity的适配器实列后设置，用来给接口实列，如果没有会报空指针
    public void setOnRecyclerItemClickListener(ChatAdaper.OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }
    //自定义接口名称，然后自定义接口里的方法，方法用于适配器监听触发调用，具体的方法内容在activity里，一般是用来从适配器传输数据到activity去，然后在activity里执行操作
    public interface OnRecyclerItemClickListener {
        //这里是适配器调用的方法可以返回值给activity，可以自定义
        void a55(int u);
    }


}

