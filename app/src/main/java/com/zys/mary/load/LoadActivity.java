package com.zys.mary.load;


import android.content.Intent;
import android.os.Bundle;
import com.ldoublem.loadingviewlib.view.LVEatBeans;
import com.zys.mary.menu.MenuActivity;
import game.activity.GameActivity;


public class LoadActivity extends GameActivity implements Runnable{

    private LVEatBeans load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new Thread(this).start();

        this.SetScreenToFull();                   //设置全屏
        this.GetScreenSize();                     //取得屏幕大小尺寸

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_load);
        setContentView(new LoadView(this));

        //load = (LVEatBeans)findViewById(R.id.load);
        //load.startAnim();
        //load.setViewColor(R.color.colorAccent);
        //load.setEyeColor(R.color.colorPrimaryDark);

    }

    /**@Override
    protected void onDestroy() {
        super.onDestroy();
        load.stopAnim();
    }*/

    @Override
    public void run() {
        //加载图片
        LoadResource.loadBitmap(this);
        Intent i = new Intent(this, MenuActivity.class);
        this.startActivity(i);
        this.finish();
    }
}
