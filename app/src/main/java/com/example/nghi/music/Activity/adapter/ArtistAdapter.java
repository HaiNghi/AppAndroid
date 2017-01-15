package com.example.nghi.music.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.example.nghi.music.Activity.object.Artist;
import com.example.nghi.music.Activity.object.Music;
import com.example.nghi.music.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nghi on 1/5/17.
 */

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.AdapterViewHolder>{
    private List<Artist> mArtistList;
    private List<Artist> mListFiltered;
    private Context mContext;
    private LayoutInflater mInflater;
    private Search filter;

    public ArtistAdapter(List<Artist> artistList, Context context) {
        this.mContext=context;
        this.mArtistList=artistList;
        this.mListFiltered=artistList;
        this.mInflater= LayoutInflater.from(context);
    }
    public Filter getFilter() {
        if (filter==null) filter=new Search();
        return filter;
    }
    public Artist getArtist(int position){
        return mListFiltered.get(position);
    }

    @Override
    public ArtistAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_list_artist,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ArtistAdapter.AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistAdapter.AdapterViewHolder holder, int position) {
        Artist artist=mListFiltered.get(position);
        holder.mTvSinger.setText(artist.getNameSinger());
        holder.mTvNumberSong.setText(artist.getNumberOfSong()+" songs");
    }

    @Override
    public int getItemCount() {
        if(mListFiltered == null) return 0;
        return mListFiltered.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView mTvNumberSong;
        TextView mTvSinger;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            mTvNumberSong = (TextView) itemView.findViewById(R.id.tvNumberOfSong);
            mTvSinger = (TextView) itemView.findViewById(R.id.tvSinger);
        }
    }
    class Search extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();

            if(constraint!=null && constraint.length()>0){
                List<Artist> temp=new ArrayList<>();
                for(Artist artist:mArtistList){
                    if(artist.getNameSinger().toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        temp.add(artist);
                    }

                }
                filterResults.count=temp.size();
                filterResults.values=temp;
            } else{
                filterResults.count=mArtistList.size();
                filterResults.values=mArtistList;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mListFiltered= (List<Artist>) results.values;
            notifyDataSetChanged();
        }

    }
}
