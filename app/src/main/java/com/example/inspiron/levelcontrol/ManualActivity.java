package com.example.inspiron.levelcontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ManualActivity extends AppCompatActivity {

    SeekBar sbKp,sbKi,sbKd;
    TextView textView,tvKp,tvKi,tvKd,editKp,editKi,editKd;
    Button send;
    int Kp,Ki,Kd;
    String IpAddress,data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        sbKp=findViewById(R.id.sbKp);
        sbKi=findViewById(R.id.sbKi);
        sbKd=findViewById(R.id.sbKd);
        textView=findViewById(R.id.textView);
        tvKp=findViewById(R.id.tvKp);
        tvKd=findViewById(R.id.tvKd);
        tvKi=findViewById(R.id.tvKi);
        editKp=findViewById(R.id.editKp);
        editKi=findViewById(R.id.editKi);
        editKd=findViewById(R.id.editKd);
        send=findViewById(R.id.send);
        IpAddress=getIntent().getStringExtra("IP_ADDRESS");
        sbKp.getProgressDrawable().setColorFilter(Color.rgb(230,255,0), PorterDuff.Mode.SRC_IN);
        sbKp.getThumb().setColorFilter(Color.rgb(230,255,0), PorterDuff.Mode.SRC_IN);
        sbKi.getProgressDrawable().setColorFilter(Color.rgb(230,255,0), PorterDuff.Mode.SRC_IN);
        sbKi.getThumb().setColorFilter(Color.rgb(230,255,0), PorterDuff.Mode.SRC_IN);
        sbKd.getProgressDrawable().setColorFilter(Color.rgb(230,255,0), PorterDuff.Mode.SRC_IN);
        sbKd.getThumb().setColorFilter(Color.rgb(230,255,0), PorterDuff.Mode.SRC_IN);



        sbKp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sbKp, int progress, boolean fromUser) {
                editKp.setText(String.valueOf(0.1*progress));
                Kp=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar sbKp) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar sbKp) {

            }
        });

        sbKi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sbKi, int progress, boolean fromUser) {
                editKi.setText(String.valueOf(0.05*progress));
                Ki=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar sbKi) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar sbKi) {

            }
        });

        sbKd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sbKd, int progress, boolean fromUser) {
                editKd.setText(String.valueOf(0.01*progress));
                Kd=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar sbKd) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar sbKd) {

            }
        });
        sendData();
    }

    public void sendData(){
        send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data=null;
                        data="Kp?prop="+String.valueOf(Kp);
                        sendDataToESP(data);
                        data=null;
                        data="Ki?integral="+String.valueOf(Ki);
                        sendDataToESP(data);
                        data=null;
                        data="Kd?diff="+String.valueOf(Kd);
                        sendDataToESP(data);
                        Toast.makeText(getApplicationContext(),"Sending Data...",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void sendDataToESP(String data){
        //String fullid = view.getResources().getResourceName(view.getId());
        //String[] idsplit = fullid.split("/");
        //String id = idsplit[1];
        //System.out.println(fullid);


        URL remoteurl = null;
        String baseurl = "http://"+IpAddress+"/";
        String urlString = baseurl + data;
        System.out.println(urlString);
        try {
            remoteurl = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new ManualActivity.pingit().execute(remoteurl);
    }
    private class pingit extends AsyncTask<URL, Void, Void> {
        @Override
        protected Void doInBackground(URL... urls) {
            try {
                URLConnection con = null;
                con = urls[0].openConnection();
                InputStream is = null;
                is = con.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
