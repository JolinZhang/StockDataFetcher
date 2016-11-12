package com.github.jolinzhang.stockdatafetcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.jolinzhang.tablefetcher.FetchResult;
import com.github.jolinzhang.tablefetcher.ITableFetcher;
import com.github.jolinzhang.tablefetcher.TableFetcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText stockName;
    private Button mFetch;
    private Button mCancle;
    private RecyclerView mInfo;
    private ProgressBar progressBar;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;

    private ArrayList<String> head;
    private ArrayList<ArrayList<String>> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stockName = (EditText) findViewById(R.id.stockName);
        mFetch = (Button)findViewById(R.id.fetch);
        mCancle = (Button)findViewById(R.id.cancle);
        mInfo = (RecyclerView)findViewById(R.id.info);
        progressBar = (ProgressBar)findViewById(R.id.processBar);

        mInfo.setHasFixedSize(true);
        //set Linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mInfo.setLayoutManager(mLayoutManager);


        //set adapter
        head = new ArrayList<>();
        content = new ArrayList<ArrayList<String>>();
        recyclerViewAdapter = new RecyclerViewAdapter(head, content,this);
        mInfo.setAdapter(recyclerViewAdapter);
        final Activity ac = this;





        //fetch button click
        mFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                //disable key board
                InputMethodManager inputManager = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);


                //get all data
                TableFetcher.getInstance().fetch(stockName.getText().toString(), new ITableFetcher.FetchResultHandler() {
                    @Override
                    public void handle(FetchResult result) {
                        switch (result) {
                            case success:
                                recyclerViewAdapter.head = result.getHeader();
                                recyclerViewAdapter.content = result.getContent();
                                ac.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        recyclerViewAdapter.notifyDataSetChanged();
                                    }
                                });
                                break;
                            case failure:
                                ac.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "please input right stock name", Toast.LENGTH_LONG).show();
                                    }
                                });

                                break;
                        }
                    }
                });



            }
        });



        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
