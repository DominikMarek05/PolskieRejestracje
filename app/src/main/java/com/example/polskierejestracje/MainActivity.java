package com.example.polskierejestracje;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView serce1;
    ImageView serce2;
    ImageView serce3;
    int wynikInt;
    int bledneOdpowiedzi;
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
        serce1 = findViewById(R.id.serce1);
        serce2 = findViewById(R.id.serce2);
        serce3 = findViewById(R.id.serce3);
        ImplementArray.stworzPodkarpackie(wojewodztwoRzeszowskie);

        kolekcjaPrzyciskow.add(pierwszaOdpowiedz);
        kolekcjaPrzyciskow.add(drugaOdpowiedz);
        kolekcjaPrzyciskow.add(trzeciaOdpowiedz);
        kolekcjaPrzyciskow.add(czwartaOdpowiedz);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MojeDane", Context.MODE_PRIVATE);
        wynikInt = sp.getInt("wynik", 0);
        bledneOdpowiedzi = sp.getInt("bledneOdpowiedzi", 0);

        ImplementArray.stworzWszystkieWojewodztwa(wszystkiePowiaty); // Stworzenie puli rejestracji
        ustawNowaRejestracje(); // Zaimplementowanie poprawnej rejestracji

        ustawOdpowiedzi(); // Ustawienie nowych odpowiedzi do przycisków

        // Początkowe ustawianie serc
        if(bledneOdpowiedzi!=0){
            ustawSerce(bledneOdpowiedzi);
        }
        pierwszaOdpowiedz.setOnClickListener(v -> {
            //String nazwaSkrotu = wszystkiePowiaty.get(rejestracja.getText().toString());
                if (pierwszaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                    wynikInt++;
                    wynik.setText("Wynik: " + wynikInt);
                    ustawNowaRejestracje();
                    ustawOdpowiedzi();
                }else{
                    ustawSerce(++bledneOdpowiedzi);
                    if(bledneOdpowiedzi==3){
                        wynikInt=0;
                        bledneOdpowiedzi=0;
                    }
                }
            });

        drugaOdpowiedz.setOnClickListener(v -> {
            if (drugaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawNowaRejestracje();
                ustawOdpowiedzi();
            }else{
                ustawSerce(++bledneOdpowiedzi);
                if(bledneOdpowiedzi==3){
                    wynikInt=0;
                    bledneOdpowiedzi=0;
                }
            }
        });

        trzeciaOdpowiedz.setOnClickListener(v -> {
            if (trzeciaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawNowaRejestracje();
                ustawOdpowiedzi();
            }else{
                ustawSerce(++bledneOdpowiedzi);
                if(bledneOdpowiedzi==3){
                    wynikInt=0;
                    bledneOdpowiedzi=0;
                }
            }
        });

        czwartaOdpowiedz.setOnClickListener(v -> {
            if (czwartaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawNowaRejestracje();
                ustawOdpowiedzi();
            }else{
                ustawSerce(++bledneOdpowiedzi);
                if(bledneOdpowiedzi==3){
                    wynikInt=0;
                    bledneOdpowiedzi=0;
                }
            }
        });

        powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edytor = sp.edit();
                edytor.putInt("wynik", wynikInt);
                edytor.putInt("bledneOdpowiedzi", bledneOdpowiedzi);
                edytor.apply();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    public void ustawOdpowiedzi(){
        ustawOdpowiedzWojewodztw(pierwszaOdpowiedz);
        ustawOdpowiedzWojewodztw(drugaOdpowiedz);
        ustawOdpowiedzWojewodztw(trzeciaOdpowiedz);
        ustawOdpowiedzWojewodztw(czwartaOdpowiedz);
        wynik.setText("Wynik: " + wynikInt);
        kolekcjaPrzyciskow.get((int)(Math.random()*4)).setText(poprawnaRejestracja.getNazwa());
    }
    public void ustawNowaRejestracje(){
        poprawnaRejestracja = wszystkiePowiaty.get((int)(Math.random()*wszystkiePowiaty.size()));
        rejestracja.setText(poprawnaRejestracja.getSkrot());
    }

    public void ustawOdpowiedzWojewodztw(Button b){
        b.setText(wszystkiePowiaty.get((int)(Math.random()*wszystkiePowiaty.size())).getNazwa());
    }
    public void ustawSerce(int numer){
        switch(numer){
            case 3: {
                serce3.setImageResource(R.drawable.emptyheart);
            }
            case 2: {
                serce2.setImageResource(R.drawable.emptyheart);
            }
            case 1:{
                serce1.setImageResource(R.drawable.emptyheart);
            }
        }
    }
}