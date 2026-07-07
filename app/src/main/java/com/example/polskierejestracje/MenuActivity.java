package com.example.polskierejestracje;

import static android.widget.Toast.LENGTH_LONG;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    Button okej;
    Button tabela;
    Button wyjdz;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        okej = findViewById(R.id.okej);
        tabela = findViewById(R.id.tabelaPrzycisk);
        wyjdz = findViewById(R.id.wyjdz);
        wyczyscDane();

        okej.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        tabela.setOnClickListener(v -> {
            sp = getSharedPreferences("MojeDane", MODE_PRIVATE);
            if(sp.getString("wyniki", "").isEmpty()){
                Toast toast = Toast.makeText(this, "Brak wyników :(", LENGTH_LONG);
                toast.show();
            }else{
                Intent intent = new Intent(MenuActivity.this, TabelaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        wyjdz.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });
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
        edytor.putBoolean("stanPierwszejOdpowiedzi", true);
        edytor.putBoolean("stanDrugiejOdpowiedzi", true);
        edytor.putBoolean("stanTrzeciejOdpowiedzi", true);
        edytor.putBoolean("stanCzwartejOdpowiedzi", true);
        edytor.apply();
    }
}