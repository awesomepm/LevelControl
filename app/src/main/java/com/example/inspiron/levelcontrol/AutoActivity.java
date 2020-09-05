package com.example.inspiron.levelcontrol;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AutoActivity extends AppCompatActivity {
    TextView zn,znKs,cc,ccKs,imc,imcKs,hm,hmKs;
    TextView content,method1,method2,method3,method4,tvmo,tvst,tvrt,tvei,tvKp,tvKi,tvKd,tvznmo,tvznst,tvznrt,tvznei,tvznKp,tvznKi,tvznKd,
            tvccmo,tvccst,tvccrt,tvccei,tvccKp,tvccKi,tvccKd, tvimcmo,tvimcst,tvimcrt,tvimcei,tvimcKp,tvimcKi,tvimcKd,
            tvhmmo,tvhmst,tvhmrt,tvhmei,tvhmKp,tvhmKi,tvhmKd;
    String IpAddress,rec,data;
    String znmo="",znst="",znrt="",znei="",znKp="",znKi="",znKd="";
    String ccmo="",ccst="",ccrt="",ccei="",ccKp="",ccKi="",ccKd="";
    String imcmo="",imcst="",imcrt="",imcei="",imcKp="",imcKi="",imcKd="";
    String hmmo="",hmst="",hmrt="",hmei="",hmKp="",hmKi="",hmKd="";
    Button getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        content=findViewById(R.id.content);
        method1=findViewById(R.id.method1);
        method2=findViewById(R.id.method2);
        method3=findViewById(R.id.method3);
        method4=findViewById(R.id.method4);
        tvmo=findViewById(R.id.tvmo);
        tvst=findViewById(R.id.tvst);
        tvrt=findViewById(R.id.tvrt);
        tvei=findViewById(R.id.tvei);
        tvKp=findViewById(R.id.tvKp);
        tvKi=findViewById(R.id.tvKi);
        tvKd=findViewById(R.id.tvKd);
        tvznmo=findViewById(R.id.tvznmo);
        tvznst=findViewById(R.id.tvznst);
        tvznrt=findViewById(R.id.tvznrt);
        tvznei=findViewById(R.id.tvznei);
        tvznKp=findViewById(R.id.tvznKp);
        tvznKi=findViewById(R.id.tvznKi);
        tvznKd=findViewById(R.id.tvznKd);
        tvccmo=findViewById(R.id.tvccmo);
        tvccst=findViewById(R.id.tvccst);
        tvccrt=findViewById(R.id.tvccrt);
        tvccei=findViewById(R.id.tvccei);
        tvccKp=findViewById(R.id.tvccKp);
        tvccKi=findViewById(R.id.tvccKi);
        tvccKd=findViewById(R.id.tvccKd);
        tvimcmo=findViewById(R.id.tvimcmo);
        tvimcst=findViewById(R.id.tvimcst);
        tvimcrt=findViewById(R.id.tvimcrt);
        tvimcei=findViewById(R.id.tvimcei);
        tvimcKp=findViewById(R.id.tvimcKp);
        tvimcKi=findViewById(R.id.tvimcKi);
        tvimcKd=findViewById(R.id.tvimcKd);
        tvhmmo=findViewById(R.id.tvhmmo);
        tvhmst=findViewById(R.id.tvhmst);
        tvhmrt=findViewById(R.id.tvhmrt);
        tvhmei=findViewById(R.id.tvhmei);
        tvhmKp=findViewById(R.id.tvhmKp);
        tvhmKi=findViewById(R.id.tvhmKi);
        tvhmKd=findViewById(R.id.tvhmKd);

        getData=findViewById(R.id.getData);
        IpAddress=getIntent().getStringExtra("IP_ADDRESS");

        getData();


    }

    public void getData(){
        getData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"Fetching Data...",Toast.LENGTH_SHORT).show();
                        data=null;
                        data="zn";
                        sendDataToESP(data);
                        String urlString=String.valueOf(receiveData(data));
                        RequestQueue queue;
                        queue = Volley.newRequestQueue(AutoActivity.this);


                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlString,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Display the first 500 characters of the response string.
                                        String [] arrOfStr = response.split("#", 28);
                                        znmo=arrOfStr[0];
                                        znst=arrOfStr[1];
                                        znrt=arrOfStr[2];
                                        znei=arrOfStr[3];
                                        znKp=arrOfStr[4];
                                        znKi=arrOfStr[5];
                                        znKd=arrOfStr[6];
                                        ccmo=arrOfStr[7];
                                        ccst=arrOfStr[8];
                                        ccrt=arrOfStr[9];
                                        ccei=arrOfStr[10];
                                        ccKp=arrOfStr[11];
                                        ccKi=arrOfStr[12];
                                        ccKd=arrOfStr[13];
                                        imcmo=arrOfStr[14];
                                        imcst=arrOfStr[15];
                                        imcrt=arrOfStr[16];
                                        imcei=arrOfStr[17];
                                        imcKp=arrOfStr[18];
                                        imcKi=arrOfStr[19];
                                        imcKd=arrOfStr[20];
                                        hmmo=arrOfStr[21];
                                        hmst=arrOfStr[22];
                                        hmrt=arrOfStr[23];
                                        hmei=arrOfStr[24];
                                        hmKp=arrOfStr[25];
                                        hmKi=arrOfStr[26];
                                        hmKd=arrOfStr[27];
                                        tvznmo.setText(znmo);
                                        tvznst.setText(znst);
                                        tvznrt.setText(znrt);
                                        tvznei.setText(znei);
                                        tvznKp.setText(znKp);
                                        tvznKi.setText(znKi);
                                        tvznKd.setText(znKd);

                                        tvccmo.setText(ccmo);
                                        tvccst.setText(ccst);
                                        tvccrt.setText(ccrt);
                                        tvccei.setText(ccei);
                                        tvccKp.setText(ccKp);
                                        tvccKi.setText(ccKi);
                                        tvccKd.setText(ccKd);

                                        tvimcmo.setText(imcmo);
                                        tvimcst.setText(imcst);
                                        tvimcrt.setText(imcrt);
                                        tvimcei.setText(imcei);
                                        tvimcKp.setText(imcKp);
                                        tvimcKi.setText(imcKi);
                                        tvimcKd.setText(imcKd);

                                        tvhmmo.setText(hmmo);
                                        tvhmst.setText(hmst);
                                        tvhmrt.setText(hmrt);
                                        tvhmei.setText(hmei);
                                        tvhmKp.setText(hmKp);
                                        tvhmKi.setText(hmKi);
                                        tvhmKd.setText(hmKd);

                                        //Log.i("end:",response);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Log.i("error:",error.getMessage());
                            }
                        });
                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);
