package com.zys.mary.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.SurfaceHolder;

import java.io.IOException;

import game.image.Image;
import game.view.GameView;

public class LoadView extends GameView implements Runnable{

    private Bitmap red,yellow;                             //红色和黄色进度条
    private int x, y;                                      //进度条绘制坐标
    private int loadImageValue = 78;                       //需要加载的图片数量
    private float width;                                   //显示黄色进度条的可视宽度

    public static Typeface mFace;                          //定义字体对象
    private int textSize = 40;                             //画笔的大小
    //画笔的透明度，用数字表示
    private int alpha[] = {255,255,255,255,255,255,255,255,255,255,230,210,190,170,150,130,
            110,90,70,50,30,10,0,10,30,50,70,90,110,130,150,170,190,210,230};
    private int index;                                     //alpha数组下标

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
        //计算每加载一张图片，黄色进度条的可视区域的值
        this.width = (float)yellow.getWidth()/loadImageValue;
        //实例化自定义字体
        mFace = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/font.ttf");
        paint.setTypeface(mFace);                          //设置绘制自定义字体
        paint.setTextSize(textSize);                       //设置绘制画笔的大小
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

            //设置字体的渐变效果
            paint.setColor(Color.YELLOW);
            paint.setAlpha(alpha[index++]);
            if(index > alpha.length - 1)
            {
                index = 0;
            }
            //绘制字体
            canvas.drawText("loading......", x, y-10, paint);
            //paint.setColor(Color.YELLOW);
            //canvas.drawText("" + LoadResource.temp, 50, 50, paint);
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
