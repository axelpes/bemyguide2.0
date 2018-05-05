package com.example.thomas.projet_signup.com.example.thomas.model;


/**
 * Created by Erdrixx on 29/03/2016.
 */
public class PointOfInterest {
    private String namePoint;
    private String nameGoogle;

    private String checkboxstyle;
    private String color;

    public PointOfInterest(String namePoint, String nameGoogle, String checkboxstyle, String color) {
        this.namePoint = namePoint;
        this.nameGoogle = nameGoogle;
        this.checkboxstyle = checkboxstyle;
        this.color = color;
    }

    public String getNamePoint() {return namePoint;}

    public void setNamePoint(String namePoint) {this.namePoint = namePoint;}

    public String getNameGoogle() {return nameGoogle; }

    public void setNameGoogle(String nameGoogle) {this.nameGoogle = nameGoogle;}

    public String getCheckboxstyle() { return checkboxstyle; }

    public void setCheckboxstyle(String checkboxstyle) { this.checkboxstyle = checkboxstyle; }

    public String getColor(){ return color; }

    public void setColor(String color){ this.color = color; }
}
