package com.example.appadaper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
import com.example.appadaper.Reservas.ReservaGlamping;
import com.example.appadaper.adapters.itemAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GlampingActivity extends AppCompatActivity implements itemAdapter.OnItemActionListener, Serializable {

    private RecyclerView recyclerView;
    private itemAdapter adapter;
    private List<Reserva> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glamping);

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
        // Colores en gama de verdes naturales (#Código - Descripción)
        items.add(new ReservaGlamping("101", "María González", "2024-06-15", "2024-06-20", 1200, false, "#2E7D32",
                "https://media.admagazine.com/photos/6239341e83e0740e83d095a3/16:9/w_5488,h_3087,c_limit/glamping.jpg", 20000, 50, true, 4, "Lujo en la naturaleza", "Senderismo, Observación de estrellas",
                "https://media.admagazine.com/photos/6239341e83e0740e83d095a3/16:9/w_5488,h_3087,c_limit/glamping.jpg"));

        items.add(new ReservaGlamping("202", "Carlos Mendoza", "2024-07-01", "2024-07-05", 1800, true, "#388E3C",
                "https://www.campingelpino.com/wp-content/uploads/2023/11/que-es-el-glamping-caracteristicas-scaled.jpg", 700, 65, true, 6, "Aventura familiar", "Talleres naturales, Paseos a caballo",
                "https://www.campingelpino.com/wp-content/uploads/2023/11/que-es-el-glamping-caracteristicas-scaled.jpg"));

        items.add(new ReservaGlamping("303", "Ana López", "2024-08-10", "2024-08-15", 1500, false, "#689F38",
                "https://visitarmedellin.com/wp-content/uploads/2023/06/bubble-sky-glamping.jpg", 1000, 45, false, 2, "Romántico", "Cena bajo las estrellas, Masajes",
                "https://visitarmedellin.com/wp-content/uploads/2023/06/bubble-sky-glamping.jpg"));

        items.add(new ReservaGlamping("404", "Roberto Sánchez", "2024-09-12", "2024-09-15", 950, true, "#7CB342",
                "https://blog.winesofargentina.com/wp-content/uploads/2021/11/Glamping-APERTURA.jpg", 500, 35, false, 3, "Económico", "Yoga matutino, Fogata nocturna",
                "https://blog.winesofargentina.com/wp-content/uploads/2021/11/Glamping-APERTURA.jpg"));

        items.add(new ReservaGlamping("505", "Laura Jiménez", "2024-10-20", "2024-10-25", 2100, false, "#33691E",
                "https://theindianface.com/cdn/shop/articles/Sin-titulo-1_11661747-7518-44c0-b923-111d1cdc5f78.jpg?v=1652867285", 1500, 80, true, 5, "Premium", "Tour gastronómico, Spa natural, Clases de fotografía",
                "https://theindianface.com/cdn/shop/articles/Sin-titulo-1_11661747-7518-44c0-b923-111d1cdc5f78.jpg?v=1652867285"));
    }

    public void onItemClick(Reserva item, int position) {
        Toast.makeText(this, "Seleccionaste: " + item.getCodigo(), Toast.LENGTH_SHORT).show();
    }


    public void onActionButtonClick(Reserva item, int position) {
        // Cambiamos el estado del item y notificamos al adaptador
        item.setReserve(!item.isReserve());
        adapter.notifyItemChanged(position);

        String mensaje = item.isReserve() ? "Reservado: " : "Desreservado: ";
        Toast.makeText(this, mensaje + item.getCodigo(), Toast.LENGTH_SHORT).show();
    }

    public void onInfoButtonClick(Reserva item) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("reserva_glamping", (Serializable) item);
        startActivity(intent);
    }
}