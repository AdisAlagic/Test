
package com.adisalagic.test.api.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntryAdd extends ApiClass{

    @SerializedName("status")
    @Expose
    private int         status;
    @SerializedName("data")
    @Expose
    private DataSession data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataSession getData() {
        return data;
    }

    public void setData(DataSession data) {
        this.data = data;
    }

}
