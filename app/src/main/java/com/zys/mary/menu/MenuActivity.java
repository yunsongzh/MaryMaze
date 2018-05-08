package com.zys.mary.menu;

import android.os.Bundle;
import game.activity.GameActivity;

public class MenuActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.SetScreenToFull();
        setContentView(new MenuView(this));
    }
}
