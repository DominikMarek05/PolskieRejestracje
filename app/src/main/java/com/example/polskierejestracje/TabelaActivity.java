package com.example.polskierejestracje;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.polskierejestracje.Classes.Wynik;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TabelaActivity extends AppCompatActivity {

    Button powrot;
    ListView lista;
    ArrayList<Wynik> tablicaWynikow = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabela);
        powrot = findViewById(R.id.powrotZTabeli);
        lista = findViewById(R.id.listaWynikow);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MojeDane", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("wyniki", "");
        Type typ = new TypeToken<ArrayList<Wynik>>() {}.getType();
        tablicaWynikow = gson.fromJson(json, typ);
        tablicaWynikow = segregujTablice(tablicaWynikow);
        ArrayList<String> tablicaWynikowString = konwertujTabliceWynikow(tablicaWynikow);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.wiersz, tablicaWynikowString);
        lista.setAdapter(adapter);

        powrot.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
        });

    }
    private ArrayList<Wynik> segregujTablice(ArrayList<Wynik> w){
        ArrayList<Wynik> nowaTablica = new ArrayList<>();
        nowaTablica.add(w.get(0));
        for(int i = 1; i < w.size(); i++){
            if(w.get(i).getWynik() < w.get(i-1).getWynik()){
                nowaTablica.addLast(w.get(i));
            }else{
                nowaTablica.addFirst(w.get(i));
            }
        }
        return nowaTablica;
    }
    private ArrayList<String> konwertujTabliceWynikow(ArrayList<Wynik> w){
        ArrayList<String> wynik = new ArrayList<>();
        int i = 0;
        for(Wynik el : w){
            wynik.add(++i + ". " + el.wypiszWynik());
        }
        return wynik;
    }
}
