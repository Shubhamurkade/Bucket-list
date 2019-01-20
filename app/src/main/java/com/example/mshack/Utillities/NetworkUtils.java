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
import java.util.List;
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



    public static AutoSuggestResp buildUrlForOAuth(String searchText){
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
            Log.i("oauth token:", oAuthResp.access_token);
            OkHttpClient client2 = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://atlas.mapmyindia.com/api/places/search/json").newBuilder();
            urlBuilder.addQueryParameter("query", searchText);
            String url = urlBuilder.build().toString();
            Request request2 = new Request.Builder()
                    .header("Authorization", oAuthResp.getToken_type() + " " + oAuthResp.getAccess_token())
                    .url(url)
                    .build();
            Response response2 = client2.newCall(request2).execute();
            return mapper.readValue(response2.body().string(), AutoSuggestResp.class);
            //Log.i("RESP2", arr.getSuggestedLocations().get(0).getPlaceAddress());
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AutoSuggestResp {

        private List<Places> suggestedLocations;

        public List<Places> getSuggestedLocations() {
            return suggestedLocations;
        }

        public void setSuggestedLocations(List<Places> suggestedLocations) {
            this.suggestedLocations = suggestedLocations;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Places {
            private String placeAddress;

            private String placeName;

            private String latitude;

            private String longitude;

            public String getPlaceAddress() {
                return placeAddress;
            }

            public String getLongitude(){return longitude;}

            public String getLatitude(){return latitude;}

            public void setPlaceAddress(String placeAddress) {
                this.placeAddress = placeAddress;
            }

            public String getPlaceName() {
                return placeName;
            }

            public void setPlaceName(String placeName) {
                this.placeName = placeName;
            }
        }

    }
}