
package com.jacob.unsplash.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "raw",
    "full",
    "regular",
    "small",
    "thumb"
})
public class Photo implements Parcelable {

    @JsonProperty("raw")
    private String raw;
    @JsonProperty("full")
    private String full;
    @JsonProperty("regular")
    private String regular;
    @JsonProperty("small")
    private String small;
    @JsonProperty("thumb")
    private String thumb;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Photo() {
    }
    protected Photo(Parcel in) {
        raw = in.readString();
        full = in.readString();
        regular = in.readString();
        small = in.readString();
        thumb = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(raw);
        dest.writeString(full);
        dest.writeString(regular);
        dest.writeString(small);
        dest.writeString(thumb);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @JsonProperty("raw")
    public String getRaw() {
        return raw;
    }

    @JsonProperty("raw")
    public void setRaw(String raw) {
        this.raw = raw;
    }

    @JsonProperty("full")
    public String getFull() {
        return full;
    }

    @JsonProperty("full")
    public void setFull(String full) {
        this.full = full;
    }

    @JsonProperty("regular")
    public String getRegular() {
        return regular;
    }

    @JsonProperty("regular")
    public void setRegular(String regular) {
        this.regular = regular;
    }

    @JsonProperty("small")
    public String getSmall() {
        return small;
    }

    @JsonProperty("small")
    public void setSmall(String small) {
        this.small = small;
    }

    @JsonProperty("thumb")
    public String getThumb() {
        return thumb;
    }

    @JsonProperty("thumb")
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
