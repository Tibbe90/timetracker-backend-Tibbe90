package com.tibbelin.tajmtrackr.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Color enbart för att låta användaren påverka sitt UI

@Document(collection = "categories")
public class Category {
    
    @Id
    private String id;
    private String userId;
    private String categoryName;
    private String color;

    public Category() {}

    public Category(String id, String categoryName, String color, String userId) {
        this.id = id;
        this.userId = userId;
        this.categoryName = categoryName;
        this.color = color;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    
}
