package com.example.nghi.music.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.nghi.music.Activity.object.Music;
import com.example.nghi.music.R;

import java.util.List;

/**
 * Created by Nghi on 1/5/17.
 */

public class SongForPlayListAdapter extends RecyclerView.Adapter<SongForPlayListAdapter.AdapterViewHolder>{
    private List<Music> mMusicList;
    private List<Music> mListFiltered;
    private Context mContext;
    private LayoutInflater mInflater;
    private CompoundButton.OnCheckedChangeListener listener;

    public SongForPlayListAdapter(List<Music> musicList, Context context) {
        this.mContext=context;
        this.mMusicList=musicList;
        this.mInflater=LayoutInflater.from(context);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener){
        this.listener = listener;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_list_song_in_playlist,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        Music music=mMusicList.get(position);
        holder.mTvTitle.setText(music.getName());
        holder.mCheckBox.setChecked(false);
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(listener);
    }

    @Override
    public int getItemCount() {
        if(mMusicList == null) return 0;
        return mMusicList.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        CheckBox mCheckBox;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }

    }
}
