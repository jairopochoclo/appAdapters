package com.example.appadaper.activities;

import android.os.Bundle;
import android.widget.TextView;
import java.io.Serializable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appadaper.R;
import com.example.appadaper.Reservas.Reserva;
import com.example.appadaper.Reservas.ReservaCabana;
import com.example.appadaper.Reservas.ReservaGlamping;
import com.example.appadaper.Reservas.ReservaHotel;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView txtInfo = findViewById(R.id.txtInfo);

        // Verifica si es ReservaHotel
        if (getIntent().hasExtra("reserva_hotel")) {
            ReservaHotel hotel = (ReservaHotel) getIntent().getSerializableExtra("reserva_hotel");
            mostrarInfoHotel(hotel, txtInfo);
        }
        // Verifica si es ReservaCabana
        else if (getIntent().hasExtra("reserva_cabana")) {
            ReservaCabana cabana = (ReservaCabana) getIntent().getSerializableExtra("reserva_cabana");
            mostrarInfoCabana(cabana, txtInfo);
        }
        else if (getIntent().hasExtra("reserva_glamping")) {
            ReservaGlamping glamping = (ReservaGlamping) getIntent().getSerializableExtra("reserva_glamping");
            mostrarInfoGlamping(glamping, txtInfo);
        }
    }

    private void mostrarInfoHotel(ReservaHotel hotel, TextView txtInfo) {
        String info = "TIPO: HOTEL\n\n" +
                "📋 Código: " + hotel.getCodigo() + "\n\n" +
                "👤 Cliente: " + hotel.getCliente() + "\n\n" +
                "📅 Fechas:\n" +
                "       - Entrada: " + hotel.getFechaEntrada() + "\n" +
                "       - Salida: " + hotel.getFechaSalida() + "\n\n" +
                "💰 Precio total: $" + hotel.getPrecioTotal() + "\n\n" +
                "🛌 Habitación: " + hotel.getTipoHabitacion() + "\n\n" +
                "👥 Huéspedes: " + hotel.getNumeroHuespedes() + "\n\n" +
                "🍳 Desayuno: " + (hotel.getIncluyeDesayuno() ? "Sí" : "No") + "\n\n" +
                "📌 Estado: " + (hotel.isReserve() ? "Reservado" : "Disponible");

        txtInfo.setText(info);
    }

    private void mostrarInfoCabana(ReservaCabana cabana, TextView txtInfo) {
        String info = "TIPO: CABAÑA\n\n" +
                "📋 Código: " + cabana.getCodigo() + "\n\n" +
                "👤 Cliente: " + cabana.getCliente() + "\n\n" +
                "📅 Fechas:\n" +
                "       - Entrada: " + cabana.getFechaEntrada() + "\n" +
                "       - Salida: " + cabana.getFechaSalida() + "\n\n" +
                "💰 Precio total: $" + cabana.getPrecioTotal() + "\n\n" +
                "📏 Metros cuadrados: " + cabana.getMetrosCuadrados() + " m²\n\n" +
                "👥 Capacidad máxima: " + cabana.getCapacidadMaxima() + " personas\n\n" +
                "🔥 Chimenea: " + (cabana.getIncluyeChimenea() ? "Sí" : "No") + "\n\n" +
                "📌 Estado: " + (cabana.isReserve() ? "Reservado" : "Disponible");

        txtInfo.setText(info);
    }

    private void mostrarInfoGlamping(ReservaGlamping glamping, TextView txtInfo) {
        String info = "TIPO: GLAMPING\n\n" +
                "📋 Código: " + glamping.getCodigo() + "\n\n" +
                "👤 Cliente: " + glamping.getCliente() + "\n\n" +
                "📅 Fechas:\n" +
                "       - Entrada: " + glamping.getFechaEntrada() + "\n" +
                "       - Salida: " + glamping.getFechaSalida() + "\n\n" +
                "💰 Precio total: $" + glamping.getPrecioTotal() + "\n\n" +
                "📏 Espacio: " + glamping.getMetrosCuadrados() + " m²\n\n" +
                "👥 Capacidad: " + glamping.getCapacidadMaxima() + " personas\n\n" +
                "🔥 Chimenea: " + (glamping.getIncluyeChimenea() ? "Sí" : "No") + "\n\n" +
                "🌿 Experiencia: " + glamping.getTipoExperiencia() + "\n\n" +
                "🎯 Actividades incluidas:\n" + glamping.getActividadesIncluidas() + "\n\n" +
                "📌 Estado: " + (glamping.isReserve() ? "Reservado" : "Disponible");

        txtInfo.setText(info);
    }
}
