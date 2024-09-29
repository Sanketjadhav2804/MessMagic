package com.example.messmagic.menu;

public class UploadWeekMenu {

    String weekday , menuName , menuDescription , imageUrl;


     public UploadWeekMenu(){

     }

    public UploadWeekMenu(String weekday, String menuName, String menuDescription, String imageUrl) {
        this.weekday = weekday;
        this.menuName = menuName;
        this.menuDescription = menuDescription;
        this.imageUrl = imageUrl;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
