package com.example.stackoverflowsearcher.models;

public class SearchHistoryItem {
    private String searchText;
    private String location;
    private int id;

    public SearchHistoryItem(int id, String searchText, String location) {
        this.setId(id);
        this.setSearchText(searchText);
        this.setLocation(location);
    }

    public String getSearchText() {
        return this.searchText;
    }

    public String getLocation() {
        return this.location;
    }

    public int getId() {
        return id;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
    }
}
