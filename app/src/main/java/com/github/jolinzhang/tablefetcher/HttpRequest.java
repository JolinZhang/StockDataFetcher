package com.github.jolinzhang.tablefetcher;

/**
 * Created by Zengtai Qi (zxq150130) on 11/7/16.
 * The class to run http request directly.
 */

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

class HttpRequest implements Runnable {

    static final String TIMEOUTERROR = "TIMEOUTERROR";
    static final String UNKNOWNHOSTERROR = "UNKNOWNHOSTERROR";
    static final String FILENOTFOUNDERROR = "FILENOTFOUNDERROR";

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     * The response handler interface. (single method)
     */
    public interface ResponseHandler {
        void handle(String id, String responseString);
    }

    private String url;
    private ResponseHandler responseHandler;
    private String id;

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     * The constructor method.
     */
    public HttpRequest(String url, ResponseHandler handler) {
        this.url = url;
        this.responseHandler = handler;
        id = UUID.randomUUID().toString();
    }

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     * The method to run in background thread.
     */
    public void asyncRun() {
        Thread worker = new Thread(this);
        worker.start();
    }

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     * The Runnable interface implementation.
     */
    @Override
    public void run() {

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseHandler.handle(id, getString());
    }

    /**
     * Created by Zengtai Qi (zxq150130) on 11/7/16.
     * The method to get response string.
     */
    private String getString() {
        HttpURLConnection urlConnection = null;
        try {
            //  Do the http request.
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
            //  URL incorrect.
            e.printStackTrace();
        } catch (java.net.SocketTimeoutException e) {
            //  Timeout.
            e.printStackTrace();
            return TIMEOUTERROR;
        } catch (java.net.UnknownHostException e) {
            //  Internet error.
            e.printStackTrace();
            return UNKNOWNHOSTERROR;
        } catch (java.io.FileNotFoundException e) {
            //  The given stock name is invalid.
            e.printStackTrace();
            return FILENOTFOUNDERROR;
        } catch (IOException e) {
            //  Other unpredicted error.
            e.printStackTrace();
        } finally {
            if (urlConnection != null) { urlConnection.disconnect(); }
        }
        return null;
    }

}
