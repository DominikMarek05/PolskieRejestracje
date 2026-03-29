package com.example.polskierejestracje;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.polskierejestracje.Classes.ImplementArray;
import com.example.polskierejestracje.Classes.Rejestracja;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button pierwszaOdpowiedz;
    Button drugaOdpowiedz;
    Button trzeciaOdpowiedz;
    Button czwartaOdpowiedz;
    Button powrot;

    ArrayList<Button> kolekcjaPrzyciskow = new ArrayList<>();
    TextView rejestracja;
    TextView wynik;
    int wynikInt = 0;
    ArrayList<Rejestracja> wojewodztwoRzeszowskie = new ArrayList<>();
    ArrayList<Rejestracja> wszystkiePowiaty = new ArrayList<>();
    Rejestracja poprawnaRejestracja = new Rejestracja();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        pierwszaOdpowiedz = findViewById(R.id.pierwszaOdpowiedz);
        drugaOdpowiedz = findViewById(R.id.drugaOdpowiedz);
        trzeciaOdpowiedz = findViewById(R.id.trzeciaOdpowiedz);
        czwartaOdpowiedz = findViewById(R.id.czwartaOdpowiedz);
        powrot = findViewById(R.id.powrot);
        rejestracja = findViewById(R.id.rejestracja);
        wynik = findViewById(R.id.wynik);
        ImplementArray.stworzPodkarpackie(wojewodztwoRzeszowskie);

        kolekcjaPrzyciskow.add(pierwszaOdpowiedz);
        kolekcjaPrzyciskow.add(drugaOdpowiedz);
        kolekcjaPrzyciskow.add(trzeciaOdpowiedz);
        kolekcjaPrzyciskow.add(czwartaOdpowiedz);

        ImplementArray.stworzWszystkieWojewodztwa(wszystkiePowiaty); // Stworzenie puli rejestracji
        ustawNowaRejestracje(); // Zaimplementowanie poprawnej rejestracji

        ustawOdpowiedzi(); // Ustawienie nowych odpowiedzi do przycisków

        
        pierwszaOdpowiedz.setOnClickListener(v -> {
            //String nazwaSkrotu = wszystkiePowiaty.get(rejestracja.getText().toString());
                if (pierwszaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                    wynikInt++;
                    wynik.setText("Wynik: " + wynikInt);
                    ustawNowaRejestracje();
                    ustawOdpowiedzi();
                }
            });

        drugaOdpowiedz.setOnClickListener(v -> {
            if (drugaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawNowaRejestracje();
                ustawOdpowiedzi();
            }
        });

        trzeciaOdpowiedz.setOnClickListener(v -> {
            if (trzeciaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawNowaRejestracje();
                ustawOdpowiedzi();
            }
        });

        czwartaOdpowiedz.setOnClickListener(v -> {
            if (czwartaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawNowaRejestracje();
                ustawOdpowiedzi();
            }
        });

        powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void ustawOdpowiedzi(){
        ustawOdpowiedzWojewodztw(pierwszaOdpowiedz);
        ustawOdpowiedzWojewodztw(drugaOdpowiedz);
        ustawOdpowiedzWojewodztw(trzeciaOdpowiedz);
        ustawOdpowiedzWojewodztw(czwartaOdpowiedz);
        kolekcjaPrzyciskow.get((int)(Math.random()*4)).setText(poprawnaRejestracja.getNazwa());
    }
    public void ustawNowaRejestracje(){
        poprawnaRejestracja = wszystkiePowiaty.get((int)(Math.random()*wszystkiePowiaty.size()));
        rejestracja.setText(poprawnaRejestracja.getSkrot());
    }

    public void ustawOdpowiedzWojewodztw(Button b){
        b.setText(wszystkiePowiaty.get((int)(Math.random()*wszystkiePowiaty.size())).getNazwa());
    }

}