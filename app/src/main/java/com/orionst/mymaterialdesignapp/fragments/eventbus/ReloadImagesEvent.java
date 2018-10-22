package com.orionst.mymaterialdesignapp.fragments.eventbus;

public class ReloadImagesEvent {

    public final boolean reload;

    public ReloadImagesEvent(boolean reload) {
        this.reload = reload;
    }
}