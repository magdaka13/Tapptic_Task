package com.mg.tapptic_task;


public class Item {
    private String mText;
    private String mImage;

    public Item(String text, String img){
        mText = text;
        mImage = img;
    }

    public String getmText() {
        return mText;
    }

    public String getmImage() {
        return mImage;
    }
}