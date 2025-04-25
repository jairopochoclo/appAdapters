package com.example.appadaper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import java.io.Serializable;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appadaper.R;
import com.example.appadaper.Reservas.Reserva;
import com.example.appadaper.Reservas.ReservaCabana;
import com.example.appadaper.Reservas.ReservaHotel;
import com.example.appadaper.adapters.itemAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CabanaActivity extends AppCompatActivity implements itemAdapter.OnItemActionListener, Serializable {

    private RecyclerView recyclerView;
    private itemAdapter adapter;
    private List<Reserva> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabana);

        recyclerView = findViewById(R.id.recyclerView);

        // Inicializar la lista de items
        initItems();

        // Configurar el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new itemAdapter(items, this);
        recyclerView.setAdapter(adapter);
    }

    private void initItems() {
        items = new ArrayList<>();
        // Asegúrate que los parámetros coincidan con el constructor de Reserva
        items.add(new ReservaCabana("301", "Carlos Rojas", "2023-12-10", "2023-12-15", 1200, true, "#8D6E63", "https://i.pinimg.com/originals/df/ed/9a/dfed9a7b33fcb90d206b83249c3fd2ed.jpg",700,85, true, 6));
        items.add(new ReservaCabana("302", "María Fernández", "2024-01-05", "2024-01-10", 950, false, "#5D4037", "https://content.arquitecturaydiseno.es/medio/2023/02/17/cabana-bosque-madera-casa-rosalie_6220c323_600x600.jpg",500,60, false, 4));
        items.add(new ReservaCabana("303", "Elena Vargas", "2023-11-20", "2023-11-25", 1500, true, "#A1887F", "https://estag.fimagenes.com/imagenesred/fb_2589381_4163.jpg",500,100, true, 8));
        items.add(new ReservaCabana("304", "Jorge Silva", "2024-02-14", "2024-02-17", 700, true, "#6D4C41", "https://images.adsttc.com/media/images/6234/0105/3e4b/313b/6f00/00b8/newsletter/01aldo_lanzi.jpg?1647575296",500,45, false, 2));
        items.add(new ReservaCabana("305", "Lucía Méndez", "2023-12-22", "2023-12-30", 1800, false, "#3E2723", "https://i.pinimg.com/736x/10/1e/c2/101ec299685bce1886114c662e7429fc.jpg",500,75, true, 5));
    }

    @Override
    public void onItemClick(Reserva item, int position) {
        Toast.makeText(this, "Seleccionaste: " + item.getCodigo(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActionButtonClick(Reserva item, int position) {
        // Cambiamos el estado del item y notificamos al adaptador
        item.setReserve(!item.isReserve());
        adapter.notifyItemChanged(position);

        String mensaje = item.isReserve() ? "Reservado: " : "Desreservado: ";
        Toast.makeText(this, mensaje + item.getCodigo(), Toast.LENGTH_SHORT).show();
    }

    public void onInfoButtonClick(Reserva item) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("reserva_cabana", (Serializable) item);
        startActivity(intent);
    }
}