package com.github.jolinzhang.tablefetcher;

import java.util.ArrayList;

/**
 * Created by Shadow on 11/7/16.
 */

public enum FetchResult {

    success, failure;

    private String id;
    private ArrayList<String> header;
    private ArrayList<ArrayList<String>> content;

    public ArrayList<String> getHeader() {
        return header;
    }

    public ArrayList<ArrayList<String>> getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    void setContent(ArrayList<ArrayList<String>> content) {
        this.content = content;
    }

    void setHeader(ArrayList<String> header) {
        this.header = header;
    }

    void setId(String id) {
        this.id = id;
    }
}
