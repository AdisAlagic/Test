
package com.adisalagic.test.api.json;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entries extends ApiClass{

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("data")
    @Expose
    private List<List<Datum>> data = new ArrayList<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<List<Datum>> getData() {
        return data;
    }

    public void setData(List<List<Datum>> data) {
        this.data = data;
    }

}
