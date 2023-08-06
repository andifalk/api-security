package com.example.client;

import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("unused")
public class CommunityPost implements Serializable {

    private UUID identifier;
    private String title;
    private String content;
    private UUID author;
    private String email;
    private String vehicle;

    public CommunityPost() {
    }

    public CommunityPost(UUID identifier, String title, String content, UUID author, String email, String vehicle) {
        this.identifier = identifier;
        this.title = title;
        this.content = content;
        this.author = author;
        this.email = email;
        this.vehicle = vehicle;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getAuthor() {
        return author;
    }

    public void setAuthor(UUID author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "CommunityPost{" +
                "identifier=" + identifier +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", email='" + email + '\'' +
                ", vehicle='" + vehicle + '\'' +
                '}';
    }
}
