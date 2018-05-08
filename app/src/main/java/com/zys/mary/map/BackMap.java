package com.zys.mary.map;

import android.graphics.Bitmap;

import game.sprite.Sprite;

public class BackMap extends Sprite {
    //x轴移动速度
    private int xSpeed;


    public BackMap(float x, float y, Bitmap image) {
        super(x, y, image);
    }



    public void Move(int dir) {
        if(dir == 1) {
            this.xSpeed = 4;
        } else {
            this.xSpeed = -4;
        }
        this.x+=xSpeed;
    }
}
