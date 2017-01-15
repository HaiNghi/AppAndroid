package com.example.nghi.music.Activity.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.RemoteViews;

import com.example.nghi.music.Activity.object.Music;
import com.example.nghi.music.Activity.service.Constant;
import com.example.nghi.music.Activity.service.MusicBroadcast;
import com.example.nghi.music.Activity.service.MusicService;
import com.example.nghi.music.R;

/**
 * Created by Nghi on 1/13/2017.
 */

public class SongNotification {
    private static SongNotification mPlaySongNotification;
    private RemoteViews mRemoteViews;
    private Notification mNotification;
    public static SongNotification getInstance(){
        if (mPlaySongNotification == null){
            mPlaySongNotification = new SongNotification();
        }
        return mPlaySongNotification;
    }

    boolean currentVersionSupportBigNotification = currentVersionSupportBigNotification();

    public static boolean currentVersionSupportBigNotification() {
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        if(sdkVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
            return true;
        }
        return false;
    }
    public void showNotification(Context context, MusicService service, Music music){
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_play);
        mRemoteViews.setImageViewResource(R.id.imgSong,R.drawable.ic_launch);
        Intent intent =new Intent(context, MusicBroadcast.class);
        intent.setAction(Constant.ACTION_SHOW_ACTIVITY);
        PendingIntent pendingIntent =PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intentPrevious = new Intent(context,MusicBroadcast.class);
        intentPrevious.setAction(Constant.ACTION_PREV);
        PendingIntent pendingIntentPrev =PendingIntent.getBroadcast(context,0,intentPrevious,0);

        Intent intentNext= new Intent(context,MusicBroadcast.class);
        intentNext.setAction(Constant.ACTION_NEXT);
        PendingIntent pendingIntentNext =PendingIntent.getBroadcast(context,0,intentNext,0);

        Intent intentPlay= new Intent(context,MusicBroadcast.class);
        intentPlay.setAction(Constant.ACTION_PLAY);
        PendingIntent pendingIntentPlay =PendingIntent.getBroadcast(context,0,intentPlay,0);

        mRemoteViews.setOnClickPendingIntent(R.id.imgBtnPrevious,pendingIntentPrev);
        mRemoteViews.setOnClickPendingIntent(R.id.imgBtnNext,pendingIntentNext);
        mRemoteViews.setOnClickPendingIntent(R.id.imgBtnPlay,pendingIntentPlay);
        mRemoteViews.setOnClickPendingIntent(R.id.rl_notify,pendingIntent);
        mRemoteViews.setTextViewText(R.id.tvNameSong, music.getName());
        mRemoteViews.setTextViewText(R.id.tvNameSinger,music.getSinger());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotification = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification))
                    .setContentIntent(pendingIntent)
                    .build();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotification.bigContentView= mRemoteViews;
        }
        mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
        service.startForeground(Constant.FOREGROUND_SERVICE, mNotification);


    }

}
