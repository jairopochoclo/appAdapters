package com.example.appadaper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appadaper.R;
import com.example.appadaper.ReservasDataManager;
import com.example.appadaper.Reservas.*;
import com.example.appadaper.adapters.itemAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class ReservaApiActivity extends AppCompatActivity implements itemAdapter.OnItemActionListener {
    private RecyclerView recyclerView;
    private itemAdapter adapter;
    private FloatingActionButton fabAdd;

    private static final int REQUEST_NUEVA_RESERVA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_api);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFormularioReserva();
            }
        });

        cargarReservas();
    }
    private void abrirFormularioReserva() {
        Intent intent = new Intent(this, ItemCrudActivity.class);
        startActivityForResult(intent, REQUEST_NUEVA_RESERVA);
    }

    private void cargarReservas() {
        ReservasDataManager.getInstance().cargarDatosDesdeApi(this, new ReservasDataManager.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List<Reserva> reservas) {
                // Crear el adaptador con la lista más reciente de reservas del singleton
                adapter = new itemAdapter(ReservasDataManager.getInstance().getReservas(), ReservaApiActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(ReservaApiActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_NUEVA_RESERVA && resultCode == RESULT_OK && data != null) {
            if (data.hasExtra("nueva_reserva")) {
                Reserva nuevaReserva = (Reserva) data.getSerializableExtra("nueva_reserva");
                if (nuevaReserva != null) {
                    // Añadirla al adapter
                    adapter.addItem(nuevaReserva);
                    // Notificar al adaptador que los datos pueden haber cambiado
                    adapter.notifyDataSetChanged();
                }
            } else if (data.hasExtra("reserva_actualizada")) {
                // Si es una actualización, recargar toda la lista
                adapter.setItems(ReservasDataManager.getInstance().getReservas());
                adapter.notifyDataSetChanged();
            }
        } else {
            // Siempre recargar la lista al volver a esta actividad,
            // para reflejar cualquier cambio hecho en otras pantallas
            adapter.setItems(ReservasDataManager.getInstance().getReservas());
            adapter.notifyDataSetChanged();
        }
    }
    public void onItemClick(Reserva item, int position) {
        Toast.makeText(this, "Seleccionaste: " + item.getCodigo(), Toast.LENGTH_SHORT).show();
    }

    public void onActionButtonClick(Reserva item, int position) {
        item.setReserve(!item.isReserve());
        adapter.notifyItemChanged(position);
    }

    public void onInfoButtonClick(Reserva item) {
        Intent intent = new Intent(this, InfoActivity.class);

        if (item instanceof ReservaHotel) {
            intent.putExtra("reserva_hotel", (Serializable) item);
        } else if (item instanceof ReservaGlamping) {
            intent.putExtra("reserva_glamping", (Serializable) item);
        } else if (item instanceof ReservaCabana) {
            intent.putExtra("reserva_cabana", (Serializable) item);
        }
        startActivity(intent);
    }
    public void onDeleteButtonClick(Reserva item, int position) {
        // Crea un AlertDialog de confirmación
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que quieres eliminar la reserva #" + item.getCodigo() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    // Si el usuario confirma, elimina el ítem
                    adapter.removeItem(item.getCodigo());
                    Toast.makeText(this, "Reserva #" + item.getCodigo() + " eliminada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    // Si cancela, no hagas nada (cierra el diálogo automáticamente)
                    Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
                })
                .setIcon(android.R.drawable.ic_dialog_alert) // Icono de advertencia
                .show();
    }
}