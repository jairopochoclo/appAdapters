package com.example.appadaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appadaper.activities.CabanaActivity;
import com.example.appadaper.activities.GlampingActivity;
import com.example.appadaper.activities.HotelActivity;
import com.example.appadaper.adapters.MenuAdapter;
import com.example.appadaper.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class AdaptersActivity extends AppCompatActivity {

    private GridView gridViewMenu;
    private MenuAdapter menuAdapter;
    private List<MenuItem> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adapters);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar GridView
        gridViewMenu = findViewById(R.id.gridViewMenu);

        // Crear elementos del menú
        initMenuItems();

        // Configurar adaptador
        menuAdapter = new MenuAdapter(this, menuItems);
        gridViewMenu.setAdapter(menuAdapter);

        // Configurar evento de clic
        gridViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Manejar la selección del menú
                handleMenuSelection(position);
            }
        });
    }

    private void initMenuItems() {
        menuItems = new ArrayList<>();

        // Agregar opciones del menú
        menuItems.add(new MenuItem("Reservar Hotel", android.R.drawable.ic_menu_sort_alphabetically)); // Orden = Hotel profesional
        menuItems.add(new MenuItem("Reservar Cabana", android.R.drawable.ic_menu_mylocation)); // Ubicación en naturaleza
        menuItems.add(new MenuItem("Reservar Glamping", android.R.drawable.ic_menu_week)); // Vacaciones/tiempo libre
        // Reservamos espacio para opciones futuras
        // menuItems.add(new MenuItem("Opción Futura 1", android.R.drawable.ic_menu_compass));

    }

    private void handleMenuSelection(int position) {
        Intent intent = null;
        switch (position) {
            case 0: // Ejemplo Hotel
                intent = new Intent(this, HotelActivity.class);
                break;
            case 1: // Ejemplo Cabana
                intent = new Intent(this, CabanaActivity.class);
                break;
            case 2: // Ejemplo Glamping
                intent = new Intent(this, GlampingActivity.class);
                break;
            default:
                Toast.makeText(this, "Opción no implementada aún", Toast.LENGTH_SHORT).show();
                return;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}