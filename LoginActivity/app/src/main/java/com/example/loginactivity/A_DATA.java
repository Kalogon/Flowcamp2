package com.example.loginactivity;

public class A_DATA {
    private int id;
    private String name;
    private String tel;
    private String image;

    public A_DATA(int id, String name, String tel,String image){
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getTel() {return tel;}

    public void setTel(String tel) {this.tel = tel;}
}
