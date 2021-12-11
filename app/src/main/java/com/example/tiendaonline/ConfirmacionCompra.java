package com.example.tiendaonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConfirmacionCompra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_compra);
    }

    public void regresar(View view){
        Intent it = new Intent(getApplication(), MainActivity.class);
        getApplication().startActivity(it);
    }
}