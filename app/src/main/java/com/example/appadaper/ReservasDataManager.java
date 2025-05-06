package com.example.appadaper;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appadaper.Reservas.Reserva;
import com.example.appadaper.Reservas.ReservaCabana;
import com.example.appadaper.Reservas.ReservaGlamping;
import com.example.appadaper.Reservas.ReservaHotel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton para gestionar los datos de reservas durante toda la vida útil de la aplicación
 */
public class ReservasDataManager {
    private static ReservasDataManager instance;
    private List<Reserva> reservas;
    private boolean isDataLoaded = false;

    // URL de la API
    private static final String URL = "https://raw.githubusercontent.com/adancondori/TareaAPI/refs/heads/main/api/reservas.json";

    // Constructor privado para evitar instanciación externa
    private ReservasDataManager() {
        reservas = new ArrayList<>();
    }

    // Método para obtener la instancia única
    public static synchronized ReservasDataManager getInstance() {
        if (instance == null) {
            instance = new ReservasDataManager();
        }
        return instance;
    }

    // Comprueba si los datos ya están cargados
    public boolean isDataLoaded() {
        return isDataLoaded;
    }

    // Obtiene la lista de reservas
    public List<Reserva> getReservas() {
        return reservas;
    }
    public void cargarDatosDesdeApi(Context context, OnDataLoadedListener listener) {
        if (isDataLoaded) {
            listener.onDataLoaded(reservas);
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, URL, null,
                response -> {
                    reservas = parsearReservas(response);
                    isDataLoaded = true;
                    listener.onDataLoaded(reservas);
                },
                error -> {
                    Toast.makeText(context, "Error al cargar: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    listener.onError(error.getMessage());
                }
        );

        Volley.newRequestQueue(context).add(request);
    }

    public interface OnDataLoadedListener {
        void onDataLoaded(List<Reserva> reservas);
        void onError(String errorMessage);
    }
    public void actualizarReserva(Reserva reservaEditada) {
        if (reservaEditada == null || reservaEditada.getCodigo() == null) {
            return; // No se puede actualizar si la reserva o su código es null
        }

        boolean encontrada = false;
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getCodigo().equals(reservaEditada.getCodigo())) {
                reservas.set(i, reservaEditada);
                encontrada = true;
                break;
            }
        }

        // Si no se encontró la reserva, podría ser una nueva, así que la añadimos
        if (!encontrada) {
            reservas.add(reservaEditada);
        }
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
                JSONObject infoAdicional = obj.getJSONObject("informacionAdicional");

                int esperanzaVida = infoAdicional.getInt("esperanzaVida");
                JSONArray datosArray = infoAdicional.getJSONArray("datos");
                List<String> datos = new ArrayList<>();
                for (int j = 0; j < datosArray.length(); j++) {
                    JSONObject dato = datosArray.getJSONObject(j);
                    String nombre = dato.getString("nombreDato");
                    String valor = dato.getString("valor");
                    datos.add(nombre + ": " + valor);
                }

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
                                obj.getInt("numeroHuespedes"),
                                esperanzaVida,
                                datos
                        );
                        lista.add(hotel);
                        break;

                    case "ReservaCabana":
                        ReservaCabana cabana = new ReservaCabana(
                                codigo, cliente, fechaEntrada, fechaSalida, precioTotal, reservado,
                                color, urlImagen, price,
                                obj.getInt("metrosCuadrados"),
                                obj.getBoolean("tieneChimenea"),
                                obj.getInt("capacidadMaxima"),
                                esperanzaVida,
                                datos
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
                                "",
                                esperanzaVida,
                                datos
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
}