package com.example.nghi.music.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.example.nghi.music.Activity.object.Album;
import com.example.nghi.music.Activity.object.Music;
import com.example.nghi.music.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nghi on 1/5/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AdapterViewHolder> {
    private List<Album> mAlbumList;
    private List<Album> mListFiltered;
    private Context mContext;
    private LayoutInflater mInflater;
    private Search filter;

    public AlbumAdapter(List<Album> albumList, Context context) {
        this.mContext=context;
        this.mAlbumList =albumList;
        this.mListFiltered=albumList;
        this.mInflater=LayoutInflater.from(context);
    }
    public Filter getFilter() {
        if (filter==null) filter=new Search();
        return filter;
    }

    public Album getAlbums(int position){
        return mListFiltered.get(position);
    }

    @Override
    public AlbumAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_list_album,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new AlbumAdapter.AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumAdapter.AdapterViewHolder holder, int position) {
        Album album= mListFiltered.get(position);
        holder.mTvAlbum.setText(album.getAlbumName());
        holder.mTvSinger.setText(album.getSinger());
    }

    @Override
    public int getItemCount() {
        if(mListFiltered == null) return 0;
        return mListFiltered.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView mTvAlbum;
        TextView mTvSinger;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            mTvAlbum = (TextView) itemView.findViewById(R.id.tvAlbum);
            mTvSinger = (TextView) itemView.findViewById(R.id.tvSinger);
        }
    }
    class Search extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();

            if(constraint!=null && constraint.length()>0){
                List<Album> temp=new ArrayList<>();
                for(Album album:mAlbumList){
                    if(album.getAlbumName().toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        temp.add(album);
                    }

                }
                filterResults.count=temp.size();
                filterResults.values=temp;
            } else{
                filterResults.count=mAlbumList.size();
                filterResults.values=mAlbumList;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mListFiltered= (List<Album>) results.values;
            notifyDataSetChanged();
        }

    }
}
