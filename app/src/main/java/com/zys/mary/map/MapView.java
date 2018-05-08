package com.zys.mary.map;

import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.SurfaceHolder;

import com.zys.mary.load.LoadResource;

import java.util.ArrayList;

import game.view.GameView;

public class MapView extends GameView implements Runnable {

    //关卡集合
    private ArrayList <Level>level = new ArrayList<Level>();

    //当前关卡
    private Level nowLevel;

    private Mary mary;

    public MapView(Context context) {
        super(context);
        this.setKeepScreenOn(true);                          //设置屏幕常亮
        this.setFocusableInTouchMode(true);                  //获得焦点

        //创建关卡
        for(int i=1; i<=1; i++)
        {
            level.add(new Level(i,context));
        }

        this.nowLevel = level.get(0);
        mary = new Mary(16,16, LoadResource.mary.get(0));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.flag = true;
        this.t = new Thread(this);
        this.t.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.flag = false;
    }

    public Level getNowLevel() {
        return nowLevel;
    }

    public Mary getMary() {

        return mary;
    }

    public void Draw(){
        this.canvas = sh.lockCanvas();
        if(canvas != null)
        {
            canvas.drawColor(Color.BLACK);


            //绘制背景地图
            for(int i=0; i<this.nowLevel.getBackmap().size(); i++)
            {
                BackMap backmap = this.nowLevel.getBackmap().get(i);
                backmap.Draw(canvas);
            }

            //根据map数组得到的Tile块的位置进行地图绘制
            for(int i=0; i<this.nowLevel.getTile().size(); i++)
            {
                Tile tile = this.nowLevel.getTile().get(i);
                tile.Draw(canvas);
                //tile.ChangeImage();
            }

            //进行人物绘制
            mary.Draw(canvas);
            mary.Move(this,this.nowLevel.getCol());
            mary.ChangeImage();

            this.sh.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mary.onKeyDown(keyCode,event);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        mary.onKeyUp(keyCode,event);
        return true;
    }

    @Override
    public void run() {
        while(flag)
        {
            this.Draw();
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
