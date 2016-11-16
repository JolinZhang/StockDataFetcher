package com.github.jolinzhang.tablefetcher;

/**
 * Created by Zengtai Qi (zxq150130) on 11/7/16.
 * The class wrap up fetch result or failure info.
 */

import java.util.ArrayList;

public enum FetchResult {

    success, failure;

    private String reason;
    private String id;
    private ArrayList<String> header;
    private ArrayList<ArrayList<String>> content;

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     */
    public ArrayList<String> getHeader() {
        return header;
    }

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     */
    public ArrayList<ArrayList<String>> getContent() {
        return content;
    }

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     */
    public String getId() {
        return id;
    }

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     */
    public String getReason() {
        return reason;
    }

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     */
    void setContent(ArrayList<ArrayList<String>> content) {
        this.content = content;
    }

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     */
    void setHeader(ArrayList<String> header) {
        this.header = header;
    }

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     */
    void setId(String id) {
        this.id = id;
    }

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     */
    void setReason(String reason) {
        this.reason = reason;
    }
}
