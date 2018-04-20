package com.zys.mary.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.SurfaceHolder;

import java.io.IOException;

import game.image.Image;
import game.view.GameView;

public class LoadView extends GameView implements Runnable{

    private Bitmap red,yellow;                            //红色和黄色进度条
    private int x, y;                                     //进度条绘制坐标
    private int loadImageValue = 78;                       //需要加载的图片数量
    private float width;                                  //显示黄色进度条的可视宽度

    public LoadView(Context context)
    {
        super(context);
        this.setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);

        //加载并缩放进度条图片
        try
        {
            red = BitmapFactory.decodeStream(context.getAssets().open
                    ("progressbar/progressbar1.png"));
            red = Image.FitTheImage(red,(float)LoadActivity.
                    ScreenWidth/(red.getWidth()*2), 1.0f);
            yellow = BitmapFactory.decodeStream(context.getAssets().open
                    ("progressbar/progressbar2.png"));
            yellow = Image.FitTheImage(yellow,(float)LoadActivity.
                    ScreenWidth/(yellow.getWidth()*2), 1.0f);

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.x = (LoadActivity.ScreenWidth - red.getWidth())/2;
        this.y =  LoadActivity.ScreenHeight - 30;
        //计算每加载一张图片，矩形设置的可视区域的值
        this.width = (float)yellow.getWidth()/loadImageValue;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        this.flag = true;
        this.t = new Thread(this);
        this.t.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        this.flag = false;
    }

    public void Draw()
    {
        this.canvas = sh.lockCanvas();
        if(canvas != null)
        {
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(red, x, y, null);            //绘制红色进度条
            canvas.save();                                       //绘制黄色进度条
            canvas.clipRect(x, y, x + LoadResource.temp * width,
                    y + yellow.getHeight());
            canvas.drawBitmap(yellow, x, y, null);
            canvas.restore();
            //paint.setColor(Color.YELLOW);
            //canvas.drawText(" " + LoadResource.ui.size(), 50, 50, paint);
            this.sh.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return true;
    }

    @Override
    public void run()
    {
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
