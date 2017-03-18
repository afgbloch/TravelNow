package com.example.aurelien.travelnow;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by martin on 18/03/17.
 */

public class HTTPRequest extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... params) {
        request(params[0]);

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

}
