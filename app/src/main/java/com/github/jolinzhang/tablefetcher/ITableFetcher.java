package com.github.jolinzhang.tablefetcher;


/**
 * Created by Shadow on 11/6/16.
 */

public interface ITableFetcher {

    public interface FetchResultHandler {

        public void handle(FetchResult result);

    }

    public void fetch(String stockName, FetchResultHandler handler);

    public void stop();

}