//        data=null;
//        data="znst";
//        sendDataToESP(data);
//        znst=receiveData(data);
//        data=null;
//        data="znrt";
//        sendDataToESP(data);
//        znrt=receiveData(data);
//        data=null;
//        data="znei";
//        sendDataToESP(data);
//        znei=receiveData(data);
//        data=null;
//
//        data=null;
//        data="ccmo";
//        sendDataToESP(data);
//        ccmo=receiveData(data);
//        data=null;
//        data="ccst";
//        sendDataToESP(data);
//        ccst=receiveData(data);
//        data=null;
//        data="ccrt";
//        sendDataToESP(data);
//        ccrt=receiveData(data);
//        data=null;
//        data="ccei";
//        sendDataToESP(data);
//        ccei=receiveData(data);
//
//        data="imcmo";
//        sendDataToESP(data);
//        imcmo=receiveData(data);
//        data=null;
//        data="imcst";
//        sendDataToESP(data);
//        imcst=receiveData(data);
//        data=null;
//        data="imcrt";
//        sendDataToESP(data);
//        imcrt=receiveData(data);
//        data=null;
//        data="imcei";
//        sendDataToESP(data);
//        imcei=receiveData(data);
//
//        data=null;
//        data="hmmo";
//        sendDataToESP(data);
//        hmmo=receiveData(data);
//        data=null;
//        data="hmst";
//        sendDataToESP(data);
//        hmst=receiveData(data);
//        data=null;
//        data="hmrt";
//        sendDataToESP(data);
//        hmrt=receiveData(data);
//        data=null;
//        data="hmei";
//        sendDataToESP(data);
//        hmei=receiveData(data);

                        //znKs.setText("Maximum Overshoot="+znmo+"  \nSettling Time="+znst+"  \nRise Time="+znrt+"  \nError Integral="+znei);
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
        new AutoActivity.pingit().execute(remoteurl);
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

    public String receiveData(String data){
        final int rec[]=new int[1];
        String rex;
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
        // Instantiate the RequestQueue.

        return urlString;
    }

}
