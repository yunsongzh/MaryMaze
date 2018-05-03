package com.zys.mary.options;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.SeekBar;

import com.zys.mary.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import game.activity.GameActivity;
import game.music.GameMediaPlayers;
import game.music.GameSoundPool;

public class OptionsActivity extends GameActivity implements
        SeekBar.OnSeekBarChangeListener {

    private SeekBar musicSeekBar,soundSeekBar;                       //进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.SetScreenToFull();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        //取得进度条对象
        musicSeekBar = (SeekBar)findViewById(R.id.mySeekBar1);
        soundSeekBar = (SeekBar)findViewById(R.id.mySeekBar2);
        //设置监听
        musicSeekBar.setOnSeekBarChangeListener(this);
        soundSeekBar.setOnSeekBarChangeListener(this);
        //读取保存的进度条的值
        try {
            FileInputStream fis = this.openFileInput("seMusic.txt");
            DataInputStream dis = new DataInputStream(fis);
            this.musicSeekBar.setProgress(dis.readInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fis = this.openFileInput("setSound.txt");
            DataInputStream dis = new DataInputStream(fis);
            this.soundSeekBar.setProgress(dis.readInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //对进度条设置的事件
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (seekBar == musicSeekBar){
            //MediaPlayer的最大声音为15
            GameMediaPlayers.SetVolumeSize(this,progress/7);
            //保存设置的进度条的值
            try {
                FileOutputStream fos = this.openFileOutput("setMusic",
                        Context.MODE_PRIVATE);
                DataOutputStream dos = new DataOutputStream(fos);
                dos.writeInt(progress);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else if (seekBar == soundSeekBar){
            //设置游戏音效(SoundPool最大值是1)
            new GameSoundPool().setVolume((float) (progress/10*0.1));
            //保存设置的进度条的值
            try {
                FileOutputStream fos = this.openFileOutput("setSound",
                        Context.MODE_PRIVATE);
                DataOutputStream dos = new DataOutputStream(fos);
                dos.writeInt(progress);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
    //返回时的事件响应
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return true;
    }
}
