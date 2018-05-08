package com.zys.mary.map;

import android.graphics.Bitmap;

import com.zys.mary.load.LoadResource;

import game.sprite.Sprite;

public class Tile extends Sprite {

    private int type;                                        //地图块的属性
    private int index;                                       //地图块的下标
    private int changeTime = 4;                              //切图的时间
    static int count;                                        //记录移动了多少列

    public Tile(float x, float y, Bitmap image,int type) {

        //绘制地图块
        super(x, y, image);
        this.type = type;
        //根据地图块属性决定index,绘制需要切图的地图块
        switch (type){
            case 21:
                this.index = 8;
                break;
            case 17:
                this.index = 31;
                break;
        }
    }

    //地图移动方法
    public static void Move(MapView mv) {
        if(mv.getMary().hp == 0) return;

        if(mv.getMary().getState().equals("右跑")) {
            for(int i=0; i<mv.getNowLevel().getTile().size(); i++) {
                Tile t = mv.getNowLevel().getTile().get(i);
                t.x -= mv.getMary().getxSpeed();
            }
            count += mv.getMary().getxSpeed();
        }
        else if(mv.getMary().getState().equals("左跑")) {
            for(int i=0; i<mv.getNowLevel().getTile().size(); i++) {
                Tile t = mv.getNowLevel().getTile().get(i);
                t.x += mv.getMary().getxSpeed();
            }
            count -= mv.getMary().getxSpeed();
        }
    }


    //切图的方法
    public void ChangeImage()
    {
        this.changeTime -- ;
        switch(type)
        {
            case 21:
                this.image = LoadResource.tile.get(index);
                this.IsTimeOver();
                if(index > 11 ) index = 8;
                break;

            case 17:
                this.image = LoadResource.tile.get(index);
                this.IsTimeOver();
                if(index > 34 ) index = 31;
                break;
        }
    }

    //判断是否到时间切图了
    public void IsTimeOver()
    {
        if(this.changeTime <= 0)
        {
            this.index++;
            this.changeTime = 4;
        }
    }

}
