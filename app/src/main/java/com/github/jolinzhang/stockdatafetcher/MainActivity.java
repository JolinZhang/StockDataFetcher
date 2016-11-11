package com.github.jolinzhang.stockdatafetcher;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.github.jolinzhang.tablefetcher.FetchResult;
import com.github.jolinzhang.tablefetcher.ITableFetcher;
import com.github.jolinzhang.tablefetcher.TableFetcher;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Button mFetch;
    private Button mCancle;
    private RecyclerView mInfo;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;

    private ArrayList<String> head;
    private ArrayList<ArrayList<String>> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFetch = (Button)findViewById(R.id.fetch);
        mCancle = (Button)findViewById(R.id.cancle);
        mInfo = (RecyclerView)findViewById(R.id.info);

        mInfo.setHasFixedSize(true);
        //set Linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mInfo.setLayoutManager(mLayoutManager);


        //set adapter
        head = new ArrayList<>();
        content = new ArrayList<ArrayList<String>>();
        recyclerViewAdapter = new RecyclerViewAdapter(head, content,getApplicationContext());
        mInfo.setAdapter(recyclerViewAdapter);

        //fetch button click
        mFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get all data
                TableFetcher.getInstance().fetch("INTC", new ITableFetcher.FetchResultHandler() {
                    @Override
                    public void handle(FetchResult result) {
                        switch (result) {
                            case success:
                                head = result.getHeader();
                                content = result.getContent();

                                break;
                            case failure:

                                break;
                        }
                    }
                });

            }


        });


    }
}
