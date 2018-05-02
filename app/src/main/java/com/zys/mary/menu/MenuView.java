package com.zys.mary.menu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.zys.mary.R;
import com.zys.mary.load.LoadActivity;
import com.zys.mary.load.LoadResource;
import com.zys.mary.load.LoadView;

import game.button.GameButton;
import game.view.GameView;

public class MenuView extends GameView implements Runnable{

    private int x1,x2 = LoadActivity.ScreenWidth;                    //背景图片的绘制坐标
    private GameButton StartGame,Options,Exit;                       //游戏菜单按钮对象
    private int textSize = 20;

    public MenuView(Context context) {
        super(context);
        //按钮初始化
        StartGame = new GameButton((LoadActivity.ScreenWidth - 100)/2 , LoadActivity
                .ScreenHeight/2 - 40, textSize,"Start_Game", context, R.raw.button);
        Options   = new GameButton(StartGame.x, StartGame.y  + 60, textSize,
                "Options",  context, R.raw.button);
        Exit      = new GameButton(StartGame.x, Options.y   + 60, textSize,
                "Exit",     context, R.raw.button);
        paint.setTypeface(LoadView.mFace);                          //设置绘制自定义字体
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

    public void Draw(){
        this.canvas = sh.lockCanvas();
        if (canvas != null){
            paint.setColor(Color.YELLOW);
            //绘制背景图片
            canvas.drawBitmap(LoadResource.map.get(0), x1-=2, 0, null);
            canvas.drawBitmap(LoadResource.map.get(0), x2-=2 ,0, null);
            if(x1 <= -LoadActivity.ScreenWidth)
            {
                x1 = LoadActivity.ScreenWidth;
            }
            if(x2 <= -LoadActivity.ScreenWidth)
            {
                x2 = LoadActivity.ScreenWidth;
            }
            this.StartGame.Draw(canvas, paint);
            this.Options.Draw(canvas, paint);
            this.Exit.Draw(canvas, paint);
            this.sh.unlockCanvasAndPost(canvas);
        }

    }

    //设置按钮触摸响应
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(this.StartGame.OnTouch(event.getX(), event.getY()))
        {
        }
        else if(this.Options.OnTouch(event.getX(), event.getY()))
        {
        }
        else if(this.Exit.OnTouch(event.getX(), event.getY())) {
            Activity a = (Activity) this.getContext();
            a.finish();
            this.flag = false;
            System.exit(0);
        }
        return super.onTouchEvent(event);
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
