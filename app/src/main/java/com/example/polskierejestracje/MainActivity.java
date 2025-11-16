package com.example.polskierejestracje;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.polskierejestracje.Classes.ImplementArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
    HashMap<String, String> wojewodztwoRzeszowskie = new HashMap<>();
    HashMap<String, String> wszystkiePowiaty = new HashMap<>();
    HashMap<String, String> wojewodztwoRzeszowskieWybierz = new HashMap<>();
    Random random = new Random();
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
        ImplementArray.stworzPodkarpackie(wojewodztwoRzeszowskieWybierz);

        kolekcjaPrzyciskow.add(pierwszaOdpowiedz);
        kolekcjaPrzyciskow.add(drugaOdpowiedz);
        kolekcjaPrzyciskow.add(trzeciaOdpowiedz);
        kolekcjaPrzyciskow.add(czwartaOdpowiedz);


        ImplementArray.stworzWszystkieWojewodztwa(wszystkiePowiaty);
        ImplementArray.stworzPodkarpackie(wojewodztwoRzeszowskie);

        ustawRejestracje();
        ustawOdpowiedzi();

        
        pierwszaOdpowiedz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nazwaSkrotu = wszystkiePowiaty.get(rejestracja.getText().toString());
                    if (pierwszaOdpowiedz.getText() == nazwaSkrotu){
                        wynikInt++;
                        wynik.setText("Wynik: " + wynikInt);
                        ustawRejestracje();
                        ustawOdpowiedzi();
                    }
                }
        });

        drugaOdpowiedz.setOnClickListener(v -> {
            String nazwaSkrotu = wszystkiePowiaty.get(rejestracja.getText().toString());
            if (drugaOdpowiedz.getText() == nazwaSkrotu){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawRejestracje();
                ustawOdpowiedzi();
            }
        });

        trzeciaOdpowiedz.setOnClickListener(v -> {
            String nazwaSkrotu = wszystkiePowiaty.get(rejestracja.getText().toString());
            if (trzeciaOdpowiedz.getText() == nazwaSkrotu){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawRejestracje();
                ustawOdpowiedzi();
            }
        });

        czwartaOdpowiedz.setOnClickListener(v -> {
            String nazwaSkrotu = wszystkiePowiaty.get(rejestracja.getText().toString());
            if (czwartaOdpowiedz.getText() == nazwaSkrotu){
                wynikInt++;
                wynik.setText("Wynik: " + wynikInt);
                ustawRejestracje();
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

    public void podstawPoprawnaOdpowiedz(){
        kolekcjaPrzyciskow.get(random.nextInt(kolekcjaPrzyciskow.size())).setText(wszystkiePowiaty.get(rejestracja.getText().toString()));
    }
    public void ustawOdpowiedzi(){
        ustawOdpowiedzWojewodztw(pierwszaOdpowiedz);
        ustawOdpowiedzWojewodztw(drugaOdpowiedz);
        ustawOdpowiedzWojewodztw(trzeciaOdpowiedz);
        ustawOdpowiedzWojewodztw(czwartaOdpowiedz);
        podstawPoprawnaOdpowiedz();
    }

    public void ustawRejestracje(){

        String[] wartosci = wszystkiePowiaty.keySet().toArray(new String[0]);
        rejestracja.setText(wartosci[random.nextInt(wartosci.length)]);
    }

    public void ustawOdpowiedzWojewodztw(Button b){
        String[] wartosci = wszystkiePowiaty.values().toArray(new String[0]);
        String losowaWartosc = wartosci[random.nextInt(wartosci.length)];
        b.setText(losowaWartosc);
    }

}