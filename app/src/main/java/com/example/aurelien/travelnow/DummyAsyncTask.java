package com.example.aurelien.travelnow;

import android.os.AsyncTask;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by martin on 18/03/17.
 */

public class DummyAsyncTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        ConstraintSolver cs = new ConstraintSolver("St-Gallen", 2017, 03, 18, 00, 59, "Lausanne", 2017, 03, 18, 23, 59);
        Connection c = cs.getConnection();
        Log.v("MY APP", c.duration_day+"d"+c.duration_hour+":"+c.duration_min);



        HTTPRequest r = new HTTPRequest();
        Log.v("MY APP", r.doPointOfInterest("Zurich"));

        return null;
    }

}
