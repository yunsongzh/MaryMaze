package com.zys.mary.load;

import android.content.Intent;
import android.os.Bundle;

import com.zys.mary.menu.MenuActivity;

import game.activity.GameActivity;

public class LoadActivity extends GameActivity implements Runnable{

    //public static LoadView lView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new Thread(this).start();

        this.SetScreenToFull();                   //设置全屏
        this.GetScreenSize();                     //取得屏幕大小尺寸




        super.onCreate(savedInstanceState);
        //lView = new LoadView(this);
        setContentView(new LoadView(this));


    }

    @Override
    public void run() {
        //加载图片
        LoadResource.loadBitmap(this);
        Intent i = new Intent(this, MenuActivity.class);
        this.startActivity(i);
        this.finish();
    }
}
