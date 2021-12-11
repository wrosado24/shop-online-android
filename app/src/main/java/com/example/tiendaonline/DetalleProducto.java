package com.example.tiendaonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

public class DetalleProducto extends AppCompatActivity {

    ImageView imageView;
    EditText cantidad;
    private static final String URL = "http://192.168.18.6/tiendaVirtual/Controlador.php";
    JsonObjectRequest jrq;

    int iproductoId;
    String iNombre, idescripcion, iImagen;
    double iMonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iproductoId = getIntent().getExtras().getInt("idProducto");
        iNombre = getIntent().getExtras().getString("nombreProducto");
        idescripcion = getIntent().getExtras().getString("descripcionProducto");
        iImagen = getIntent().getExtras().getString("imagenProducto");
        iMonto = getIntent().getExtras().getDouble("montoProducto");
        setContentView(R.layout.activity_detalle_producto);

        cantidad = findViewById(R.id.txtCompraCantidadd);
        TextView tvNombre = this.findViewById(R.id.txtDpNombre);
        TextView tvDescripcion = this.findViewById(R.id.txtDpDescripcion);
        TextView tvMontoProducto = this.findViewById(R.id.txtCompraMontoProducto);

        tvNombre.setText(iNombre);
        tvDescripcion.setText(idescripcion);
        tvMontoProducto.setText("S/. " + String.valueOf(iMonto));

        imageView = findViewById(R.id.txtDpImagen);
        Glide.with(this)
                .load(iImagen)
                .into(imageView);

        int usuarioLogueado = obtenerIdUsuario();

        TextView tTextoLogueo = this.findViewById(R.id.txtAuth);

        if(usuarioLogueado == 0){
            tTextoLogueo.setText("Iniciar sesion");
        }else{
            tTextoLogueo.setText("Cerrar sesión");
            eliminarPreferencias();
        }

        tTextoLogueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplication(), Login.class);
                getApplication().startActivity(it);
            }
        });
    }

    public void comprarProducto(View view){
        int usuarioId = obtenerIdUsuario();
        if(usuarioId == 0){
            Intent it = new Intent(getApplication(), Login.class);
            getApplication().startActivity(it);
            Toast.makeText(getApplication(), "Accede a tu cuenta para realizar la compra", Toast.LENGTH_SHORT).show();
        }else{
            double monto_total = iMonto *(Integer.parseInt(cantidad.getText().toString()));
            String enlace = URL + "?tag=comprarProducto&productoId="+iproductoId+"&usuarioId="+usuarioId+"&cantidad="+Integer.parseInt(cantidad.getText().toString())+"&monto="+monto_total;
            System.out.println("enlace: " + enlace);
            jrq = new JsonObjectRequest(Request.Method.GET, enlace, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        String respuesta = response.getString("estado");
                        if(respuesta.equalsIgnoreCase("ok")){
                            Intent it = new Intent(getApplication(), ConfirmacionCompra.class);
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
    }

    public int obtenerIdUsuario(){
        SharedPreferences preferences = getApplication().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return preferences.getInt("id", 0);

    }

    public void eliminarPreferencias(){
        SharedPreferences.Editor editor = getApplication().getSharedPreferences("credenciales", Context.MODE_PRIVATE).edit();
        editor.clear().apply();
    }

}