package com.example.tp_activities;

public interface IBackgroundService {

    public void addListener(IBackgroundServiceListener listener);
    public void removeListener(IBackgroundServiceListener listener);
}
