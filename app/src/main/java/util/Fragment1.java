package util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.love5.R;

public class Fragment1 extends Fragment {

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        // TODO Auto-generated method stub

        View view= inflater.inflate(R.layout.paper1, container, false);



        //对View中控件的操作方法

        Button btn = (Button)view.findViewById(R.id.b);

        btn.setOnClickListener(new View.OnClickListener() {



            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                Toast.makeText(getActivity(), "点击了首页", Toast.LENGTH_SHORT).show();

            }

        });

        return view;

    }
















}
