package com.example.stackoverflowsearcher.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class StackoverflowResult implements Serializable
{

    @SerializedName("items")
    @Expose
    private List<Discussion> items = null;

    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;

    @SerializedName("quota_max")
    @Expose
    private Integer quotaMax;

    @SerializedName("quota_remaining")
    @Expose
    private Integer quotaRemaining;

    private final static long serialVersionUID = -1687632598173740519L;

    public List<Discussion> getItems() {
        return items;
    }

    public void setItems(List<Discussion> items) {
        this.items = items;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Integer getQuotaMax() {
        return quotaMax;
    }

    public void setQuotaMax(Integer quotaMax) {
        this.quotaMax = quotaMax;
    }

    public Integer getQuotaRemaining() {
        return quotaRemaining;
    }

    public void setQuotaRemaining(Integer quotaRemaining) {
        this.quotaRemaining = quotaRemaining;
    }

}