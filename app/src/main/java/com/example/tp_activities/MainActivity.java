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

    BackgroundService service;

    Button start;
    Button connexion;
    Button deconnexion;
    Button stop;

    EditText txt;

    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Init view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init intent service
        in = new Intent(this, BackgroundService.class);

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
            BackgroundService = (LocalService.lo)
        }
        else if (connexion.equals((Button)view)){

        }
        else if (deconnexion.equals((Button) view)){

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
