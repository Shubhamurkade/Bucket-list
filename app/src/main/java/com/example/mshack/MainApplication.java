package com.example.mshack;

import android.app.Application;
import com.mmi.services.account.MapmyIndiaAccountManager;

public class MainApplication extends Application {

    private String getAtlasGrantType() {
        return "client_credentials";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MapmyIndiaAccountManager.getInstance().setRestAPIKey("ep9cjzplb1iawpuwggl7vjnirx4wvs49");
        MapmyIndiaAccountManager.getInstance().setMapSDKKey("upwdomxbyrl7hr397zreifvacb6k4m5x");
        MapmyIndiaAccountManager.getInstance().setAtlasClientId("ep9cjzplb1iawpuwggl7vjnirx4wvs49");
        MapmyIndiaAccountManager.getInstance().setAtlasClientSecret("BRt6beAlmOf6h2jm3JfkTWEE_DRsIBWJlmbkc2pxjrQZFuf7cm7-5Q==");
        MapmyIndiaAccountManager.getInstance().setAtlasGrantType(getAtlasGrantType());
    }
}
