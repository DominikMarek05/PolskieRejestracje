package com.example.polskierejestracje;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    Button okej;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        okej = findViewById(R.id.okej);
        SharedPreferences sp = getSharedPreferences("MojeDane", MODE_PRIVATE);
        SharedPreferences.Editor edytor = sp.edit();
        edytor.putInt("wynik", 0);
        edytor.putInt("bledneOdpowiedzi", 0);
        edytor.apply();

        okej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });
    }
}