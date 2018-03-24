
package com.jacob.unsplash.model;

import java.util.HashMap;
import java.util.List;
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
    "created_at",
    "updated_at",
    "width",
    "height",
    "color",
    "description",
    "categories",
    "user",
    "urls",
    "links",
    "liked_by_user",
    "sponsored",
    "likes",
    "current_user_collections",
    "photo_tags"
})
public class Result {

    @JsonProperty("id")
    private String id;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("width")
    private Integer width;
    @JsonProperty("height")
    private Integer height;
    @JsonProperty("color")
    private String color;
    @JsonProperty("description")
    private Object description;
    @JsonProperty("categories")
    private List<Object> categories = null;
    @JsonProperty("user")
    private User user;
    @JsonProperty("urls")
    private Urls urls;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("liked_by_user")
    private Boolean likedByUser;
    @JsonProperty("sponsored")
    private Boolean sponsored;
    @JsonProperty("likes")
    private Integer likes;
    @JsonProperty("current_user_collections")
    private List<Object> currentUserCollections = null;
    @JsonProperty("photo_tags")
    private List<PhotoTag> photoTags = null;
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

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("width")
    public Integer getWidth() {
        return width;
    }

    @JsonProperty("width")
    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty("height")
    public Integer getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(Integer height) {
        this.height = height;
    }

    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
    }

    @JsonProperty("description")
    public Object getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(Object description) {
        this.description = description;
    }

    @JsonProperty("categories")
    public List<Object> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("urls")
    public Urls getUrls() {
        return urls;
    }

    @JsonProperty("urls")
    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("liked_by_user")
    public Boolean getLikedByUser() {
        return likedByUser;
    }

    @JsonProperty("liked_by_user")
    public void setLikedByUser(Boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    @JsonProperty("sponsored")
    public Boolean getSponsored() {
        return sponsored;
    }

    @JsonProperty("sponsored")
    public void setSponsored(Boolean sponsored) {
        this.sponsored = sponsored;
    }

    @JsonProperty("likes")
    public Integer getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @JsonProperty("current_user_collections")
    public List<Object> getCurrentUserCollections() {
        return currentUserCollections;
    }

    @JsonProperty("current_user_collections")
    public void setCurrentUserCollections(List<Object> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
    }

    @JsonProperty("photo_tags")
    public List<PhotoTag> getPhotoTags() {
        return photoTags;
    }

    @JsonProperty("photo_tags")
    public void setPhotoTags(List<PhotoTag> photoTags) {
        this.photoTags = photoTags;
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
