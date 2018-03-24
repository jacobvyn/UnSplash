
package com.jacob.unsplash.model;

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
    "id",
    "updated_at",
    "username",
    "name",
    "first_name",
    "last_name",
    "twitter_username",
    "portfolio_url",
    "bio",
    "location",
    "links",
    "profile_image",
    "total_collections",
    "instagram_username",
    "total_likes",
    "total_photos"
})
public class User {

    @JsonProperty("id")
    private String id;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("username")
    private String username;
    @JsonProperty("name")
    private String name;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("twitter_username")
    private String twitterUsername;
    @JsonProperty("portfolio_url")
    private String portfolioUrl;
    @JsonProperty("bio")
    private String bio;
    @JsonProperty("location")
    private String location;
    @JsonProperty("links")
    private UserLinks links;
    @JsonProperty("profile_image")
    private ProfileImage profileImage;
    @JsonProperty("total_collections")
    private Integer totalCollections;
    @JsonProperty("instagram_username")
    private String instagramUsername;
    @JsonProperty("total_likes")
    private Integer totalLikes;
    @JsonProperty("total_photos")
    private Integer totalPhotos;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("twitter_username")
    public String getTwitterUsername() {
        return twitterUsername;
    }

    @JsonProperty("twitter_username")
    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    @JsonProperty("portfolio_url")
    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    @JsonProperty("portfolio_url")
    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    @JsonProperty("bio")
    public String getBio() {
        return bio;
    }

    @JsonProperty("bio")
    public void setBio(String bio) {
        this.bio = bio;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("links")
    public UserLinks getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(UserLinks links) {
        this.links = links;
    }

    @JsonProperty("profile_image")
    public ProfileImage getProfileImage() {
        return profileImage;
    }

    @JsonProperty("profile_image")
    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    @JsonProperty("total_collections")
    public Integer getTotalCollections() {
        return totalCollections;
    }

    @JsonProperty("total_collections")
    public void setTotalCollections(Integer totalCollections) {
        this.totalCollections = totalCollections;
    }

    @JsonProperty("instagram_username")
    public String getInstagramUsername() {
        return instagramUsername;
    }

    @JsonProperty("instagram_username")
    public void setInstagramUsername(String instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    @JsonProperty("total_likes")
    public Integer getTotalLikes() {
        return totalLikes;
    }

    @JsonProperty("total_likes")
    public void setTotalLikes(Integer totalLikes) {
        this.totalLikes = totalLikes;
    }

    @JsonProperty("total_photos")
    public Integer getTotalPhotos() {
        return totalPhotos;
    }

    @JsonProperty("total_photos")
    public void setTotalPhotos(Integer totalPhotos) {
        this.totalPhotos = totalPhotos;
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
