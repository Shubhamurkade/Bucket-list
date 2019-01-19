package com.example.mshack.Utillities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * These utilities will be used to communicate with the weather servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String QUERY_PARAM = "api_key";
    final static String API_KEY = "INSERT-YOUR-KEY";


    public static String buildUrlForOAuth(){
        /*try{
            URL url = new URL("https://outpost.mapmyindia.com/api/security/oauth/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String params = "grant_type=client_credentials&client_id=4Ffs0QpXpGqHilHwZ4cX-JuUUOyHbvkCydim8e231I4=&client_secret=BRt6beAlmOf6h2jm3JfkTWEE_DRsIBWJlmbkc2pxjrQZFuf7cm7-5Q==";
            String encodedData = URLEncoder.encode(params, "UTF-8");
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(encodedData.getBytes(StandardCharsets.UTF_8).length));
              DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(encodedData.getBytes(StandardCharsets.UTF_8));

            String response = conn.getResponseMessage();
            Log.i("REQ:", encodedData);
            Log.i("RESP:", response);

            return response;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("grant_type", "client_credentials")
                    .add("client_id", "4Ffs0QpXpGqHilHwZ4cX-JuUUOyHbvkCydim8e231I4=")
                    .add("client_secret", "BRt6beAlmOf6h2jm3JfkTWEE_DRsIBWJlmbkc2pxjrQZFuf7cm7-5Q==")
                    .build();
            Request request = new Request.Builder().url("https://outpost.mapmyindia.com/api/security/oauth/token")
                    .post(formBody).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            Log.i("RESP", response.body().string());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static URL buildUrl(String sortBy) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(sortBy)
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildUrlForTrailersAndReviews(String movieId, String reviewOrVideo) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(reviewOrVideo)
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}