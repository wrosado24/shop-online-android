package com.example.tiendaonline;

import android.content.Context;
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
import com.example.tiendaonline.model.Compras;
import com.example.tiendaonline.model.Producto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ComprasFragment extends Fragment {

    private static final String URL = "http://192.168.18.6/tiendaVirtual/Controlador.php";
    List<Compras> compras;
    RecyclerView recyclerView;
    JsonObjectRequest jrq;
    JSONArray vector;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ComprasFragment() {
        // Required empty public constructor
    }

    public static ComprasFragment newInstance(String param1, String param2) {
        ComprasFragment fragment = new ComprasFragment();
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
        View root = inflater.inflate(R.layout.fragment_compras, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recy2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listarCompras();
        return root;
    }

    public void listarCompras(){
        String enlace = URL + "?tag=listarComprasPorCliente&clienteId="+obtenerIdUsuario();
        compras = new ArrayList<>();
        jrq = new JsonObjectRequest(Request.Method.GET, enlace, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    vector = response.getJSONArray("dato");
                    for(int f=0;f<vector.length();f++){
                        JSONObject fila = (JSONObject) vector.get(f);
                        Compras compra = new Compras();
                        compra.setSku(fila.getString("sku"));
                        compra.setNombre(fila.getString("nombre"));
                        compra.setMonto(fila.getDouble("monto"));
                        compra.setCantidad(fila.getInt("cantidad"));
                        compras.add(compra);
                    }

                    Adaptador2 dp = new Adaptador2(getContext(),compras);
                    recyclerView.setAdapter(dp);
                }catch (Exception e){
                    Log.w("error", e.getLocalizedMessage());
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
}