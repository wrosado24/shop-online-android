package com.example.tiendaonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetalleProducto extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String iNombre = getIntent().getExtras().getString("nombreProducto");
        String idescripcion = getIntent().getExtras().getString("descripcionProducto");
        String iImagen = getIntent().getExtras().getString("imagenProducto");

        setContentView(R.layout.activity_detalle_producto);

        TextView tvNombre = this.findViewById(R.id.txtDpNombre);
        TextView tvDescripcion = this.findViewById(R.id.txtDpDescripcion);

        tvNombre.setText(iNombre);
        tvDescripcion.setText(idescripcion);

        imageView = findViewById(R.id.txtDpImagen);
        Glide.with(this)
                .load(iImagen)
                .into(imageView);
    }
}