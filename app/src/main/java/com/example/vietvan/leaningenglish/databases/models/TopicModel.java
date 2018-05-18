package com.example.vietvan.leaningenglish.databases.models;

import java.io.Serializable;

/**
 * Created by VietVan on 01/03/2018.
 */

public class TopicModel implements Serializable{
    public int id;
    public String name, imageUrl, category, color, lastTime;

    public TopicModel(int id, String name, String imageUrl, String category, String color, String lastTime) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.color = color;
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "TopicModel{" +
                "name='" + name + '\'' +
                '}';
    }

}
