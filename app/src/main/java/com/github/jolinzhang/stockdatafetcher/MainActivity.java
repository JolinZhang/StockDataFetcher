package com.github.jolinzhang.stockdatafetcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.jolinzhang.tablefetcher.FetchResult;
import com.github.jolinzhang.tablefetcher.ITableFetcher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ITableFetcher fetcher = null;
        fetcher.fetch("stock", new ITableFetcher.IFetchResultHandler() {
            @Override
            public void handle(FetchResult result) {
                switch (result) {
                    case success:

                        break;
                    case failure:

                        break;
                }
            }
        });
    }
}
