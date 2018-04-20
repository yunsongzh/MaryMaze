package com.zys.mary.load;

import android.os.Bundle;
import game.activity.GameActivity;

public class LoadActivity extends GameActivity implements Runnable{

    //public static LoadView lView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.SetScreenToFull();
        this.GetScreenSize();
        super.onCreate(savedInstanceState);
        //lView = new LoadView(this);
        setContentView(new LoadView(this));
        new Thread(this).start();

    }

    @Override
    public void run() {
        //加载图片
        LoadResource.loadBitmap(this);
    }
}
