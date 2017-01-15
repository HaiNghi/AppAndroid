package com.example.nghi.music.Activity.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseApp extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initUI();
        initData();
        addListener();
    }
    protected abstract void setContentView();
    protected abstract void initUI();
    protected abstract void initData();
    protected abstract void addListener();
}
