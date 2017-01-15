package com.example.nghi.music.Activity.eventBus.event;

/**
 * Created by Nghi on 1/13/2017.
 */

public class SendTimerEvent {
    private int timer;

    public SendTimerEvent(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
