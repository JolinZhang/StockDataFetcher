package com.github.jolinzhang.tablefetcher;

import java.util.ArrayList;

/**
 * Created by Shadow on 11/6/16.
 */

public interface ITableFetcher {

    public enum IFetchResult {

        success, failure;

        ArrayList[] getHeader;
        ArrayList<ArrayList[]> getContent;

    }

    public interface IFetchResultHandler {

        public void handle(IFetchResult result);

    }

    public void fetch(String stockName, IFetchResultHandler handler);

}
