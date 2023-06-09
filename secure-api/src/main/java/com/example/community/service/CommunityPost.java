package com.example.community.service;

import com.example.community.entity.CommunityPostEntity;

import java.io.Serializable;
import java.util.UUID;

public class CommunityPost implements Serializable {

    private UUID identifier;
    private String title;
    private String content;
    private UUID author;

    public CommunityPost() {
    }

    public CommunityPost(CommunityPostEntity communityPostEntity) {
        this.identifier = communityPostEntity.getIdentifier();
        this.title = communityPostEntity.getTitle();
        this.content = communityPostEntity.getContent();
        this.author = communityPostEntity.getAuthor();
    }

    public CommunityPost(UUID identifier, String title, String content, UUID author, String email, String vehicle) {
        this.identifier = identifier;
        this.title = title;
        this.content = content;
        this.author = author;
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

    @Override
    public String toString() {
        return "CommunityPost{" +
                "identifier=" + identifier +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                '}';
    }

    public CommunityPostEntity toEntity() {
        return new CommunityPostEntity(this.identifier, this.title,
                this.content, this.author, null, null);
    }
}
