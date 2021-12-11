package com.example.tiendaonline;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tiendaonline.model.Logueo;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private static final String URL = "http://192.168.18.6/tiendaVirtual/Controlador.php";
    JsonObjectRequest jrq;
    JSONArray vector;
    EditText txtLoginCorreo, txtLoginContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLoginCorreo = findViewById(R.id.txtLoginCorreo);
        txtLoginContrasena = findViewById(R.id.txtLoginContrasena);
    }

    public void login(View view){
        String enlace = URL + "?tag=consultarUsuario&correo="+txtLoginCorreo.getText().toString()+"&contrasena="+txtLoginContrasena.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET, enlace, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String respuesta = response.getString("estado");

                    if(respuesta.equalsIgnoreCase("ok")){

                        vector = response.getJSONArray("dato");
                        Logueo logueo = new Logueo();
                        for(int f=0;f<vector.length();f++){
                            JSONObject fila = (JSONObject) vector.get(f);
                            logueo.setId(fila.getInt("id"));
                            logueo.setNombre(fila.getString("nombre"));
                            logueo.setNombre(fila.getString("apellido"));
                            logueo.setNombre(fila.getString("correo"));
                            logueo.setNombre(fila.getString("contrasena"));
                        }
                        guardarCredenciales(logueo.getId(), logueo.getNombre());
                        Toast.makeText(getApplication(), "Bienvenido " + logueo.getNombre() + " " + logueo.getApellido(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
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

    private void guardarCredenciales(int idUsuario, String nombreUsuario){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", idUsuario);
        editor.putString("nombre", nombreUsuario);
        editor.commit();
    }

    public void eliminarPreferencias(){
        SharedPreferences.Editor editor = getSharedPreferences("credenciales", Context.MODE_PRIVATE).edit();
        editor.clear().apply();
    }

    public void registrateAqui(View view){
        Intent intent = new Intent(getApplication(), Registro.class);
        getApplication().startActivity(intent);
    }
}