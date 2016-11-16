package com.github.jolinzhang.tablefetcher;

/**
 * Created by Zengtai Qi (zxq150130) on 11/7/16.
 * The interface exposed to other packages.
 */

public interface ITableFetcher {

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     * The fetch result handler interface.
     */
    interface FetchResultHandler {

        void handle(FetchResult result);

    }

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     * The method to fetch stock data.
     */
    void fetch(String stockName, FetchResultHandler handler);

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     * Call this method to drop this request and ignore the possible result.
     */
    void drop();

}
