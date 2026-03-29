package com.example.polskierejestracje.Classes;

public class Rejestracja {
    private char literaWojewodztwa;
    private String skrot;
    private String nazwa;

    public char getLiteraWojewodztwa(){
        return this.literaWojewodztwa;
    }
    public String getSkrot(){
        return skrot;
    }

    public String getNazwa(){
        return nazwa;
    }
    public Rejestracja(char litera, String skrot, String nazwa){
        this.literaWojewodztwa = litera;
        this.skrot = skrot;
        this.nazwa = nazwa;
    }
    public Rejestracja(){}
}
