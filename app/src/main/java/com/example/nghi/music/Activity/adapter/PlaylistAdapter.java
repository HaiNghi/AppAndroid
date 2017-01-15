package com.example.nghi.music.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.example.nghi.music.Activity.object.Album;
import com.example.nghi.music.Activity.object.PlayList;
import com.example.nghi.music.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nghi on 1/7/17.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.AdapterViewHolder> {
    private List<PlayList> mPlayList;
    private List<PlayList> mListFiltered;
    private Context mContext;
    private LayoutInflater mInflater;
    private Search filter;
    private MenuItem.OnMenuItemClickListener listener;

    public PlaylistAdapter(List<PlayList> playLists, Context context) {
        this.mContext=context;
        this.mPlayList =playLists;
        this.mListFiltered=playLists;
        this.mInflater=LayoutInflater.from(context);
    }

    public void setOnMenuItemClick(MenuItem.OnMenuItemClickListener listener){
        this.listener = listener;
    }
    public Filter getFilter() {
        if (filter==null) filter=new Search();
        return filter;
    }
    public PlayList getPlayList(int position){
        return mListFiltered.get(position);
    }



    @Override
    public PlaylistAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_new_list,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new PlaylistAdapter.AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaylistAdapter.AdapterViewHolder holder, int position) {
        PlayList playList= mListFiltered.get(position);

        holder.mTvPlayList.setText(playList.getNamePlayList());
        holder.mTVSongNumber.setText(playList.getNumberOfSong()+" songs");
    }

    @Override
    public int getItemCount() {
        if(mListFiltered == null) return 0;
        return mListFiltered.size();
    }



    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView mTvPlayList;
        TextView mTVSongNumber;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnCreateContextMenuListener(this);
            mTvPlayList = (TextView) itemView.findViewById(R.id.tvNameList);
            mTVSongNumber = (TextView) itemView.findViewById(R.id.tvNumberOfSong);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem play = menu.add(0,0,0,"Play all");
            MenuItem rename = menu.add(0,1,1,"Rename");
            MenuItem add= menu.add(0,2,2,"Add song");
            MenuItem delete= menu.add(0,3,3,"Delete");
            play.setOnMenuItemClickListener(listener);
            add.setOnMenuItemClickListener(listener);
            rename.setOnMenuItemClickListener(listener);
            delete.setOnMenuItemClickListener(listener);

        }
    }
    class Search extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();

            if(constraint!=null && constraint.length()>0){
                List<PlayList> temp=new ArrayList<>();
                for(PlayList playList:mPlayList){
                    if(playList.getNamePlayList().toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        temp.add(playList);
                    }

                }
                filterResults.count=temp.size();
                filterResults.values=temp;
            } else{
                filterResults.count=mPlayList.size();
                filterResults.values=mPlayList;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mListFiltered= (List<PlayList>) results.values;
            notifyDataSetChanged();
        }

    }
}
