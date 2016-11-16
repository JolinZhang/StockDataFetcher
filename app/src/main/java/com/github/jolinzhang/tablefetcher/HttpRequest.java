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
import java.util.concurrent.TimeUnit;

/**
 * Created by Shadow on 11/10/16.
 */

class HttpRequest implements Runnable {

    static final String TIMEOUTERROR = "TIMEOUTERROR";
    static final String UNKNOWNHOSTERROR = "UNKNOWNHOSTERROR";
    static final String FILENOTFOUNDERROR = "FILENOTFOUNDERROR";

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

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseHandler.handle(id, getString());
    }

    private String getString() {
        HttpURLConnection urlConnection = null;
        try {
            URL u = new URL(url);
            urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setConnectTimeout(45 * 1000);
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
            return responseString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
            return TIMEOUTERROR;
        } catch (java.net.UnknownHostException e) {
            e.printStackTrace();
            return UNKNOWNHOSTERROR;
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            return FILENOTFOUNDERROR;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) { urlConnection.disconnect(); }
        }
        return null;
    }

}
