package com.example.love5;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MainToolbar extends AppCompatActivity {
    PopupMenu popupMenu;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_toolbar);
        //记得不要导错类，要用v7库的，不然会报错
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button b1 = findViewById(R.id.b1);

        popupMenu = new PopupMenu(this, b1);
        menu = popupMenu.getMenu();

        //实现弹出的菜单框显示图片
        Method m = null;
        try {
            m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        m.setAccessible(true);
        try {
            m.invoke(menu, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 通过代码添加菜单项,在action修改xml文件，为菜单添加新item
//
//       menu.add(Menu.NONE, Menu.FIRST + 0, 0, "复制");
//
//        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "粘贴");



// 通过XML文件添加菜单项
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navigation, menu);
//toolbar菜单监听
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(getApplicationContext(), "home",
                                Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navigation_dashboard:
                        Toast.makeText(getApplicationContext(), "navigation_dashboard",
                                Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navigation_notifications:
                        Toast.makeText(getApplicationContext(), "navigation_notifications",
                                Toast.LENGTH_LONG).show();
                        break;
//                    case Menu.FIRST + 0:
//
//                        Toast.makeText(getApplicationContext(), "复制",
//
//                                Toast.LENGTH_LONG).show();
//
//                        break;
//                    case Menu.FIRST + 1:
//
//                        Toast.makeText(getApplicationContext(), "粘贴",
//
//                                Toast.LENGTH_LONG).show();
//
//                        break;
                    default:
                        break;
                }
                return false;
            }
        });
//按钮触发显示弹出菜单
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });


    }



    //绑定toolbar的图标
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar, menu);
        return true;
    }

    //图标点击响应操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.audio:
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.camera:
                Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add:
                Toast.makeText(getApplicationContext(), "添加好友", Toast.LENGTH_SHORT).show();
                break;

        }


        return true;
    }

    //实现toolbar的菜单显示图片
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                {
                    Method m = null;
                    try {
                        m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    m.setAccessible(true);
                    try {
                        m.invoke(menu, true);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return true;
    }

}
