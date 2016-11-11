package com.github.jolinzhang.tablefetcher;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Shadow on 11/10/16.
 */

class HttpRequest implements Runnable {

    public interface ResponseHandler {
        void handle(String id, String responseString);
    }

    private String url;
    private ResponseHandler responseHandler;
    private String id;

    public HttpRequest(String url, ResponseHandler handler) {
        this.url = url;
        this.responseHandler = handler;
        id = UUID.randomUUID().toString();
    }

    public void asyncRun() {
        Thread worker = new Thread(this);
        worker.start();
    }

    @Override
    public void run() {
        responseHandler.handle(id, getString());
    }

    private String getString() {
        System.out.println("begin");
        HttpURLConnection urlConnection = null;
        try {
            URL u = new URL(url);
            urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.addRequestProperty("User-Agent", "android");

            InputStream is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            String responseString = sb.toString();
            System.out.println("end");
            return responseString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) { urlConnection.disconnect(); }
        }
        return null;
    }

}
