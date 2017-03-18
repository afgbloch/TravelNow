package com.example.aurelien.travelnow;

import android.os.AsyncTask;
import android.util.JsonWriter;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by martin on 18/03/17.
 */

public class HTTPRequest extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... params) {
        doStationRequest(params[0]);

        return new Boolean(true);
    }

    private void request(String urlString){
        try {
            URL url = new URL(urlString);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                StringBuilder sb = new StringBuilder();

                int next_char = is.read();
                while(next_char != -1){
                    sb.append((char) next_char);
                    next_char = is.read();
                }
                Log.v("MY APP", sb.toString());
            } catch (IOException e) {
                Log.e("MY APP", e.toString());
            }
            finally {
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            Log.e("MY APP", e.toString());
        } catch (IOException e) {
            Log.e("MY APP", e.toString());
        }
    }

    public void doStationRequest(String stationName){

        StationRequest sr = new StationRequest(stationName);
        List<Location> list;

        try {
            URL url = new URL(sr.toStringRequest());
            Log.v("MY APP", "Request : " + sr.toStringRequest());

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            try {
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                StringBuilder sb = new StringBuilder();

                int next_char = is.read();
                while(next_char != -1){
                    sb.append((char) next_char);
                    next_char = is.read();
                }
                Log.v("MY APP", sb.toString());

                list = Location.locationList(sb.toString());


            } catch (IOException e) {
                Log.e("MY APP", e.toString());
            }
            finally {
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            Log.e("MY APP", e.toString());
        } catch (IOException e) {
            Log.e("MY APP", e.toString());
        }
    }

    private class Message {
        String str;

        public Message() {
            str = "http://transport.opendata.ch/v1/";
        }

        public String toStringRequest() {
            return str;
        }
    }

    private class LocationRequest extends Message {
        public LocationRequest() {
            super();
            super.str += "locations";
        }
    }

    private class StationRequest extends LocationRequest {
        public StationRequest(String name) {
            super();
            super.str += "?query="+name;
        }
    }
}
