package com.thedeveloperworldisyours.feed.models;

import com.google.gson.annotations.Expose;

/**
 * Created by javiergonzalezcabezas on 29/3/15.
 */
public class Media {

    @Expose
    private String m;

    /**
     *
     * @return
     * The m
     */
    public String getM() {
        return m;
    }

    /**
     *
     * @param m
     * The m
     */
    public void setM(String m) {
        this.m = m;
    }

}
