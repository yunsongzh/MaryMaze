package com.zys.mary.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.zys.mary.load.LoadActivity;
import com.zys.mary.load.LoadResource;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Level {

    private int col;                                         //关卡的列数
    private int Level;                                       //表示关卡数
    private ArrayList <Tile>tile = new ArrayList<Tile>();       //存储创建好的加载地图块的情况
    private ArrayList <BackMap>backmap = new ArrayList<BackMap>();

    private Bitmap image;                                    //用于创建空白图
    private static Canvas canvas;                            //在空白图上画图

    public ArrayList<BackMap> getBackmap() {
        return backmap;
    }

    public ArrayList<Tile> getTile() {
        return tile;
    }

    public int getCol() {
        return col;
    }

    public Level(int level, Context context){
        this.Level = level;
        switch (level){
            case 1:
                //创建地图
                this.CreatTile(this.ReadMap(context, "mapdat/map.dat"));

                //创建一张空白的图片(乘以15是看地图块有多少列，
                // 因为地图块为300列（20列为一个屏幕（320X240））)
                image = Bitmap.createBitmap(LoadActivity.ScreenWidth*15,
                        LoadActivity.ScreenHeight, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(image);
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(LoadResource.map.get(level),
                        0, 0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth,   0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*2, 0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*3, 0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*4, 0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*5,   0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*6, 0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*7, 0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*8, 0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*9, 0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*10,   0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*11, 0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*12, 0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*13, 0 ,null);
                canvas.drawBitmap(LoadResource.map.get(level),
                        LoadActivity.ScreenWidth*14, 0 ,null);

                this.backmap.add(new BackMap(0,0,image));

                break;
        }
    }

    //创建地图块的方法
    public void CreatTile(int map[][])
    {
        for(int i=0; i<map.length; i++)
        {
            for(int j=0; j<map[i].length; j++)
            {
                if(map[i][j] > 0)
                {
                    Tile t = new Tile(j*16, i*16,SelectImage(map[i][j]),map[i][j]);
                    this.tile.add(t);
                }
            }
        }
    }

    //根据地图块的下标值返回图片
    public Bitmap SelectImage(int index)
    {
        if(index == 1)  return LoadResource.tile.get(0);
        if(index == 6)  return LoadResource.tile.get(1);
        if(index == 12)  return LoadResource.tile.get(2);
        if(index == 13)  return LoadResource.tile.get(3);
        if(index == 14)  return LoadResource.tile.get(4);
        if(index == 15)  return LoadResource.tile.get(5);
        if(index == 25)  return LoadResource.tile.get(6);
        if(index == 28)  return LoadResource.tile.get(7);
        if(index == 34)  return LoadResource.tile.get(8);
        if(index == 41)  return LoadResource.tile.get(9);
        if(index == 45)  return LoadResource.tile.get(10);
        if(index == 46)  return LoadResource.tile.get(11);
        if(index == 265)  return LoadResource.tile.get(12);
        if(index == 266)  return LoadResource.tile.get(13);
        if(index == 298)  return LoadResource.tile.get(14);
        if(index == 299)  return LoadResource.tile.get(15);
        if(index == 267) return LoadResource.tile.get(16);
        if(index == 268) return LoadResource.tile.get(17);
        if(index == 300) return LoadResource.tile.get(18);
        if(index == 301) return LoadResource.tile.get(19);
        if(index == 278) return LoadResource.tile.get(20);
        if(index == 279) return LoadResource.tile.get(21);
        if(index == 312) return LoadResource.tile.get(22);
        return null;
    }

    //读取地图块文件
    public int[][] ReadMap(Context context, String path)
    {
        int map[][] = null;
        try
        {
            InputStream in = context.getResources().getAssets().open(path);
            DataInputStream dis = new DataInputStream(in);

            int row = dis.readInt();
            int col = dis.readInt();

            this.col = col;                              //保存关卡的列数

            map = new int[row][col];

            for(int i=0; i<map.length; i++)
            {
                for(int j=0; j<map[i].length; j++)
                {
                    map[i][j] = dis.readInt();
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return map;

    }

}
