package com.fc.radiate.DataManagement;

import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fc.radiate.App;
import com.fc.radiate.R;
import com.fc.radiate.DataManagement.RadioContract.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "changeuuid",
        "stationuuid",
        "name",
        "url",
        "homepage",
        "favicon",
        "tags",
        "country",
        "state",
        "language",
        "votes",
        "negativevotes",
        "lastchangetime",
        "ip",
        "codec",
        "bitrate",
        "hls",
        "lastcheckok",
        "lastchecktime",
        "lastcheckoktime",
        "clicktimestamp",
        "clickcount",
        "clicktrend"
})
public class Station implements Serializable, Parcelable
{

    private Bitmap icon;

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("favicon")
    private String favicon;
    @JsonProperty("country")
    private String country;
    @JsonProperty("language")
    private String language;
    @JsonProperty("bitrate")
    private String bitrate;

    public final static Parcelable.Creator<Station> CREATOR = new Creator<Station>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        public Station[] newArray(int size) {
            return (new Station[size]);
        }

    };
    private final static long serialVersionUID = -691582194887873029L;

    protected Station(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.favicon = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.language = ((String) in.readValue((String.class.getClassLoader())));
        this.bitrate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Station() {
    }
    public Station(
            String id,
            String name,
            String STREAM_URL,
            String iconURL,
            String country,
            String Language,
            String bitrate) {
        this.id = id;
        this.name= name;
        this.url = STREAM_URL;
        this.favicon = iconURL;
        this.country = country;
        this.language = Language;
        this.bitrate = bitrate;
    }


    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("favicon")
    public String getFavicon() {
        return favicon;
    }

    @JsonProperty("favicon")
    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("bitrate")
    public String getBitrate() {
        return bitrate;
    }

    @JsonProperty("bitrate")
    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).append("url", url).append("favicon", favicon).append("country", country).append("language", language).append("bitrate", bitrate).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(url).append(country).append(favicon).append(bitrate).append(language).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Station) == false) {
            return false;
        }
        Station rhs = ((Station) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(url, rhs.url).append(country, rhs.country).append(favicon, rhs.favicon).append(bitrate, rhs.bitrate).append(language, rhs.language).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(url);
        dest.writeValue(favicon);
        dest.writeValue(country);
        dest.writeValue(language);
        dest.writeValue(bitrate);
    }

    public int describeContents() {
        return 0;
    }

    public Bitmap getImage() {
        return  BitmapFactory.decodeResource(App.getContext().getResources(),
                R.drawable.loading_background);
    }
}
