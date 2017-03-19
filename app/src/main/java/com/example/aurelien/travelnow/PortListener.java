package com.example.aurelien.travelnow;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by martin on 18/03/17.
 */

public class PortListener extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        try {
            ServerSocket ss = new ServerSocket(12345);
            Socket socket = ss.accept();
            DataInputStream is = new DataInputStream(socket.getInputStream());
            Log.v("MY APP - ", is.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
