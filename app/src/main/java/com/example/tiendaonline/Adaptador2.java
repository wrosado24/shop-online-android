package com.example.tiendaonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendaonline.model.Compras;

import java.util.List;

public class Adaptador2 extends RecyclerView.Adapter<Adaptador2.myHolder>{

    private Context context;
    private List<Compras> compras;

    public Adaptador2(Context context, List<Compras> compras){
        this.context = context;
        this.compras = compras;
    }

    @NonNull
    @Override
    public Adaptador2.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.compras, parent, false);
        return new Adaptador2.myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador2.myHolder holder, int position) {
        Compras compra = compras.get(position);

        holder.txtNombre.setText("Nombre: " + compra.getNombre());
        holder.txtSku.setText("SKU: " + compra.getSku());
        holder.txtMonto.setText("Monto total: " + String.valueOf(compra.getMonto()));
        holder.txtCantidad.setText("Cantidad: " + String.valueOf(compra.getCantidad()));
    }

    @Override
    public int getItemCount() {
        return compras.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{

        TextView txtNombre, txtSku, txtMonto, txtCantidad;
        ConstraintLayout miLayout;

        public myHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView){
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtComprasNombre);
            txtSku = itemView.findViewById(R.id.txtComprasSku);
            txtCantidad = itemView.findViewById(R.id.txtCompraCantidad);
            txtMonto = itemView.findViewById(R.id.txtCompraMonto);
            miLayout = itemView.findViewById(R.id.miLayout2);

        }
    }


}
