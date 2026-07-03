package com.example.polskierejestracje;

import static java.lang.Character.toUpperCase;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.polskierejestracje.Classes.ImplementArray;
import com.example.polskierejestracje.Classes.Rejestracja;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    Button pierwszaOdpowiedz;
    Button drugaOdpowiedz;
    Button trzeciaOdpowiedz;
    Button czwartaOdpowiedz;
    Button pauza;
    Button powrotDoMenu;
    ArrayList<Button> kolekcjaPrzyciskow = new ArrayList<>();
    TextView rejestracja;
    TextView wynik;
    TextView napisPrzegranej;
    ImageView serce1;
    ImageView serce2;
    ImageView serce3;
    RelativeLayout glownyLayout;
    int wynikInt;
    int bledneOdpowiedzi;
    ArrayList<Rejestracja> wszystkiePowiaty = new ArrayList<>();
    Rejestracja poprawnaRejestracja = new Rejestracja();
    SharedPreferences sp;
    Boolean przelacznik = true;

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
        pauza = findViewById(R.id.pauza);
        powrotDoMenu = findViewById(R.id.powrotDoMenu);
        rejestracja = findViewById(R.id.rejestracja);
        wynik = findViewById(R.id.wynik);
        napisPrzegranej = findViewById(R.id.napisPrzegranej);
        serce1 = findViewById(R.id.serce1);
        serce2 = findViewById(R.id.serce2);
        serce3 = findViewById(R.id.serce3);
        glownyLayout = findViewById(R.id.main);

        kolekcjaPrzyciskow.add(pierwszaOdpowiedz);
        kolekcjaPrzyciskow.add(drugaOdpowiedz);
        kolekcjaPrzyciskow.add(trzeciaOdpowiedz);
        kolekcjaPrzyciskow.add(czwartaOdpowiedz);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Wyłączanie wygaszania w momencie gry

        // Wczytaj wartości z SharedPreferences
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
        if(pierwszaOdpowiedz.isClickable()){
            pierwszaOdpowiedz.setOnClickListener(v -> obsluzPrzycisk(pierwszaOdpowiedz));
        }

        // Ustawienie drugiego przycisku
        if(drugaOdpowiedz.isClickable()){
            drugaOdpowiedz.setOnClickListener(v -> obsluzPrzycisk(drugaOdpowiedz));
        }

        // Ustawienie trzeciego przycisku
        if(trzeciaOdpowiedz.isClickable()){
            trzeciaOdpowiedz.setOnClickListener(v -> obsluzPrzycisk(trzeciaOdpowiedz));
        }

        // Ustawienie czwartego przycisku
        if(czwartaOdpowiedz.isClickable()){
            czwartaOdpowiedz.setOnClickListener(v -> obsluzPrzycisk(czwartaOdpowiedz));
        }

        pauza.setOnClickListener(v -> zapiszPrzyPauzie()); // Obsługa pauzy

        powrotDoMenu.setOnClickListener(v -> zakonczGre()); // Obsługa powrotu do menu

        // Blokada przycisku systemowego powrotu
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public void ustawOdpowiedzi(){
        ustawOdpowiedzWojewodztw(kolekcjaPrzyciskow);

        wynik.setText("Wynik: " + wynikInt);
        kolekcjaPrzyciskow.get((int)(Math.random()*4)).setText(poprawnaRejestracja.getNazwa());
    }
    public void ustawNowaRejestracje(){
        poprawnaRejestracja = wszystkiePowiaty.get((int)(Math.random()*wszystkiePowiaty.size()));
        rejestracja.setText(poprawnaRejestracja.getSkrot());
    }

    public void ustawOdpowiedzWojewodztw(ArrayList<Button> b){
        HashSet<Rejestracja> pulaOdpowiedzi = new HashSet<>();
        for(Rejestracja rej : wszystkiePowiaty){
            if(konwerterZnakow(rej.getNazwa().charAt(0)) == poprawnaRejestracja.getSkrot().charAt(1) && !rej.getSkrot().equals(poprawnaRejestracja.getSkrot())) {
                if(!pierwszaOdpowiedz.getText().toString().equals(rej.getNazwa()) && !drugaOdpowiedz.getText().toString().equals(rej.getNazwa()) && !trzeciaOdpowiedz.getText().toString().equals(rej.getNazwa()) && !czwartaOdpowiedz.getText().toString().equals(rej.getNazwa())) pulaOdpowiedzi.add(rej);
            }
        }
        if(pulaOdpowiedzi.size()>=4) { // Jeżeli możliwych odpowiedzi jest więcej niż 3, ustaw je na przyciskach
            for(int i = 0; i < 4; i++){
                int losowaWartosc = (int) (Math.random() * pulaOdpowiedzi.size());
                int j = 0;
                for (Rejestracja el : pulaOdpowiedzi) {
                    if (j == losowaWartosc){
                        b.get(i).setText(el.getNazwa());
                    }
                    j++;
                }
            }
        }else{
            for(int i = 0; i < 4; i++){
                b.get(i).setText(wszystkiePowiaty.get((int)(Math.random()*wszystkiePowiaty.size())).getNazwa());
            }
        }
    }
    public void obsluzPrzycisk(Button przycisk){
        if (przycisk.getText().equals(poprawnaRejestracja.getNazwa())){
            wynikInt++;
            wynik.setText("Wynik: " + wynikInt);
            ustawNowaRejestracje();
            ustawOdpowiedzi();
            if(!pierwszaOdpowiedz.isActivated()){
                wlaczKonkretnyPrzycisk(pierwszaOdpowiedz);
            }
            if(!drugaOdpowiedz.isActivated()){
                wlaczKonkretnyPrzycisk(drugaOdpowiedz);
            }
            if(!trzeciaOdpowiedz.isActivated()){
                wlaczKonkretnyPrzycisk(trzeciaOdpowiedz);
            }
            if(!czwartaOdpowiedz.isActivated()){
                wlaczKonkretnyPrzycisk(czwartaOdpowiedz);
            }
        }else{
            animacjaSerc(++bledneOdpowiedzi); // Inkrementacja błędnej odpowiedzi i nadanie animacji sercu
            ustawSerce(bledneOdpowiedzi); // Wczytanie odpowiedniej grafiki serduszka
            wylaczKonkretnyPrzycisk(przycisk); // Odrzucenie przycisku w którym już wiemy, że jest błędna odpowiedź
            if(bledneOdpowiedzi==3){
                wyswietlKoniecGry();
            }
        }
    }
    public void wylaczKonkretnyPrzycisk(Button przycisk){
        przycisk.setClickable(false);
        przycisk.setAlpha(0.5f);
        przycisk.setBackgroundResource(R.drawable.buttonwrong);
    }
    public void wlaczKonkretnyPrzycisk(Button przycisk){
        przycisk.setClickable(true);
        przycisk.setAlpha(1.0f);
        przycisk.setBackgroundResource(R.drawable.button);
    }
    public void zapiszPrzyPauzie(){
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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

        edytor.putBoolean("stanPierwszejOdpowiedzi", pierwszaOdpowiedz.isClickable());
        edytor.putBoolean("stanDrugiejOdpowiedzi", drugaOdpowiedz.isClickable());
        edytor.putBoolean("stanTrzeciejOdpowiedzi", trzeciaOdpowiedz.isClickable());
        edytor.putBoolean("stanCzwartejOdpowiedzi", czwartaOdpowiedz.isClickable());
        edytor.apply();
        wygenerujPolePauzy();
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

        // Wczytanie niepoprawnych odpowiedzi po powrocie z menu
        if(!sp.getBoolean("stanPierwszejOdpowiedzi", true)) wylaczKonkretnyPrzycisk(pierwszaOdpowiedz);
        if(!sp.getBoolean("stanDrugiejOdpowiedzi", true)) wylaczKonkretnyPrzycisk(drugaOdpowiedz);
        if(!sp.getBoolean("stanTrzeciejOdpowiedzi", true)) wylaczKonkretnyPrzycisk(trzeciaOdpowiedz);
        if(!sp.getBoolean("stanCzwartejOdpowiedzi", true)) wylaczKonkretnyPrzycisk(czwartaOdpowiedz);
    }
    public void wyswietlKoniecGry(){
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
        pauza.setVisibility(View.GONE);

        // Przesunięcie wyniku po końcu gry
        Animation przesuniecie = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ruchwyniku);
        wynik.startAnimation(przesuniecie);
    }
    @SuppressLint("ResourceType")
    public void wygenerujPolePauzy(){
        RelativeLayout.LayoutParams poleWlasciwosci = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams przycisk1Wlasciwosci = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams przycisk2Wlasciwosci = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView polePauzy = new ImageView(MainActivity.this);
        Button przyciskDoMenu = new Button(MainActivity.this);
        Button przyciskDoWznowieniaGry = new Button(MainActivity.this);

        // Generowanie pola pauzy
        poleWlasciwosci.addRule(RelativeLayout.CENTER_HORIZONTAL);
        poleWlasciwosci.addRule(RelativeLayout.CENTER_VERTICAL);

        polePauzy.setLayoutParams(poleWlasciwosci);
        polePauzy.setBackgroundColor(Color.BLACK);
        polePauzy.getBackground().setAlpha(127);
        // Generowanie przycisku do wznowienia gry
        przycisk1Wlasciwosci.addRule(RelativeLayout.CENTER_IN_PARENT);
        przycisk1Wlasciwosci.setMargins(80, 20, 80 ,20);

        przyciskDoWznowieniaGry.setId(1);
        przyciskDoWznowieniaGry.setLayoutParams(przycisk1Wlasciwosci);
        przyciskDoWznowieniaGry.setText("Wznowienie");
        przyciskDoWznowieniaGry.setTextSize(20.0f);
        przyciskDoWznowieniaGry.setTextColor(Color.BLACK);
        przyciskDoWznowieniaGry.setPadding(20, 20, 20, 20);
        przyciskDoWznowieniaGry.setBackgroundResource(R.drawable.button);
        // Przycisk do menu
        przycisk2Wlasciwosci.addRule(RelativeLayout.BELOW, 1);
        przycisk2Wlasciwosci.setMargins(80, 20, 80 ,20);

        przyciskDoMenu.setLayoutParams(przycisk2Wlasciwosci);
        przyciskDoMenu.setText("Wyjście");
        przyciskDoMenu.setTextSize(20.0f);
        przyciskDoMenu.setTextColor(Color.BLACK);
        przyciskDoMenu.setPadding(20, 20, 20, 20);
        przyciskDoMenu.setBackgroundResource(R.drawable.button);

        // Dodanie wszystkich wygenerowanych przycisków do głównego layoutu
        glownyLayout.addView(polePauzy);
        glownyLayout.addView(przyciskDoMenu);
        glownyLayout.addView(przyciskDoWznowieniaGry);

        przelaczWidocznoscPrzyciskow(); // Wyłączenie przycisków w momencie pauzy

        przyciskDoMenu.setOnClickListener(v -> {
            przelaczWidocznoscPrzyciskow(); // Włączenie przycisków zaraz przed wyjściem do menu
            zakonczGre();
        });
        przyciskDoWznowieniaGry.setOnClickListener(v -> {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            glownyLayout.removeView(polePauzy);
            glownyLayout.removeView(przyciskDoMenu);
            glownyLayout.removeView(przyciskDoWznowieniaGry);

            przelaczWidocznoscPrzyciskow();
        });
    }
    public void przelaczWidocznoscPrzyciskow(){
        przelacznik=!przelacznik;
        pauza.setEnabled(przelacznik);
        pierwszaOdpowiedz.setEnabled(przelacznik);
        drugaOdpowiedz.setEnabled(przelacznik);
        trzeciaOdpowiedz.setEnabled(przelacznik);
        czwartaOdpowiedz.setEnabled(przelacznik);
    }
    public void zakonczGre() {
        przelaczWidocznoscPrzyciskow();
        finish();
        overridePendingTransition(0, 0);
    }

    public char konwerterZnakow(char c){
        switch(c){
            case 'Ł':
            case 'ł':
                return 'L';
            case 'Ś':
            case 'ś':
                return 'S';
            case 'Ż':
            case 'ż':
                return 'Z';
        }
        return toUpperCase(c);
    }
    // Rysowanie serc
    public void ustawSerce(int numer){
        switch(numer){
            case 3:
                serce3.setImageResource(R.drawable.emptyheart);
            case 2:
                serce2.setImageResource(R.drawable.emptyheart);
            case 1:
                serce1.setImageResource(R.drawable.emptyheart);
        }
    }
    // Animacja serc
    public void animacjaSerc(int numer){
        Animation drgaj = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drganie);
        switch(numer){
            case 1:
                serce1.startAnimation(drgaj);
                break;
            case 2:
                serce2.startAnimation(drgaj);
                break;
            case 3:
                serce3.startAnimation(drgaj);
                break;
        }
    }

}