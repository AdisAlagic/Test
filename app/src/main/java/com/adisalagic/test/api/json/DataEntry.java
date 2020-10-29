
package com.adisalagic.test.api.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataEntry extends ApiClass {

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
