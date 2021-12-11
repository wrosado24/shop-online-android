package com.example.tiendaonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tiendaonline.model.Compras;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Graficos extends AppCompatActivity {

    BarChart barra1;
    int c1,c2,c3,c4;
    JsonObjectRequest jrq;

    JsonObjectRequest jobs;
    JSONArray vector;
    private static final String URL = "http://192.168.18.6/tiendaVirtual/Controlador.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos);

        barra1=findViewById(R.id.barra);
        inicializarGraficos();
    }

    public void inicializarGraficos(){
        c1=0; c2=0;c3=0;c4=0;
        String enlace = URL + "?tag=listarCompras";
        jrq = new JsonObjectRequest(Request.Method.GET, enlace, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    vector = response.getJSONArray("dato");
                    for(int f=0;f<vector.length();f++){
                        JSONObject fila = (JSONObject) vector.get(f);
                        int id = fila.getInt("id");
                        if(id == 19){
                            c1++;
                        }else if(id == 20){
                            c2++;
                        }else if(id == 21){
                            c3++;
                        }else if(id == 22){
                            c4++;
                        }
                    }
                    graficar();

                }catch (Exception e){
                    Log.w("error", e.getLocalizedMessage());
                    Toast.makeText(getApplication(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("Ha sucedido un error: ", error);
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_SHORT).show();
            }

        });

        //cola para la peticion
        RequestQueue cola = Volley.newRequestQueue(getApplication());
        cola.add(jrq);
    }

    void graficar(){
        List lista=new ArrayList();
        lista.add(new BarEntry(c1,0));
        lista.add(new BarEntry(c2,1));
        lista.add(new BarEntry(c3,2));
        lista.add(new BarEntry(c4,3));
        String vec[]={"MTP196","Xiaomi Mi Desktop","Kingston A400","Jetion PJT-DCM141"};
        BarDataSet ds=new BarDataSet(lista,"Productos mas vendidos");
        barra1.animateY(1500);
        BarData data=new BarData(vec,ds);
        ds.setColors(ColorTemplate.COLORFUL_COLORS);
        barra1.setData(data);
    }
}