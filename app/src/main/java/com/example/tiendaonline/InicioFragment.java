package com.example.tiendaonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tiendaonline.model.Producto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {

    private static final String URL = "http://192.168.18.6/tiendaVirtual/Controlador.php?tag=obtenerProductosPorCategoria&id=2";
    List<Producto> productos;
    RecyclerView recyclerView;
    JsonObjectRequest jrq;
    JSONArray vector;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public InicioFragment() {
        // Required empty public constructor
    }

    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recy1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listarProductos();
        return root;
    }

    void listarProductos(){
        String enlace = URL;
        productos = new ArrayList<>();
        jrq = new JsonObjectRequest(Request.Method.GET, enlace, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    vector = response.getJSONArray("dato");
                    for(int f=0;f<vector.length();f++){
                        JSONObject fila = (JSONObject) vector.get(f);
                        Producto producto = new Producto();
                        producto.setId(fila.getInt("id"));
                        producto.setSku(fila.getString("sku"));
                        producto.setNombre(fila.getString("nombre"));
                        producto.setMonto(fila.getDouble("monto"));
                        producto.setImagen(fila.getString("imagen"));
                        producto.setDescripcion(fila.getString("descripcion"));
                        productos.add(producto);
                    }

                    Adaptador1 dp = new Adaptador1(getContext(),productos);
                    recyclerView.setAdapter(dp);
                }catch (Exception e){
                    Log.w("mensaje", e.getLocalizedMessage());
                    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("Ha sucedido un error: ", error);
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }

        });

        //cola para la peticion
        RequestQueue cola = Volley.newRequestQueue(getContext());
        cola.add(jrq);
    }

    public int obtenerIdUsuario(){
        SharedPreferences preferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return preferences.getInt("id", 0);

    }

    public void eliminarPreferencias(){
        SharedPreferences.Editor editor = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE).edit();
        editor.clear().apply();
    }



}