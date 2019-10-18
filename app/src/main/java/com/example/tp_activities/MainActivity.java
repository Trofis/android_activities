package com.example.tp_activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, IBackgroundServiceListener {

0   Button start;
    Button connexion;
    Button deconnexion;
    Button stop;

    ServiceConnection connection;

    EditText txt;

    Intent in;

    IBackgroundService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Init view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init intent service
        in = new Intent(this, BackgroundService.class);


        IBackgroundServiceListener listener = new IBackgroundServiceListener() {
            public void dataChanged(final Object data) {
                VotreActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        dataChanged(data);
                    }
                });
            }
        };

        //Création de l’objet Connexion
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("BackgroundService", "Connected!");
                service =
                        ((BackgroundServiceBinder)service).getService();
                service.addListener(listener);
            }
            public void onServiceDisconnected(ComponentName name) {
                Log.i("BackgroundService", "Disconnected!");
            }
        };
        //Get view items
        start = findViewById(R.id.start);
        connexion = findViewById(R.id.connexion);
        deconnexion = findViewById(R.id.deconnexion);
        stop = findViewById(R.id.stop);
        txt = findViewById(R.id.txt);


        //Set listeners
        start.setOnClickListener(this);
        connexion.setOnClickListener(this);
        deconnexion.setOnClickListener(this);
        stop.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        if (start.equals((Button) view)) {
            startService(in);
        }
        else if (connexion.equals((Button)view)){
            bindService(in, connection, BIND_AUTO_CREATE);
        }
        else if (deconnexion.equals((Button) view)){
            unbindService(connection);
            service.removeListener();
        }
        else
        {
            stopService(in);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void dataChanged(Object o) {
        LocalDateTime ldt = (LocalDateTime)o;
        txt.setText(o.toString());
    }
}
