
package com.adisalagic.test.api.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum extends ApiClass{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("body")
    @Expose
    private String body;
    /**
     * Дата создания
     */
    @SerializedName("da")
    @Expose
    private String da;
    /**
     * Дата модификации
     */
    @SerializedName("dm")
    @Expose
    private String dm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

}
