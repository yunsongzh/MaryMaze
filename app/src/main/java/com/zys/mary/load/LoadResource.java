package com.zys.mary.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import game.image.Image;

public class LoadResource {
    //mary图片集合
    public static ArrayList <Bitmap>mary = new ArrayList<Bitmap>();

    //敌人图片集合
    public static ArrayList <Bitmap>enemy = new ArrayList<Bitmap>();

    //金币品图片集合
    public static ArrayList <Bitmap>coin = new ArrayList<Bitmap>();

    //爆炸图片集合
    public static ArrayList <Bitmap>blast = new ArrayList<Bitmap>();

    //食物图片集合
    public static ArrayList <Bitmap>food = new ArrayList<Bitmap>();

    //地图图片集合
    public static ArrayList <Bitmap>map = new ArrayList<Bitmap>();

    //地图块图片集合
    public static ArrayList <Bitmap>tile = new ArrayList<Bitmap>();

    //武器图片集合
    public static ArrayList <Bitmap>weapon = new ArrayList<Bitmap>();

    //ui图片集合
    public static ArrayList <Bitmap>ui = new ArrayList<Bitmap>();

    //记录加载了多少张图片
    public static int temp;

    public  static void loadBitmap(Context context){
        try
        {
            //加载mary图片
            for(int i=1; i<=13; i++)
            {
                mary.add(BitmapFactory.decodeStream(context.getAssets().open
                        ("mario/mario" + i + ".png")));
                temp++;
            }

            //加载敌人图片
            for(int i=1; i<=12; i++)
            {
                enemy.add(BitmapFactory.decodeStream(context.getAssets().open
                        ("enemy/enemy" + i + ".png")));
                temp++;
            }

            //加载金币图片
            for(int i=1; i<=4; i++)
            {
                coin.add(BitmapFactory.decodeStream(context.getAssets().open
                        ("coin/coin" + i + ".png")));
                temp++;
            }

            //加载爆炸图片
            for(int i=1; i<=3; i++)
            {
                blast.add(BitmapFactory.decodeStream(context.getAssets().open
                        ("blast/blast" + i + ".png")));
                temp++;
            }

            //加载食物图片
            for(int i=1; i<=3; i++)
            {
                food.add(BitmapFactory.decodeStream(context.getAssets().open
                        ("food/food" + i + ".png")));
                temp++;
            }

            //加载地图图片
            for(int i=1; i<=4; i++)
            {
                Bitmap m = BitmapFactory.decodeStream(context.getAssets().open
                        ("map/map" + i +".jpg"));
                temp++;

                //把背景图设置与屏幕大小相同
                m = Image.FitTheScreenSizeImage(m, LoadActivity.ScreenWidth, LoadActivity
                        .ScreenHeight);
                map.add(m);
            }

            //加载地图块图片
            for(int i=1; i<=35; i++)
            {
                tile.add(BitmapFactory.decodeStream(context.getAssets().open
                        ("tile/tile" + i + ".png")));
                temp++;
            }

            //加载武器图片
            for(int i=1; i<=2; i++)
            {
                weapon.add(BitmapFactory.decodeStream(context.getAssets().open
                        ("weapon/weapon" + i + ".png")));
                temp++;
            }

            //加载UI图片
            for(int i=1; i<=2; i++)
            {
                ui.add(BitmapFactory.decodeStream(context.getAssets().open
                        ("ui/ui" + i + ".png")));
                temp++;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
