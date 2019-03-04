package com.lightsys.audioapp;

public class Lesson {
    String name;
    String mp3;
    String textData;

    Lesson(String name){
        this.name = name;
    }
    Lesson(String name,String mp3){
        this.name = name;
        this.mp3 = mp3;
    }
    Lesson(String name,String mp3,String textData){
        this.name = name;
        this.mp3 = mp3;
        this.textData = textData;
    }

    public String getName() {
        return name;
    }

    public String getMp3() {
        return mp3;
    }

    public String getTextData() {
        return textData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }
}
