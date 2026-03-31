package com.example.polskierejestracje;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
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
    Button powrotDoMenu;
    ArrayList<Button> kolekcjaPrzyciskow = new ArrayList<>();
    TextView rejestracja;
    TextView wynik;
    TextView napisPrzegranej;
    ImageView serce1;
    ImageView serce2;
    ImageView serce3;
    int wynikInt;
    int bledneOdpowiedzi;
    ArrayList<Rejestracja> wszystkiePowiaty = new ArrayList<>();
    Rejestracja poprawnaRejestracja = new Rejestracja();
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Pobranie wszystkich obiektów z .xml
        pierwszaOdpowiedz = findViewById(R.id.pierwszaOdpowiedz);
        drugaOdpowiedz = findViewById(R.id.drugaOdpowiedz);
        trzeciaOdpowiedz = findViewById(R.id.trzeciaOdpowiedz);
        czwartaOdpowiedz = findViewById(R.id.czwartaOdpowiedz);
        powrot = findViewById(R.id.powrot);
        powrotDoMenu = findViewById(R.id.powrotDoMenu);
        rejestracja = findViewById(R.id.rejestracja);
        wynik = findViewById(R.id.wynik);
        napisPrzegranej = findViewById(R.id.napisPrzegranej);
        serce1 = findViewById(R.id.serce1);
        serce2 = findViewById(R.id.serce2);
        serce3 = findViewById(R.id.serce3);

        kolekcjaPrzyciskow.add(pierwszaOdpowiedz);
        kolekcjaPrzyciskow.add(drugaOdpowiedz);
        kolekcjaPrzyciskow.add(trzeciaOdpowiedz);
        kolekcjaPrzyciskow.add(czwartaOdpowiedz);

        sp = getApplicationContext().getSharedPreferences("MojeDane", MODE_PRIVATE);
        bledneOdpowiedzi = sp.getInt("bledneOdpowiedzi", 0);
        wynikInt = sp.getInt("wynik", 0);
        ImplementArray.stworzWszystkieWojewodztwa(wszystkiePowiaty); // Stworzenie puli rejestracji

        // Wczytaj wartości po powrocie
        if(bledneOdpowiedzi!=0 || wynikInt!=0){
            ustawSerce(bledneOdpowiedzi);
            wczytajPoPauzie();
        }else{
            ustawNowaRejestracje(); // Zaimplementowanie poprawnej rejestracji
            ustawOdpowiedzi(); // Ustawienie nowych odpowiedzi do przycisków
        }

        // Ustawienie pierwszego przycisku
        pierwszaOdpowiedz.setOnClickListener(v -> {
                if (pierwszaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                    wynikInt++;
                    wynik.setText("Wynik: " + wynikInt);
                    ustawNowaRejestracje();
                    ustawOdpowiedzi();
                }else{
                    ustawSerce(++bledneOdpowiedzi);
                    if(bledneOdpowiedzi==3){
                        wyswietlKoniecGry();
                    }
                }
            });

        // Ustawienie drugiego przycisku
        drugaOdpowiedz.setOnClickListener(v -> {
            if (drugaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawNowaRejestracje();
                ustawOdpowiedzi();
            }else{
                ustawSerce(++bledneOdpowiedzi);
                if(bledneOdpowiedzi==3){
                    wyswietlKoniecGry();
                }
            }
        });

        // Ustawienie trzeciego przycisku
        trzeciaOdpowiedz.setOnClickListener(v -> {
            if (trzeciaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawNowaRejestracje();
                ustawOdpowiedzi();
            }else{
                ustawSerce(++bledneOdpowiedzi);
                if(bledneOdpowiedzi==3){
                    wyswietlKoniecGry();
                }
            }
        });

        // Ustawienie nowego przycisku
        czwartaOdpowiedz.setOnClickListener(v -> {
            if (czwartaOdpowiedz.getText().equals(poprawnaRejestracja.getNazwa())){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawNowaRejestracje();
                ustawOdpowiedzi();
            }else{
                ustawSerce(++bledneOdpowiedzi);
                if(bledneOdpowiedzi==3){
                    wyswietlKoniecGry();
                }
            }
        });

        // Obsługa pauzy
        powrot.setOnClickListener(view -> zapiszPrzyPauzie());

        // Obsługa powrotu do menu
        powrotDoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Blokada przycisku systemowego powrotu
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
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
    public void zapiszPrzyPauzie(){
        sp = getApplicationContext().getSharedPreferences("MojeDane", Context.MODE_PRIVATE);
        SharedPreferences.Editor edytor = sp.edit();
        edytor.putInt("wynik", wynikInt);
        edytor.putInt("bledneOdpowiedzi", bledneOdpowiedzi);
        edytor.putString("pierwszaOdpowiedz", pierwszaOdpowiedz.getText().toString());
        edytor.putString("drugaOdpowiedz", drugaOdpowiedz.getText().toString());
        edytor.putString("trzeciaOdpowiedz", trzeciaOdpowiedz.getText().toString());
        edytor.putString("czwartaOdpowiedz", czwartaOdpowiedz.getText().toString());
        edytor.putString("skrot", poprawnaRejestracja.getSkrot());
        edytor.putString("poprawnaOdpowiedz", poprawnaRejestracja.getNazwa());
        edytor.apply();
        finish();
    }
    public void wczytajPoPauzie(){
        sp = getApplicationContext().getSharedPreferences("MojeDane", Context.MODE_PRIVATE);
        wynikInt = sp.getInt("wynik", 0);
        wynik.setText("Wynik: " + wynikInt);
        bledneOdpowiedzi = sp.getInt("bledneOdpowiedzi", 0);
        pierwszaOdpowiedz.setText(sp.getString("pierwszaOdpowiedz", ""));
        drugaOdpowiedz.setText(sp.getString("drugaOdpowiedz", ""));
        trzeciaOdpowiedz.setText(sp.getString("trzeciaOdpowiedz", ""));
        czwartaOdpowiedz.setText(sp.getString("czwartaOdpowiedz", ""));
        rejestracja.setText(sp.getString("skrot", ""));

        poprawnaRejestracja.setNazwa(sp.getString("poprawnaOdpowiedz", ""));
        poprawnaRejestracja.setSkrot(sp.getString("skrot",""));
    }
    public void wyswietlKoniecGry(){
        sp = getApplicationContext().getSharedPreferences("MojeDane", MODE_PRIVATE);
        SharedPreferences.Editor edytor = sp.edit();
        edytor.putInt("wynik", 0);
        edytor.putInt("bledneOdpowiedzi", 0);
        edytor.apply();
        powrotDoMenu.setVisibility(View.VISIBLE);
        napisPrzegranej.setVisibility(View.VISIBLE);
        pierwszaOdpowiedz.setVisibility(View.GONE);
        drugaOdpowiedz.setVisibility(View.GONE);
        trzeciaOdpowiedz.setVisibility(View.GONE);
        czwartaOdpowiedz.setVisibility(View.GONE);
    }

    // Rysowanie serc
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