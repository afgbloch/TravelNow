package com.example.aurelien.travelnow;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by martin on 18/03/17.
 */

public class Checkpoint {
    Location station;
    String arrival;
    String departure;
    int platform;

    public Checkpoint(Location station, String arrival, String departure, int platform) {
        this.station = station;
        this.arrival = arrival;
        this.departure = departure;
        this.platform = platform;
    }

    public Checkpoint(String json_string){
        try{
            JSONObject parser = new JSONObject(json_string);
            this.station = new Location(parser.getString("station"));
            this.arrival = parser.getString("arrival");
            this.departure = parser.getString("departure");
            this.platform = parser.getInt("platform");
        } catch (JSONException e){
            System.err.println(e.toString());
        }
    }
}
