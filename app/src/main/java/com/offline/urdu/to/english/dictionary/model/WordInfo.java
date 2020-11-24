package com.offline.urdu.to.english.dictionary.model;

/**
 * Created by SST on 7/4/2017.
 */

public class WordInfo {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getUrdu() {
        return urdu;
    }

    public void setUrdu(String urdu) {
        this.urdu = urdu;
    }

    String english;
    String urdu;
}
