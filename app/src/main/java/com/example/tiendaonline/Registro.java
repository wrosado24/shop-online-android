package com.example.tiendaonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class Registro extends AppCompatActivity {

    private static final String URL = "http://192.168.18.6/tiendaVirtual/Controlador.php";
    JsonObjectRequest jrq;

    EditText txtRegNombre, txtRegApellido, txtCorreo, txtRegContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtRegNombre = findViewById(R.id.txtRegistroNombre);
        txtRegApellido = findViewById(R.id.txtRegistroApellido);
        txtCorreo = findViewById(R.id.txtRegistroCorreo);
        txtRegContra = findViewById(R.id.txtRegistroContrasena);

    }

    public void registro(View view){
        String enlace = URL + "?tag=registrarUsuario&nombre="+txtRegNombre.getText().toString()+"&apellido="+txtRegApellido.getText().toString()+"&correo="+txtCorreo.getText().toString()+"&contrasena="+txtRegContra.getText().toString();
        System.out.println("enlace: " + enlace);
        jrq = new JsonObjectRequest(Request.Method.GET, enlace, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String respuesta = response.getString("estado");

                    if(respuesta.equalsIgnoreCase("ok")){
                        Toast.makeText(getApplication(), "Se ha creado tu cuenta correctamente.", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(getApplication(), Login.class);
                        getApplication().startActivity(it);
                    }
                }catch (Exception e){
                    Log.w("error", e.getLocalizedMessage());
                    Toast.makeText(getApplication(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("error: ", error);
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_SHORT).show();
            }

        });

        //cola para la peticion
        RequestQueue cola = Volley.newRequestQueue(getApplication());
        cola.add(jrq);
    }

    public void volverLogin(View view){
        Intent it = new Intent(getApplication(), Login.class);
        getApplication().startActivity(it);
    }
}