package com.example.nghi.music.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.example.nghi.music.Activity.object.Music;
import com.example.nghi.music.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nghi on 1/5/17.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.AdapterViewHolder>{
    private List<Music> mMusicList;
    private List<Music> mListFiltered;
    private Context mContext;
    private LayoutInflater mInflater;
    private Search filter;

    public SongAdapter(List<Music> musicList, Context context) {
        this.mContext=context;
        this.mMusicList=musicList;
        this.mListFiltered=musicList;
        this.mInflater=LayoutInflater.from(context);
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_list_song,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new AdapterViewHolder(view);
    }
    public Filter getFilter() {
        if (filter==null) filter=new Search();
        return filter;
    }
    public int getPositionPlay(int position){
        if (mMusicList.size() == mListFiltered.size())
             return position;
        Music music = mListFiltered.get(position);
            return mMusicList.indexOf(music);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        Music music=mListFiltered.get(position);
        holder.mTvName.setText(music.getName());
        holder.mTvSinger.setText(music.getSinger());
    }

    @Override
    public int getItemCount() {
        if(mListFiltered == null) return 0;
        return mListFiltered.size();
    }

    public Music getItem(int position) {
        return mListFiltered.get(position);
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        TextView mTvSinger;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvSinger = (TextView) itemView.findViewById(R.id.tvSinger);
        }

    }
    class Search extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();

            if(constraint!=null && constraint.length()>0){
                List<Music> temp=new ArrayList<>();
                for(Music music:mMusicList){
                    if(music.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        temp.add(music);
                    }

                }
                filterResults.count=temp.size();
                filterResults.values=temp;
            } else{
                filterResults.count=mMusicList.size();
                filterResults.values=mMusicList;
            }
            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mListFiltered= (List<Music>) results.values;
            notifyDataSetChanged();
        }

    }
}
