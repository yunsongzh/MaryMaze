package com.zys.mary.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.KeyEvent;

import com.zys.mary.load.LoadActivity;
import com.zys.mary.load.LoadResource;

import game.sprite.Sprite;

public class Mary extends Sprite {

    private int level;                                    //人物等级
    private int xSpeed,ySpeed;                            //X轴和Y轴的移动速度
    private int lifevalue;                                //生命数
    private String state;                                 //记录当前状态,向左还是向右
    private int index;                                    //切图下标
    private int changeTime;                               //切图间隔

    public Mary(float x, float y, Bitmap image) {
        super(x, y, image);
        this.level = 1;
        this.hp = 1;
        this.lifevalue = 3;
        this.xSpeed = 4;
        this.changeTime = 2;
        this.state = "右停";
    }

    public String getState() {
        return state;
    }

    public int getxSpeed() {

        return xSpeed;
    }

    public void Draw(Canvas canvas) {
        if(this.state.indexOf("左") != -1) {
            canvas.save();
            canvas.scale(-1, 1, this.x+this.image.getWidth()/2,
                    this.y+this.image.getHeight()/2);
            canvas.drawBitmap(image, x, y, null);
            canvas.restore();
        }
        else
        {
            canvas.drawBitmap(image, x, y, null);
        }
    }

    //移动方法
    public void Move(MapView mv,int col) {
        if(this.hp < 0) return;

        if(this.state.equals("右跑")) {
            if(this.x < LoadActivity.ScreenWidth/2) {
                this.x += this.xSpeed;
            }
            else {
                //25列图块*16-屏幕的宽度（因为一开始左半部分和最后的右半部分不算）
                if(Tile.count != col*16-LoadActivity.ScreenWidth) {
                    Tile.Move(mv);
                    //背景地图左移
                    mv.getNowLevel().getBackmap().get(0).Move(2);
                } else {
                    if(this.x < LoadActivity.ScreenWidth - this.image.getWidth()) {
                        this.x += this.xSpeed;
                    }
                }
            }
        }
        else if(this.state.equals("左跑")) {
            if(this.x > LoadActivity.ScreenWidth/2) {
                this.x -=this.xSpeed;
            }
            else {
                if(Tile.count != 0) {
                    Tile.Move(mv);
                    //背景地图右移
                    mv.getNowLevel().getBackmap().get(0).Move(1);
                } else {
                    if(this.x > 0) {
                        this.x -=this.xSpeed;
                    }
                }
            }
        }
    }

    //切图
    public void ChangeImage() {
        if(this.hp < 0) return;
        this.changeTime --;

        //移动
        if(this.state.equals("右跑")  || this.state.equals("左跑")) {
            if(this.level == 1)
            {
                this.image = LoadResource.mary.get(index);
                if(this.changeTime <= 0)
                {
                    index++;
                    this.changeTime = 2;
                }
                if(index == 2) index=0;
            }
        }
        //停止
        else if(this.state.equals("右停") || this.state.equals("左停") ) {
            if(this.level == 1)
            {
                this.image = LoadResource.mary.get(0);
            }
        }

    }

    //按键按下事件
    public void onKeyDown(int keyCode, KeyEvent event) {
        if(this.hp < 0) return ;
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT :
                this.state = "左跑";
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT :
                this.state = "右跑";
                break;
        }

    }


    //按键抬起事件
    public void onKeyUp(int keyCode, KeyEvent event) {
        if(this.hp < 0) return ;
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT :
                this.state = "左停";
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT :
                this.state = "右停";
                break;
        }
    }

}
