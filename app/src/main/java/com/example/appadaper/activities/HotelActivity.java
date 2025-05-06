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
import com.example.appadaper.Reservas.ReservaHotel;
import com.example.appadaper.adapters.itemAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HotelActivity extends AppCompatActivity implements itemAdapter.OnItemActionListener, Serializable {
    private static final long serialVersionUID = 1L;
    private RecyclerView recyclerView;
    private itemAdapter adapter;
    private List<Reserva> items;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

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
        // Azules profesionales (tonalidades más oscuras = mayor estatus)
       /* items.add(new ReservaHotel("101", "María González", "2023-11-15", "2023-11-20", 750, true, "#1565C0", "https://static1.eskypartners.com/travelguide/vancouver-hotels.jpg",700,"suite", true, 4));
        items.add(new ReservaHotel("205", "Carlos Mendoza", "2023-12-01", "2023-12-03", 300, false, "#42A5F5","https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2c/b0/c1/4c/boutique-hotels.jpg?w=1200&h=-1&s=1", 300,"individual", false, 1));
        items.add(new ReservaHotel("312", "Ana López", "2024-01-10", "2024-01-17", 1200, true, "#0D47A1","https://cf.bstatic.com/static/img/default_3x1/9263a587ad3e2400ddc6f7956a6000c4ab405c98.jpg", 500,"doble", true, 2));
        items.add(new ReservaHotel("418", "Roberto Sánchez", "2023-11-25", "2023-11-30", 1800, true, "#1E88E5","https://cf.bstatic.com/xdata/images/hotel/max1024x768/275338789.jpg?k=746ef8382ba908fc0ee92f38a6c67fe998c4295911060170723b8f7ac93277b7&o=&hp=1", 500,"familiar", false, 6));
        items.add(new ReservaHotel("503", "Laura Jiménez", "2023-11-10", "2023-11-12", 200, false, "#90CAF9", "https://www.xotels.com/wp-content/uploads/2022/07/lodge-room-xotels-hote-managemen-company.webp",500,"simple", false, 2));
    */
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
        intent.putExtra("reserva_hotel", (Serializable) item);
        startActivity(intent);
    }
    public void onDeleteButtonClick(Reserva item, int position) {
        adapter.removeItem(item.getCodigo()); // Necesitas implementar removeItem(String codigo)
        Toast.makeText(this, "Reserva #" + item.getCodigo() + " eliminada", Toast.LENGTH_SHORT).show();
    }
}