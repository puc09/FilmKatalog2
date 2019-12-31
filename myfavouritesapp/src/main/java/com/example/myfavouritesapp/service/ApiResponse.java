package com.example.myfavouritesapp.service;

import com.example.myfavouritesapp.model.Cast;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiResponse {

    @SerializedName("cast")
    @Expose
    private ArrayList<Cast> casts = new ArrayList<>();

    public ArrayList<Cast> getCasts() {
        return casts;
    }

    public void setCasts(ArrayList<Cast> castList) {
        castList = casts;
    }
}
