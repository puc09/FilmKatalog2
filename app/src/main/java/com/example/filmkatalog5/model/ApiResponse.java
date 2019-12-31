package com.example.filmkatalog5.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiResponse {

    @SerializedName("results")
    @Expose
    private ArrayList<Film> films = new ArrayList<>();
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("cast")
    @Expose
    private ArrayList<Cast> casts = new ArrayList<>();

    public ArrayList<Film> getFilms() {
        return films;
    }

    public void setItems(ArrayList<Film> itemList) {
        itemList = films;
    }

    public ArrayList<Cast> getCasts() {
        return casts;
    }

    public void setCasts(ArrayList<Cast> castList) {
        castList = casts;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
