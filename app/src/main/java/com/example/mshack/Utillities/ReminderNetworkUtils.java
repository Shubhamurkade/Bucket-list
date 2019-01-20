package com.example.mshack.Utillities;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * These utilities will be used to communicate with the weather servers.
 */
public final class ReminderNetworkUtils {

    private static final String TAG = ReminderNetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String QUERY_PARAM = "api_key";
    final static String API_KEY = "INSERT-YOUR-KEY";

    public static String getJWTToken(String username) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create( MediaType.parse("application/json; charset=utf-8"), "{\"username\": \""+ username + "\"}");
            Request request = new Request.Builder().url("http://23.101.138.79/api/generateToken")
                    .post(body).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            String resp = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            GenerateTokenResp tokenGenResp = mapper.readValue(resp, GenerateTokenResp.class);

            Log.i("RESP", tokenGenResp.getToken());
            return tokenGenResp.getToken();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void buildUrlForServerDelete(String access_token, String id) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().header("x-access-token", access_token).url("http://23.101.138.79/api/remainder/"+id)
                    .delete().build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static List<ReminderListResp.Remainder> buildUrlForServerFetch(String access_token) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().header("x-access-token", access_token).url("http://23.101.138.79/api/remainder/list")
                    .get().build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            String resp = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            ReminderListResp remainderListResp = mapper.readValue(resp, ReminderListResp.class);

            return remainderListResp.getList();

            //Log.i("list", OAuthResp.getlist());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public static void buildUrlForServerAdd(AddRemainderRequest rem, String access_token) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            OkHttpClient client = new OkHttpClient();
            String content = mapper.writeValueAsString(rem);
            RequestBody body = RequestBody.create( MediaType.parse("application/json; charset=utf-8"), content);
            Request request = new Request.Builder().header("x-access-token", access_token).url("http://23.101.138.79/api/remainder")
                    .post(body).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            //Log.i("list", OAuthResp.getlist());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReminderListResp {

        private List<Remainder> list;

        public List<Remainder> getList() {
            return list;
        }

        public void setList(List<Remainder> list) {
            this.list = list;
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Remainder {

            private String latitude;

            private String longitude;

            private String radius;

            private String title;

            private String text;

            private String place;

            @JsonProperty("_id")
            private String id;

            private String createdAt;

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getRadius() {
                return radius;
            }

            public void setRadius(String radius) {
                this.radius = radius;
            }

            public String getTitle() {
                return title.trim();
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCreatedAt() {
                return createdAt.substring(0, 10);
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }
        }
    }
}
