package com.github.jolinzhang.tablefetcher;


/**
 * Created by Shadow on 11/6/16.
 */

public interface ITableFetcher {

    public interface IFetchResultHandler {

        public void handle(FetchResult result);

    }

    public void fetch(String stockName, IFetchResultHandler handler);

}
