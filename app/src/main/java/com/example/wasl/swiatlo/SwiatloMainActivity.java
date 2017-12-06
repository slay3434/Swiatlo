package com.example.wasl.swiatlo;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
//import android.view.ViewDebug;
import android.widget.GridView;
import android.widget.TextView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.lang.*;

//import java.io.*;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.io.ByteArrayOutputStream;
import java.util.Random;

//import android.net.wifi.WifiManager;


public class SwiatloMainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    private String serverip = "109.241.91.227";
    boolean close = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiatlo_main);


        //R.id.textView2

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!close) {
                    (new Asker()).execute("ss0");
                    try {
                        Thread.sleep(5000);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        th.start();



        //(new Asker()).execute("ss0");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        View myView = findViewById(R.id.activity_swiatlo_main);
//        myView.setOnTouchListener(new OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                // ... Respond to touch events
//                return true;
//            }
//        });

    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {

        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(e);

    //public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        ((TextView) findViewById( R.id.textView)).setText("start runnerajaga");

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y >  2) {
                //dx = dx * -1 ;
                   (findViewById(R.id.ustawienia)).setTop(0);
            }


                if (y <  2) {
                    //dx = dx * -1 ;
                float m = -650f;
                    (findViewById(R.id.ustawienia)).setTranslationY(m);
                }
                // reverse direction of rotation to left of the mid-line
               /* if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                mRenderer.setAngle(
                        mRenderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();*/
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SwiatloMain Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        close = true;

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }



    public boolean ison = false;

    public void wlacz(View v) {
        //SessionIdentifierGenerator sg = new SessionIdentifierGenerator();
        //((TextView)findViewById(R.id.textView2)).setText(sg.nextSessionId());

        (new Komunikator()).execute("change");

    }

    public void Koniec(View v) {
        close = true;
        finish();
        System.exit(0);
    }

    public void changeStat() {
        if (ison) {
            findViewById(R.id.activity_swiatlo_main).setBackgroundResource(R.drawable.lighton);
            //ison = true;
        } else {
            findViewById(R.id.activity_swiatlo_main).setBackgroundResource(R.drawable.lightoff);
            //ison = false;
        }

        //((TextView) findViewById(R.id.textView)).setText(String.valueOf(ison));
    }

    public void take(View v) {

        (new Asker()).execute("ss0");
    }



    private class Asker extends AsyncTask<String,Void, String>{

        @Override
        protected String doInBackground(String... params)
        {
            String odp = "";
            try {
                Random ran = new Random();
                int x = ran.nextInt(4);

                //URL url = new URL("http://192.168.0.103:8080/users/"+Integer.toString(x));
                URL url = new URL("http://"+serverip+"/stest/getstate");

                HttpURLConnection   conn = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        odp+=inputLine;
                    in.close();
                    conn.disconnect();

                return odp;

            }
            catch(Exception e)
            {
                odp = e.getMessage();

            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {

            ison = Boolean.parseBoolean(result);
            changeStat();
            ((TextView) findViewById(R.id.textView)).setText(result);
        }
    }

    private class Komunikator extends AsyncTask<String, Void, String> {
        private Exception exception;

        boolean stat = false;
        @Override
        protected String doInBackground(String... params) {

            String zz = "";

            try {
                InetAddress addr = InetAddress.getByName(serverip);
                //InetAddress addr = InetAddress.getByName("192.168.0.103");
                Socket myclient = new Socket(addr, 6000);
                PrintWriter outt = new PrintWriter(myclient.getOutputStream(), true);
                outt.print(params[0]);
                outt.flush();
                outt.close();
                myclient.close();

            } catch (Exception e) {
                return e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.output);
            //txt.setText("Executed"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you

            //((TextView) findViewById(R.id.textView)).setText(result);
            ison = !ison;
            changeStat();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }


    public class Zmieniacz
    {
        public  void  zmien(String tresc)
        {
            ((TextView) findViewById(R.id.textView)).setText(tresc);
        }
    }

    //********************** STARE SMIECI



    public final class SessionIdentifierGenerator {
        private SecureRandom random = new SecureRandom();

        public String nextSessionId() {
            return new BigInteger(130, random).toString(32);
        }
    }


    public class MyRunnable implements Runnable {

        private int var;

        public MyRunnable(int var) {
            this.var = var;
        }

        public void run() {
            // code in the other thread, can reference "var" variable
            try {
                ((TextView) findViewById(R.id.textView)).setText("1");
                InetAddress addr = InetAddress.getByName("0.0.0.0");
                Socket myclient = new Socket(addr, 6000);

                ((TextView) findViewById(R.id.textView)).setText("2");
                InputStream reader = myclient.getInputStream();

                ((TextView) findViewById(R.id.textView)).setText("3");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];
                String response = "";
                int bytesRead;

                while ((bytesRead = reader.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }

                ((TextView) findViewById(R.id.textView)).setText(response);

             /*   ((TextView) findViewById( R.id.textView)).setText("start runneraj");
                ServerSocket server = new ServerSocket(6000);
                ((TextView) findViewById( R.id.textView)).setText("start runnerajaga");
                Socket client = server.accept();
                ((TextView) findViewById( R.id.textView)).setText("start runneraja");
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                int i=0;
                while(true) {
                    try {
                        ((TextView) findViewById( R.id.textView)).setText("fff"+i);
                        String line = reader.readLine();
                        //((TextView) findViewById( R.id.textView)).setText(line);
                        i++;
                    }catch (Exception rex)
                    {
                        System.out.println(rex);
                    }
                }*/

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    private class Sluchacz extends AsyncTask<String, Void, String> {

        private Exception exception;

        //private boolean stat = false;
        @Override
        protected String doInBackground(String... params) {
            (new Zmieniacz()).zmien("jakas wartosc");

            try {
                //onProgressUpdate("111");



                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response = "";

                            (new Zmieniacz()).zmien("start sluchacza");

                            ServerSocket server = new ServerSocket(6001);
                            Socket myclient = server.accept();

                            //InetAddress addr = InetAddress.getByName("0.0.0.0");
                            //Socket myclient = new Socket(addr, 6001);

                            //onProgressUpdate("2");





//                             InputStream reader = myclient.getInputStream();
//                            //onProgressUpdate("3");
//                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
//                            byte[] buffer = new byte[1024];
//
//                            int bytesRead;
//
//                            while ((bytesRead = reader.read(buffer)) != -1) {
//                                byteArrayOutputStream.write(buffer, 0, bytesRead);
//                                response += byteArrayOutputStream.toString("UTF-8");
//                            }

                            BufferedReader reader = new BufferedReader(new InputStreamReader( myclient.getInputStream()));
                            response = reader.readLine();

                            (new Zmieniacz()).zmien(response);
                            //onProgressUpdate(response);
                            //ison = Boolean.getBoolean(response);
                            //changeStat();
                            ((TextView) findViewById(R.id.textView)).setText(response);
//                            ison = Boolean.parseBoolean(response);
//                            changeStat();


                            myclient.close();
                        }catch (Exception exr)
                        {  System.out.println(exr.getMessage());
                            (new Zmieniacz()).zmien(exr.getMessage());}
                    }
                });

                t.start();

                //InetAddress addr = InetAddress.getByName("109.241.91.227");
                InetAddress addr = InetAddress.getByName(serverip);
                Socket mywclient = new Socket(addr, 6000);

                PrintWriter outt = new PrintWriter(mywclient.getOutputStream(), true);
                outt.print("getstatus");
                outt.flush();
                outt.close();
                mywclient.close();

            } catch (Exception e) {
                (new Zmieniacz()).zmien(e.getMessage());
                //System.out.println(e.getMessage());

            }


            return null;//response;
        }


        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.output);
            //txt.setText("Executed"); // txt.setText(result);

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
//
//            ((TextView) findViewById(R.id.textView)).setText(result);
//             ison = Boolean.parseBoolean(result);
//             changeStat();

            //((TextView) findViewById(R.id.textView)).setText("fff "+result +"dddd");
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void...values) {
            //((TextView) findViewById(R.id.textView)).setText("fff"+response[0]);
            // ison = Boolean.getBoolean(response[0]);
            // changeStat();
        }
    }

    //************** tworzenie watku runnable
    {
        //        Thread ts = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
//                String ipAddress = BigInteger.valueOf(wm.getDhcpInfo().netmask).toString();
//
//                if(ipAddress.startsWith("192"))
//                    (new Sluchacz()).execute("aaa");
//            }
//        });

        //ts.run();
    }
}


