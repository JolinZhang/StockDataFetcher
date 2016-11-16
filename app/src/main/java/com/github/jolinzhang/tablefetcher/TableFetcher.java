package com.github.jolinzhang.tablefetcher;

import java.util.ArrayList;

/**
 * Created by Shadow on 11/10/16.
 */

public class TableFetcher implements ITableFetcher {

    static String urlPrefix = "http://utdallas.edu/~John.Cole/2016Spring/";
    static String urlSuffix = ".txt";

    private Boolean enable = true;

    @Override
    public void fetch(String stockName, final FetchResultHandler handler) {
        final HttpRequest request = new HttpRequest(urlPrefix + stockName + urlSuffix, new HttpRequest.ResponseHandler() {
            @Override
            public void handle(String id, String responseString) {
                if (!enable) { return; }
                FetchResult failureResult = FetchResult.failure;
                switch (responseString) {
                    case HttpRequest.FILENOTFOUNDERROR:
                        failureResult.setReason("Invalid stock name.");
                        handler.handle(failureResult);
                        return;
                    case HttpRequest.TIMEOUTERROR:
                        failureResult.setReason("Request timeout error.");
                        handler.handle(failureResult);
                        return;
                    case HttpRequest.UNKNOWNHOSTERROR:
                        failureResult.setReason("Internet connection error.");
                        handler.handle(failureResult);
                        return;
                    default:
                        break;
                }
                if (responseString == null) {
                    failureResult.setReason("Unknown error.");
                    handler.handle(failureResult);
                    return;
                }
                String[] lines = responseString.split("\n");
                if (lines.length < 2) {
                    failureResult.setReason("Server content error.");
                    handler.handle(failureResult);
                    return;
                }
                FetchResult result = FetchResult.success;
                result.setId(id);
                String[] columns = lines[0].split(",");
                result.setHeader(toHeader(columns));
                result.setContent(toContent(lines, columns.length));
                handler.handle(result);
            }
        });
        request.asyncRun();
    }

    @Override
    public void drop() {
        enable = false;
    }

    private ArrayList<String> toHeader(String[] columns) {
        ArrayList<String> header = new ArrayList<>();
        for (int i = 0; i < columns.length; i++) {
            header.add(columns[i]);
        }
        return header;
    }

    private ArrayList<ArrayList<String>> toContent(String[] lines, int width) {
        ArrayList<ArrayList<String>> content = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String[] columns = lines[i].split(",");
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < columns.length; j++) {
                row.add(columns[j]);
            }
            while (row.size() > width) { row.remove(row.size()); }
            while (row.size() < width) { row.add(""); }
            content.add(row);
        }
        return content;
    }
}
