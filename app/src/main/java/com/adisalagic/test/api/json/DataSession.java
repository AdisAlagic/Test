package com.adisalagic.test.api.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSession extends ApiClass{

    @SerializedName("session")
    @Expose
    private String session;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}