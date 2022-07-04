package com.example.theticket;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Adate {
    private String full_name;


    public Adate(String full_name) {
        this.full_name = full_name;
    }


    public String getDate() {
        return full_name;
    }
}



