
package com.adisalagic.test.api.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataEdit extends ApiClass{

    @SerializedName("result")
    @Expose
    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

}
