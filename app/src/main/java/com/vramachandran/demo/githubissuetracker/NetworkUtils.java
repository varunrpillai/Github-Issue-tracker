package com.vramachandran.demo.githubissuetracker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class NetworkUtils {
    /**
     * Return true if the device has data network available
     */
    public static boolean hasConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Access the InputStream from URL into a string
     */
    public static String getUrlContent(String urlString) {

        String contentAsString = null;
        InputStream is = null;
        try {
            URL url = new URL(urlString);

            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();
            contentAsString = NetworkUtils.readIt(is);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return contentAsString;
    }

    /**
     * Read the string from the InputStream
     */
    public static String readIt(InputStream stream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line = reader.readLine();
        StringBuilder response = new StringBuilder();
        while (line != null) {
            response.append(line);
            line = reader.readLine();
        }
        return response.toString();
    }
}