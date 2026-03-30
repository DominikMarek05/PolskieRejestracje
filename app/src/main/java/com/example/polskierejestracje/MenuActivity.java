package com.example.polskierejestracje;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {

    Button okej;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        okej = findViewById(R.id.okej);
        wyczyscDane();

        okej.setOnClickListener(view -> startActivity(new Intent(MenuActivity.this, MainActivity.class)));
    }
    public void wyczyscDane(){
        sp = getSharedPreferences("MojeDane", MODE_PRIVATE);
        SharedPreferences.Editor edytor = sp.edit();
        edytor.putInt("wynik", 0);
        edytor.putInt("bledneOdpowiedzi", 0);
        edytor.putString("pierwszaOdpowiedz", "");
        edytor.putString("drugaOdpowiedz", "");
        edytor.putString("trzeciaOdpowiedz", "");
        edytor.putString("czwartaOdpowiedz", "");
        edytor.apply();
    }
}