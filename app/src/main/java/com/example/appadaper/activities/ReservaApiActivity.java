package com.example.appadaper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appadaper.R;
import com.example.appadaper.Reservas.*;
import com.example.appadaper.adapters.itemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReservaApiActivity extends AppCompatActivity implements itemAdapter.OnItemActionListener {
    private RecyclerView recyclerView;
    private itemAdapter adapter;
    private List<Reserva> items = new ArrayList<>();
    private static final String URL = "https://raw.githubusercontent.com/adancondori/TareaAPI/refs/heads/main/api/reservas.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarReservasDesdeApi();
    }

    private void cargarReservasDesdeApi() {
        com.android.volley.toolbox.JsonObjectRequest request = new com.android.volley.toolbox.JsonObjectRequest(
                Request.Method.GET, URL, null,
                response -> {
                    items = parsearReservas(response); // Ahora sí es JSONObject
                    adapter = new itemAdapter(items, this);
                    recyclerView.setAdapter(adapter);
                },
                error -> Toast.makeText(this, "Error al cargar: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }

    private List<Reserva> parsearReservas(JSONObject response) {
        List<Reserva> lista = new ArrayList<>();
        try {
            JSONArray jsonArray = response.getJSONArray("reservas");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String tipo = obj.getString("tipo");
                String codigo = obj.getString("codigo");
                String cliente = obj.getString("cliente");
                String fechaEntrada = obj.getString("fechaEntrada");
                String fechaSalida = obj.getString("fechaSalida");
                int precioTotal = obj.getInt("precioTotal");

                // Valores por defecto
                boolean reservado = false;
                String color = "#FFFFFF";
                String urlImagen = "";
                int price = 0;

                switch (tipo) {
                    case "ReservaHotel":
                        ReservaHotel hotel = new ReservaHotel(
                                codigo, cliente, fechaEntrada, fechaSalida, precioTotal, reservado,
                                color, urlImagen, price,
                                obj.getString("tipoHabitacion"),
                                obj.getBoolean("incluyeDesayuno"),
                                obj.getInt("numeroHuespedes")
                        );
                        lista.add(hotel);
                        break;

                    case "ReservaCabana":
                        ReservaCabana cabana = new ReservaCabana(
                                codigo, cliente, fechaEntrada, fechaSalida, precioTotal, reservado,
                                color, urlImagen, price,
                                obj.getInt("metrosCuadrados"),
                                obj.getBoolean("tieneChimenea"),
                                obj.getInt("capacidadMaxima")
                        );
                        lista.add(cabana);
                        break;

                    case "ReservaGlamping":
                        JSONArray actividadesArray = obj.getJSONArray("actividadesIncluidas");
                        StringBuilder actividades = new StringBuilder();
                        for (int j = 0; j < actividadesArray.length(); j++) {
                            actividades.append(actividadesArray.getString(j));
                            if (j < actividadesArray.length() - 1) actividades.append(", ");
                        }

                        ReservaGlamping glamping = new ReservaGlamping(
                                codigo, cliente, fechaEntrada, fechaSalida, precioTotal, reservado,
                                color, urlImagen, price,
                                obj.getInt("metrosCuadrados"),
                                obj.getBoolean("tieneChimenea"),
                                obj.getInt("capacidadMaxima"),
                                obj.getString("tipoExperiencia"),
                                actividades.toString(),
                                ""
                        );
                        lista.add(glamping);
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }


    @Override
    public void onItemClick(Reserva item, int position) {
        Toast.makeText(this, "Seleccionaste: " + item.getCodigo(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActionButtonClick(Reserva item, int position) {
        item.setReserve(!item.isReserve());
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onInfoButtonClick(Reserva item) {
        Intent intent = new Intent(this, InfoActivity.class);

        if (item instanceof ReservaHotel) {
            intent.putExtra("reserva_hotel", (Serializable) item);
        } else if (item instanceof ReservaCabana) {
            intent.putExtra("reserva_cabana", (Serializable) item);
        } else if (item instanceof ReservaGlamping) {
            intent.putExtra("reserva_glamping", (Serializable) item);
        }
        startActivity(intent);
    }
}
