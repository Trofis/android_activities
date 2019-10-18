package com.example.tp_activities;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class BackgroundService extends Service implements IBackgroundService {
    private Timer timer;
    private boolean onStart;
    private BackgroundServiceBinder binder;
    private ArrayList<IBackgroundServiceListener> listeners;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        binder = new BackgroundServiceBinder(this);
        listeners = new ArrayList<>();
        Log.d(this.getClass().getName(), "onCreate");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.d(this.getClass().getName(), "onStart");
        onStart = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                LocalDateTime ldt;
                while (onStart){
                    ldt = LocalDateTime.now();
                    ldt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                    Log.d("Heure : ",ldt.toString());

                    fireDataChanged(ldt);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 60000);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        this.listeners.clear();
        Log.d(this.getClass().getName(), "onDestroy");
        this.timer.cancel();
        onStart = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void addListener(IBackgroundServiceListener listener) {
        if (listeners != null){
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(IBackgroundServiceListener listener) {
        if (listeners != null){
            listeners.remove(listener);
        }
    }

    private void fireDataChanged(Object data){
        if (listeners != null){
            for (IBackgroundServiceListener listener : listeners){
                listener.dataChanged(data);
            }
        }
    }
}