package com.group6.tinderforfood;

public class FoodCategory { //FoodCategory object represents a single food category ie pizza, Chinese, American, etc
    private String name; //The name of the category
    private String picUrl; //The link (online or offline) to the image representation of a category

    public FoodCategory(String name){
        this.name = name;
        picUrl = "@drawable/" + name + ".jpg";
        //for the purposes of this app prototype, the image used for each category will be a .jpg with the same name stored in res/drawable/
    }
}
