package com.thetidbitapp.model;

import org.json.JSONObject;

/**
 * Created by Ujval on 6/13/15
 */
public class FBEvent {

    private String food;

	public FBEvent(JSONObject json) {

	}

    public void setFood(String s) {
        this.food = s;
    }

}
