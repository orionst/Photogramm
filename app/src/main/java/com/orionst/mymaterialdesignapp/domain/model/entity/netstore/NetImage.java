package com.orionst.mymaterialdesignapp.domain.model.entity.netstore;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NetImage {

    @Expose
    @SerializedName("webformatURL")
    private String webFormatURL;

    @Expose
    @SerializedName("largeImageURL")
    private String largeImageURL;

    private Uri photoUri;

    public NetImage(String webFormatURL, String largeImageURL) {
        this.webFormatURL = webFormatURL;
        this.largeImageURL = largeImageURL;
        this.photoUri = Uri.parse(largeImageURL);
    }

    public String getWebformatURL() {
        return webFormatURL;
    }

    public void setWebformatURL(String pageUrl) {
        this.webFormatURL = pageUrl;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

}
