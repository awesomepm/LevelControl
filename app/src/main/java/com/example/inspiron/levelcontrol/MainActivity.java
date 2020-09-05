package com.example.inspiron.levelcontrol;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    TextView qs1,idSet,maxH;
    Button man,auto;
    EditText setVal;
    String data,IpAddress="192.168.43.34";
    Context context;
    float level;
    int h=25;

    static String rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qs1=findViewById(R.id.qs1);
        man=findViewById(R.id.man);
        auto=findViewById(R.id.auto);
        idSet=findViewById(R.id.idSet);
        maxH=findViewById(R.id.maxH);
        context=this;

        maxH.setText("Height of Tank: "+String.valueOf(h));

        setVal=findViewById(R.id.setVal);
        setVal.setSelection(setVal.getText().length());//start cursor from last
        openMan();
        openAuto();
    }

    public void openMan(){
        man.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //ToDo send value of level to both activities

                        if(setVal.getText().toString().trim().equals("")) {
                            Toast.makeText(MainActivity.this, "Please enter valid height between 0 and " + String.valueOf(h), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            level = Float.parseFloat(setVal.getText().toString());
                            if (level <= h && level >= 0) {
                                data = null;
                                data="man";
                                sendDataToESP(data);

                                data = null;
                                data="h?height="+String.valueOf((int)level);
                                sendDataToESP(data);
                                //check Toast.makeText(MainActivity.this,String.valueOf(level),Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity.this, ManualActivity.class);
                                intent.putExtra("IP_ADDRESS",IpAddress);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Please enter valid height between 0 and " + String.valueOf(h), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }

    public void openAuto(){
        auto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(setVal.getText().toString().trim().equals("")) {
                            Toast.makeText(MainActivity.this, "Please enter valid height between 0 and " + String.valueOf(h), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            level = Float.parseFloat(setVal.getText().toString());
                            if (level <= h && level >= 0) {
                                //check Toast.makeText(MainActivity.this,String.valueOf(level),Toast.LENGTH_SHORT).show();
                                data = null;
                                data="auto";
                                sendDataToESP(data);

                                data = null;
                                data="h?height="+String.valueOf((int)level);;
                                sendDataToESP(data);
                                Intent intent=new Intent(MainActivity.this, AutoActivity.class);
                                intent.putExtra("IP_ADDRESS",IpAddress);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Please enter valid height between 0 and " + String.valueOf(h), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.ip_addr){

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText edittext = new EditText(context);
            edittext.setText(IpAddress);
            edittext.setSelection(edittext.getText().length());

            //edittext.setInputType(InputType.TYPE_CLASS_NUMBER);;
            alert.setMessage("Enter IP Address");
            alert.setTitle("Connect to ESP8266");

            alert.setView(edittext);

            alert.setNeutralButton("Connect", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    IpAddress = edittext.getText().toString();
                    data=null;
                    data="maxH";
                    sendDataToESP(data);
                    String max=receiveData(data);
                    // Instantiate the RequestQueue.
                    RequestQueue queue;
                    queue = Volley.newRequestQueue(MainActivity.this);

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, max,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    h = Integer.valueOf(response);
                                    maxH.setText("Height of Tank: " + response);
                                    //Log.i("end:",response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.i("error:",error.getMessage());
                        }
                    });
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                    Toast.makeText(getApplicationContext(), "Connected to ESP8266", Toast.LENGTH_LONG).show();
                }
            });


            alert.show();
        }

        return super.onOptionsItemSelected(item);
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

    public void sendDataToESP(String data){
        URL remoteurl = null;
        String baseurl = "http://"+IpAddress+"/";
        String urlString = baseurl + data;
        System.out.println(urlString);
        try {
            remoteurl = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new pingit().execute(remoteurl);
    }

    public String receiveData(String data){
        URL remoteurl = null;
        String baseurl = "http://"+IpAddress+"/";
        String urlString = baseurl + data;
        System.out.println(urlString);
        try {
            remoteurl = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Log.i("URL:",urlString);

        return urlString;
    }
}
