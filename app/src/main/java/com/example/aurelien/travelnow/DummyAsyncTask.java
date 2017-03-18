package com.example.aurelien.travelnow;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by martin on 18/03/17.
 */

public class DummyAsyncTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        ConstraintSolver cs = new ConstraintSolver("Genf", 2017, 03, 17, 9, 00, "St-Gallen", 2017, 03, 17, 15, 00);
        Connection c = cs.getConnection();
        Log.v("MY APP", c.duration_day+"d"+c.duration_hour+":"+c.duration_min);
        return null;
    }

}
