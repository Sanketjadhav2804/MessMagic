package com.example.messmagic.menu;

public class UploadSpecialMenu {
    String  menuName , menuDescription , imageUrl;

    public UploadSpecialMenu(){

    }

    public UploadSpecialMenu(String menuName, String menuDescription, String imageUrl) {
        this.menuName = menuName;
        this.menuDescription = menuDescription;
        this.imageUrl = imageUrl;
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
