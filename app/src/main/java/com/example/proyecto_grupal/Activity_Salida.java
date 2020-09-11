package com.example.proyecto_grupal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import Agenetico.Algoritmo;

public class Activity_Salida extends AppCompatActivity {

    public static ArrayList<Integer> variables;
    public EditText Atx_Campo;
    public String poblacion,iteracion1,soporte1;
    public Boolean nosolucion;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__salida);
        Atx_Campo= (EditText) findViewById(R.id.Atx_Campo);
        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        poblacion= bundle.getString("txtpoblacion");
        iteracion1= bundle.getString("txtiteracion");
        soporte1= bundle.getString("txtsoporte");
        nosolucion= bundle.getBoolean("Chck_Solucion");
        //Atx_Campo.setEnabled(false);
        variables = new ArrayList<>();
        //Obtener variables Seleccionadas
        //MainActivity mainActivity = new MainActivity();
        //TableLayout Jtable_Attr = (TableLayout) ((mainActivity)).findViewById(R.id.Jtable_Attr);
        for (int i = 0; i < 14; i++) {
            //if (Boolean.valueOf(Jtable_Attr.get(i, 1).toString())) {

            variables.add(i);
            // }
        }
        int sizepoblacion  = Integer.valueOf(poblacion);
        int iteracion = Integer.valueOf(iteracion1);
        float soporte = Float.valueOf(soporte1.replace(",", "."));
        Algoritmo.Iniciar(iteracion, sizepoblacion, variables, soporte,nosolucion,Atx_Campo);
    }
}