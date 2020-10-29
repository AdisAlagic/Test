
package com.adisalagic.test.api.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditEntry extends ApiClass{

    @SerializedName("status")
    @Expose
    private int      status;
    @SerializedName("data")
    @Expose
    private DataEdit data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataEdit getData() {
        return data;
    }

    public void setData(DataEdit data) {
        this.data = data;
    }

}
