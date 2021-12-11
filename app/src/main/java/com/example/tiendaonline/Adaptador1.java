package com.example.tiendaonline;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tiendaonline.model.Producto;

import java.util.List;

public class Adaptador1 extends RecyclerView.Adapter<Adaptador1.myHolder>{

    private Context context;
    private List<Producto> productos;
    private View.OnClickListener listener;

    public Adaptador1(Context context, List<Producto> productos){
        this.context = context;
        this.productos = productos;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.productos, parent, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        Producto producto = productos.get(position);
        Glide.with(context)
                .load(producto.getImagen())
                .into(holder.imageView);

        holder.txtNombre.setText(producto.getNombre());
        holder.txtDescripcion.setText(producto.getDescripcion());
        holder.txtMonto.setText(String.valueOf(producto.getMonto()));

        holder.miLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, DetalleProducto.class);

                it.putExtra("idProducto", producto.getId());
                it.putExtra("nombreProducto", producto.getNombre());
                it.putExtra("descripcionProducto", producto.getDescripcion());
                it.putExtra("montoProducto", producto.getMonto());
                it.putExtra("imagenProducto", producto.getImagen());
                it.putExtra("nombreProducto", producto.getNombre());


                context.startActivity(it);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{

        TextView txtNombre, txtDescripcion, txtMonto;
        ImageView imageView;
        ConstraintLayout miLayout;

        public myHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView){
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombreProducto);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtMonto = itemView.findViewById(R.id.txtMonto);
            imageView = itemView.findViewById(R.id.imagenProducto);
            miLayout = itemView.findViewById(R.id.miLayout);

        }
    }
}
