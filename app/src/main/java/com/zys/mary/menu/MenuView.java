package com.zys.mary.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.zys.mary.R;
import com.zys.mary.load.LoadActivity;
import com.zys.mary.load.LoadResource;
import com.zys.mary.load.LoadView;
import com.zys.mary.options.OptionsActivity;

import game.button.GameButton;
import game.music.GameMediaPlayers;
import game.view.GameView;

public class MenuView extends GameView implements Runnable{

    private int x1,x2 = LoadActivity.ScreenWidth;                    //背景图片的绘制坐标
    private GameButton StartGame,Options,Exit;                       //游戏菜单按钮对象
    private int textSize = 50;

    //编辑开机动画和菜单状态切换
    public static final int UI = 1;
    public static final int BUTTON = 2;
    private int menuState = UI;

    //UI上下移动的数组
    private int move[] = {-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,
            7,6,5,4,3,2,1,0,-1,-2,-3,-4,-5,-6,-7};
    private int index;                                               //标记move数组
    private GameMediaPlayers gm;                                     //音乐对象


    public MenuView(Context context) {
        super(context);
        //按钮初始化
        StartGame = new GameButton((LoadActivity.ScreenWidth - 100)/2,
                LoadActivity.ScreenHeight/2 - 40, textSize,"Start Game",
                context, R.raw.button);
        Options   = new GameButton(StartGame.x, StartGame.y  + 60, textSize,
                "Options",  context, R.raw.button);
        Exit      = new GameButton(StartGame.x, Options.y   + 60, textSize,
                "Exit",     context, R.raw.button);
        paint.setTypeface(LoadView.mFace);                          //设置绘制自定义字体
        gm = new GameMediaPlayers();                                //加载音乐
        gm.LoadMusic(context, R.raw.menu);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        gm.StartMusic(true);                                 //播放音乐
        this.flag = true;
        this.t = new Thread(this);
        this.t.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        gm.PuaseMusic(false);                                //音乐暂停
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
            //切换开始动画和菜单显示
            switch(menuState) {
                case UI:
                    canvas.drawBitmap(LoadResource.ui.get(1),
                            (LoadActivity.ScreenWidth - LoadResource.ui.get(1)
                                    .getWidth())/2,
                            LoadActivity.ScreenHeight/7 - move[index++],
                            null);
                    canvas.drawBitmap(LoadResource.ui.get(0),
                            (LoadActivity.ScreenWidth - LoadResource.ui.get(0)
                                    .getWidth())/2,
                            LoadActivity.ScreenHeight/2,
                            null);
                    if(index > move.length - 1)
                    {
                        index = 0;
                    }
                    break;
                case BUTTON:
                    this.StartGame.Draw(canvas, paint);
                    this.Options.Draw(canvas, paint);
                    this.Exit.Draw(canvas, paint);
                    break;
            }
            this.sh.unlockCanvasAndPost(canvas);
        }

    }

    //设置按钮触摸响应
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (menuState){
            case UI:
                this.menuState = BUTTON;
                break;
            case BUTTON:
                if(this.StartGame.OnTouch(event.getX(), event.getY())) {
                }
                else if(this.Options.OnTouch(event.getX(), event.getY())) {
                    Intent i = new Intent(this.getContext(), OptionsActivity.class);
                    this.getContext().startActivity(i);
                }
                else if(this.Exit.OnTouch(event.getX(), event.getY())) {
                    Activity a = (Activity) this.getContext();
                    a.finish();
                    this.flag = false;
                    System.exit(0);
                }
                break;
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
