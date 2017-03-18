package com.example.aurelien.travelnow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by martin on 18/03/17.
 */

public class ConstraintSolver {

    Calendar departure = Calendar.getInstance();
    Calendar arrival = Calendar.getInstance();
    String departurePlace;
    String arrivalPlace;

    public ConstraintSolver(String departurePlace, int departureYear, int departureMonth,
                            int departureDay, int departureHour, int departureMin,
                            String arrivalPlace, int arrivalYear, int arrivalMonth, int arrivalDay,
                            int arrivalHour, int arrivalMin) {
        this.departure.set(departureYear,departureMonth, departureDay, departureHour, departureMin);
        this.arrival.set(arrivalYear, arrivalMonth, arrivalDay, arrivalHour, arrivalMin);
        this.arrivalPlace = arrivalPlace;
        this.departurePlace = departurePlace;
    }

    public Connection getConnection() {
        DateFormat df_day = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat df_hour = new SimpleDateFormat("hh:mm");
        HTTPRequest request = new HTTPRequest();
        List<Connection> list = request.doPathRequest(departurePlace, arrivalPlace,
                df_day.format(arrival.getTime()), df_hour.format(arrival.getTime()));

        list.sort(new Comparator<Connection>() {
            @Override
            public int compare(Connection o1, Connection o2) {
                return o1.getScore() - o2.getScore();
            }
        });

        return list.get(0);

    }

}
