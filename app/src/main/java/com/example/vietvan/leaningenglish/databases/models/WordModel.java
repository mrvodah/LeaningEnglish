package com.example.vietvan.leaningenglish.databases.models;

/**
 * Created by VietVan on 06/03/2018.
 */

public class WordModel {
    public int id;
    public String origin, explanation, type, pronunciation, image_url, example, example_translation;
    public int topic_id, level;

    public WordModel(int id, String origin, String explanation, String type, String pronunciation, String image_url, String example, String example_translation, int topic_id, int level) {
        this.id = id;
        this.origin = origin;
        this.explanation = explanation;
        this.type = type;
        this.pronunciation = pronunciation;
        this.image_url = image_url;
        this.example = example;
        this.example_translation = example_translation;
        this.topic_id = topic_id;
        this.level = level;
    }

    @Override
    public String toString() {
        return "WordModel{" +
                "origin='" + origin + '\'' +
                '}';
    }

}
