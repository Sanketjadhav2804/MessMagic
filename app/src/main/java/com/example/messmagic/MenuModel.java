package com.example.messmagic;

public class MenuModel {

        private boolean expandable;
        private  String weekDay , menuNAme , desc;
        private int  other;

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public MenuModel(String weekDay, String menuNAme, int other, String desc) {
        this.weekDay = weekDay;
        this.menuNAme = menuNAme;
        this.other = other;
        this.desc = desc;
        this.expandable=false;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getMenuNAme() {
        return menuNAme;
    }

    public void setMenuNAme(String menuNAme) {
        this.menuNAme = menuNAme;
    }

    public int getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = Integer.parseInt(other);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "MenuModel{" +
                "weekDay='" + weekDay + '\'' +
                ", menuNAme='" + menuNAme + '\'' +
                ", other='" + other + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
