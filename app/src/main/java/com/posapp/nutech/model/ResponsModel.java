package com.posapp.nutech.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adiernes14@gmail.com on 4/16/2017.
 */

public class ResponsModel {

    String  kode, pesan;
    List<DataModel> result;

    public List<DataModel> getResult() {
        return result;
    }

    public void setResult(List<DataModel> result) {
        this.result = result;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    @SerializedName("title")
    private String Title;

    @SerializedName("image")
    private String Image;

    @SerializedName("response")
    private String Response;

    public String getResponse(){
        return Response;
    }

}
