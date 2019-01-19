package com.example.mshack.Utillities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import okhttp3.HttpUrl;
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

    public static String buildUrlForOAuth(String searchText){
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
            //Log.i("RESP", response.body().string());


            ObjectMapper mapper = new ObjectMapper();
            OAuthResp oAuthResp = mapper.readValue(response.body().string(), OAuthResp.class);
            //Log.i("search text:", searchText);
            OkHttpClient client2 = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://atlas.mapmyindia.com/api/places/search/json").newBuilder();
            urlBuilder.addQueryParameter("query", searchText);
            String url = urlBuilder.build().toString();
            Request request2 = new Request.Builder()
                    .header("Authorization", oAuthResp.getToken_type() + " " + oAuthResp.getAccess_token())
                    .url(url)
                    .build();
            Response response2 = client2.newCall(request2).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response2);
            Log.i("RESP2:", response2.body().string());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OAuthResp {

        private String access_token;

        private String token_type;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }
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