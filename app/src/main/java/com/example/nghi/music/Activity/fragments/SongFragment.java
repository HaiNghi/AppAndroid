package com.example.nghi.music.Activity.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nghi.music.Activity.activity.PlaySongActivity;
import com.example.nghi.music.Activity.adapter.RecycleTouchListener;
import com.example.nghi.music.Activity.adapter.SongAdapter;
import com.example.nghi.music.Activity.eventBus.BusProvider;
import com.example.nghi.music.Activity.eventBus.event.SendActionEvent;
import com.example.nghi.music.Activity.eventBus.event.SendListMusicEvent;
import com.example.nghi.music.Activity.eventBus.event.SendPositionEvent;
import com.example.nghi.music.Activity.object.Music;
import com.example.nghi.music.Activity.service.Constant;
import com.example.nghi.music.Activity.utils.DatabaseUtil;
import com.example.nghi.music.R;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

public class SongFragment extends BaseFragment {
    private List<Music> mMusics = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private SongAdapter mAdapter;
    private Bus mBus;

    public SongFragment() {
    }

    public void search(String str){
        mAdapter.getFilter().filter(str);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_song, container, false);
        mDbUtil = new DatabaseUtil();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_song);
        mRecyclerView.setHasFixedSize(true);
        mMusics = ((SendListMusicEvent) getArguments().getSerializable(Constant.KEY_SEND_TO_MAIN)).getMusics();
        //  mMusicList=mDbUtil.getMusic(getContext());
        mAdapter = new SongAdapter(mMusics, view.getContext());
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mBus = BusProvider.getInstance();

        mRecyclerView.addOnItemTouchListener(new RecycleTouchListener(getContext(), mRecyclerView, new RecycleTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mBus.post(new SendListMusicEvent(mMusics));
                mBus.post(new SendPositionEvent(mAdapter.getPositionPlay(position)));
                mBus.post(new SendActionEvent(Constant.PLAY));
                mBus.post(new SendActionEvent(Constant.START_ACTIVITY));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }


}
