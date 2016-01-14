package com.thetidbitapp.util;

import android.widget.Toast;

import com.facebook.GraphResponse;
import com.thetidbitapp.model.FBEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ujval on 6/14/15
 */
public final class ParseUtil {

    private static final String DATA_KEY = "data";
    private static final String NAME_KEY = "name";

    private ParseUtil() { }

    /**
     * Parses the response fetched from Facebook
     * @param response response from Facebook's event end point
     * @return a map from event names to events
     */
    public static Map<String, FBEvent> getEventMap(GraphResponse response) {

        Map<String, FBEvent> events = new HashMap<>();

        try {

            JSONObject jsonResponse = response.getJSONObject();
            JSONArray data = jsonResponse.getJSONArray(DATA_KEY);
            for (int i = 0; i < data.length(); i++) {
                JSONObject fbEventJson = data.getJSONObject(i);
                events.put(fbEventJson.getString(NAME_KEY), new FBEvent(fbEventJson));
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        return events;
    }

}
