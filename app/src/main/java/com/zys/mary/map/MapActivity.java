package com.zys.mary.map;

import android.os.Bundle;

import game.activity.GameActivity;

public class MapActivity extends GameActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.SetScreenToFull();
        super.onCreate(savedInstanceState);
        super.setContentView(new MapView(this));
    }
}
