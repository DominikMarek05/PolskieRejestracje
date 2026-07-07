package com.example.polskierejestracje.Classes;

public class Wynik {
    private int wynik;
    private String data;

    public int getWynik() {
        return wynik;
    }
    public Wynik(int wynik, String data){
        this.wynik = wynik;
        this.data = data;
    }
    public String wypiszWynik(){
        return wynik + " " + data;
    }
}
