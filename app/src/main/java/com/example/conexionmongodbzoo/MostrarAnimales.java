package com.example.conexionmongodbzoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MostrarAnimales extends AppCompatActivity {
    private ArrayList<Animal> array;
    private Animal animal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_animales);
        String particularidad = getIntent().getStringExtra("particularidad");
        String origen = getIntent().getStringExtra("origen");
        if(particularidad!=null || origen!=null) {
            EditText mostrarParticularidad =  findViewById(R.id.textoMostrarParticularidad);
            EditText mostrarOrigen = findViewById(R.id.textoMostrarOrigen);
            mostrarParticularidad.setText(particularidad);
            mostrarOrigen.setText(origen);
            TextView textoParticularidad=findViewById(R.id.textView);
            textoParticularidad.setText("Particularidad del animal: ");
            TextView textoOrigen = findViewById(R.id.idOrigenAnimal);
            textoOrigen.setText("Origen del animal: ");
        }else{
            Toast.makeText(getApplicationContext(), "Mostrando actividad sin datos!!", Toast.LENGTH_SHORT).show();
        }

    }


    public void volverMain(View vista){
        Intent i=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }


}