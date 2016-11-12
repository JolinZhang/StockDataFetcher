package com.github.jolinzhang.stockdatafetcher;

import android.app.ActionBar;

import com.github.jolinzhang.tablefetcher.FetchResult;
import com.github.jolinzhang.tablefetcher.HttpRequest;
import com.github.jolinzhang.tablefetcher.ITableFetcher;
import com.github.jolinzhang.tablefetcher.TableFetcher;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        final boolean[] a = {false};

        TableFetcher.getInstance().fetch("INTC", new ITableFetcher.FetchResultHandler() {
            @Override
            public void handle(FetchResult result) {
                a[0] = true;
                assertEquals(10, result.getHeader().size());
                assertEquals(10, result.getContent().size());
                assertEquals(10, result.getContent().get(0).size());
            }
        });

        while (!a[0]) {}

    }

}