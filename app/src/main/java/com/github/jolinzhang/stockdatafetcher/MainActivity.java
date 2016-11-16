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

public class MainActivity extends AppCompatActivity {

    private EditText stockName;
    private Button mFetch;
    private RecyclerView mInfo;
    private ProgressBar progressBar;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;

    private ArrayList<String> head;
    private ArrayList<ArrayList<String>> content;

    private int tag = 1;

    private TableFetcher fetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stockName = (EditText) findViewById(R.id.stockName);
        mFetch = (Button)findViewById(R.id.fetch);
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

                //fetch button
                if(tag == 1){
                    tag =2;
                    mFetch.setText("CANCLE");
                progressBar.setVisibility(View.VISIBLE);
                //every time click fetch, clear content in recyclerView
                recyclerViewAdapter.head = head;
                recyclerViewAdapter.content = content;
                recyclerViewAdapter.notifyDataSetChanged();

                //disable key board
                InputMethodManager inputManager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                //get all data
                    fetcher = new TableFetcher();
                    fetcher.fetch(stockName.getText().toString(), new ITableFetcher.FetchResultHandler() {
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
                                        tag =1;
                                        mFetch.setText("FETCH");
                                        recyclerViewAdapter.notifyDataSetChanged();
                                    }
                                });
                                break;
                            case failure:
                                final String message = result.getReason();
                                ac.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        tag =1;
                                        mFetch.setText("FETCH");
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    }
                                });

                                break;
                        }
                    }
                });

            }

            //cancel button
            else{
                tag =1;
                mFetch.setText("FETCH");
                progressBar.setVisibility(View.GONE);
                    fetcher.drop();



            }

            }
        });




    }
}
