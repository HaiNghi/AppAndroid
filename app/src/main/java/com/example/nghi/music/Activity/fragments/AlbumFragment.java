package com.example.nghi.music.Activity.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nghi.music.Activity.activity.SongActivity;
import com.example.nghi.music.Activity.adapter.AlbumAdapter;
import com.example.nghi.music.Activity.adapter.RecycleTouchListener;
import com.example.nghi.music.Activity.object.Album;
import com.example.nghi.music.Activity.object.Music;
import com.example.nghi.music.Activity.service.Constant;
import com.example.nghi.music.Activity.utils.DatabaseUtil;
import com.example.nghi.music.R;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends BaseFragment {
    private List<Album> mAlbumList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private AlbumAdapter mAdapter;

    public AlbumFragment() {
        // Required empty public constructor
    }
    public void search(String str){
        mAdapter.getFilter().filter(str);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDbUtil=new DatabaseUtil();
        final View view = inflater.inflate(R.layout.fragment_album, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_album);
        mRecyclerView.setHasFixedSize(true);
        mAlbumList = mDbUtil.getAlbum(getContext());
        mAdapter = new AlbumAdapter(mAlbumList, view.getContext());
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecycleTouchListener(getContext(), mRecyclerView, new RecycleTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getContext(), SongActivity.class);
                intent.putExtra(Constant.KEY, mAdapter.getAlbums(position));
                intent.putExtra(Constant.TAG,Constant.TAG_ALBUMS);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;


    }



}
