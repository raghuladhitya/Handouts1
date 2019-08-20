package com.example.handouts;

public class Register {

    String name;
    String pass;
    String type;

    public Register(){

    }

    public Register(String name, String pass, String type){
        this.name=name;
        this.pass=pass;
        this.type=type;
    }

    public String getName(){
        return name;
    }

    public String getPass(){
        return pass;
    }

    public String getType(){
        return type;
    }

}