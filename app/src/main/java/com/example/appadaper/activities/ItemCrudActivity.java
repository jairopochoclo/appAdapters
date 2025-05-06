package com.example.appadaper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appadaper.R;
import com.example.appadaper.Reservas.Reserva;
import com.example.appadaper.Reservas.ReservaCabana;
import com.example.appadaper.Reservas.ReservaGlamping;
import com.example.appadaper.Reservas.ReservaHotel;
import com.example.appadaper.ReservasDataManager;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ItemCrudActivity extends AppCompatActivity {
    public static final String EXTRA_MODO_EDICION = "modo_edicion";


    private MaterialAutoCompleteTextView actvTipoReserva;
    private LinearLayout llHotelFields;
    private LinearLayout llCabanaFields;
    private LinearLayout llGlampingFields;
    private Button btnLimpiar;
    private Button btnGuardar;

    // Campos comunes
    private TextInputEditText etCodigo;
    private TextInputEditText etCliente;
    private TextInputEditText etFechaEntrada;
    private TextInputEditText etFechaSalida;
    private TextInputEditText etPrecioTotal;
    private CheckBox cbConfirmarReserva;

    // Campos específicos de Hotel
    private MaterialAutoCompleteTextView actvTipoHabitacion;
    private CheckBox cbIncluyeDesayuno;
    private TextInputEditText etNumeroHuespedes;

    // Campos específicos de Cabaña
    private TextInputEditText etMetrosCuadrados;
    private CheckBox cbIncluyeChimenea;
    private TextInputEditText etCapacidadMaxima;

    // Campos específicos de Glamping
    private TextInputEditText etMetrosCuadradosGlamping;
    private CheckBox cbIncluyeChimeneaGlamping;
    private TextInputEditText etCapacidadMaximaGlamping;
    private TextInputEditText etTipoExperiencia;
    private TextInputEditText etActividadesIncluidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_crud);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        initViews();

        // Configurar el dropdown de tipo de reserva
        setupTipoReservaDropdown();

        // Configurar listeners de los botones
        setupButtons();

        boolean esEdicion = getIntent().getBooleanExtra(EXTRA_MODO_EDICION, false);

        if (esEdicion) {
            if (getIntent().hasExtra("reserva_hotel")) {
                ReservaHotel hotel = (ReservaHotel) getIntent().getSerializableExtra("reserva_hotel");
                cargarDatosHotel(hotel);  // Implementa este método
            } else if (getIntent().hasExtra("reserva_cabana")) {
                ReservaCabana cabana = (ReservaCabana) getIntent().getSerializableExtra("reserva_cabana");
                cargarDatosCabana(cabana);
            } else if (getIntent().hasExtra("reserva_glamping")) {
                ReservaGlamping glamping = (ReservaGlamping) getIntent().getSerializableExtra("reserva_glamping");
                cargarDatosGlamping(glamping);
            }
        }
        if (esEdicion) {
            btnGuardar.setText("Actualizar");
        }
    }

    private void initViews() {
        actvTipoReserva = findViewById(R.id.actvTipoReserva);
        llHotelFields = findViewById(R.id.llHotelFields);
        llCabanaFields = findViewById(R.id.llCabanaFields);
        llGlampingFields = findViewById(R.id.llGlampingFields);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Campos comunes
        etCodigo = findViewById(R.id.etCodigo);
        etCliente = findViewById(R.id.etCliente);
        etFechaEntrada = findViewById(R.id.etFechaEntrada);
        etFechaSalida = findViewById(R.id.etFechaSalida);
        etPrecioTotal = findViewById(R.id.etPrecioTotal);
        cbConfirmarReserva = findViewById(R.id.cbConfirmarReserva);

        // Campos de Hotel
        actvTipoHabitacion = findViewById(R.id.actvTipoHabitacion);
        cbIncluyeDesayuno = findViewById(R.id.cbIncluyeDesayuno);
        etNumeroHuespedes = findViewById(R.id.etNumeroHuespedes);

        // Campos de Cabaña
        etMetrosCuadrados = findViewById(R.id.etMetrosCuadrados);
        cbIncluyeChimenea = findViewById(R.id.cbIncluyeChimenea);
        etCapacidadMaxima = findViewById(R.id.etCapacidadMaxima);

        // Campos de Glamping
        etMetrosCuadradosGlamping = findViewById(R.id.etMetrosCuadradosGlamping);
        cbIncluyeChimeneaGlamping = findViewById(R.id.cbIncluyeChimeneaGlamping);
        etCapacidadMaximaGlamping = findViewById(R.id.etCapacidadMaximaGlamping);
        etTipoExperiencia = findViewById(R.id.etTipoExperiencia);
        etActividadesIncluidas = findViewById(R.id.etActividadesIncluidas);
    }

    private void setupTipoReservaDropdown() {
        // Opciones para el dropdown
        String[] tiposReserva = {"Hotel", "Cabaña", "Glamping"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                tiposReserva
        );

        actvTipoReserva.setAdapter(adapter);

        // Configurar dropdown para tipo de habitación (Hotel)
        String[] tiposHabitacion = {"Individual", "Doble", "Suite", "Familiar"};
        ArrayAdapter<String> habitacionAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                tiposHabitacion
        );
        actvTipoHabitacion.setAdapter(habitacionAdapter);

        // Listener para cambiar los campos visibles según la selección
        actvTipoReserva.setOnItemClickListener((parent, view, position, id) -> {
            String selected = tiposReserva[position];
            mostrarCamposEspecificos(selected);
        });
    }

    private void setupButtons() {
        btnLimpiar.setOnClickListener(v -> limpiarFormulario());
        btnGuardar.setOnClickListener(v -> guardarReserva());
    }

    private void limpiarFormulario() {
        // Limpiar campos comunes
        etCodigo.setText("");
        etCliente.setText("");
        etFechaEntrada.setText("");
        etFechaSalida.setText("");
        etPrecioTotal.setText("");
        cbConfirmarReserva.setChecked(false);

        // Limpiar campos de Hotel
        actvTipoHabitacion.setText("");
        cbIncluyeDesayuno.setChecked(false);
        etNumeroHuespedes.setText("");

        // Limpiar campos de Cabaña
        etMetrosCuadrados.setText("");
        cbIncluyeChimenea.setChecked(false);
        etCapacidadMaxima.setText("");

        // Limpiar campos de Glamping
        etMetrosCuadradosGlamping.setText("");
        cbIncluyeChimeneaGlamping.setChecked(false);
        etCapacidadMaximaGlamping.setText("");
        etTipoExperiencia.setText("");
        etActividadesIncluidas.setText("");

        // Resetear tipo de reserva
        actvTipoReserva.setText("");

        // Ocultar todos los campos específicos
        llHotelFields.setVisibility(View.GONE);
        llCabanaFields.setVisibility(View.GONE);
        llGlampingFields.setVisibility(View.GONE);

        Toast.makeText(this, "Formulario limpiado", Toast.LENGTH_SHORT).show();
    }

    private void guardarReserva() {
        // Validar campos obligatorios
        if (!validarCampos()) {
            return;
        }

        // Obtener valores comunes
        String codigo = etCodigo.getText().toString();
        String cliente = etCliente.getText().toString();
        String fechaEntrada = etFechaEntrada.getText().toString();
        String fechaSalida = etFechaSalida.getText().toString();
        int precioTotal = Integer.parseInt(etPrecioTotal.getText().toString());
        boolean confirmada = cbConfirmarReserva.isChecked();

        // Obtener tipo de reserva
        String tipoReserva = actvTipoReserva.getText().toString();

        // Crear la reserva según el tipo
        Reserva nuevaReserva = null;
        List<String> datosAdicionales = new ArrayList<>();

        try {
            switch(tipoReserva) {
                case "Hotel":
                    String tipoHabitacion = actvTipoHabitacion.getText().toString();
                    boolean incluyeDesayuno = cbIncluyeDesayuno.isChecked();
                    int numeroHuespedes = Integer.parseInt(etNumeroHuespedes.getText().toString());

                    nuevaReserva = new ReservaHotel(
                            codigo, cliente, fechaEntrada, fechaSalida, precioTotal, confirmada,
                            "#FFFFFF", "", 0,
                            tipoHabitacion, incluyeDesayuno, numeroHuespedes,
                            0, datosAdicionales
                    );
                    break;

                case "Cabaña":
                    int metrosCuadrados = Integer.parseInt(etMetrosCuadrados.getText().toString());
                    boolean incluyeChimenea = cbIncluyeChimenea.isChecked();
                    int capacidadMaxima = Integer.parseInt(etCapacidadMaxima.getText().toString());

                    nuevaReserva = new ReservaCabana(
                            codigo, cliente, fechaEntrada, fechaSalida, precioTotal, confirmada,
                            "#FFFFFF", "", 0,
                            metrosCuadrados, incluyeChimenea, capacidadMaxima,
                            0, datosAdicionales
                    );
                    break;

                case "Glamping":
                    int metrosCuadradosGlamping = Integer.parseInt(etMetrosCuadradosGlamping.getText().toString());
                    boolean incluyeChimeneaGlamping = cbIncluyeChimeneaGlamping.isChecked();
                    int capacidadMaximaGlamping = Integer.parseInt(etCapacidadMaximaGlamping.getText().toString());
                    String tipoExperiencia = etTipoExperiencia.getText().toString();
                    String actividadesIncluidas = etActividadesIncluidas.getText().toString();

                    nuevaReserva = new ReservaGlamping(
                            codigo, cliente, fechaEntrada, fechaSalida, precioTotal, confirmada,
                            "#FFFFFF", "", 0,
                            metrosCuadradosGlamping, incluyeChimeneaGlamping, capacidadMaximaGlamping,
                            tipoExperiencia, actividadesIncluidas, "",
                            0, datosAdicionales
                    );
                    break;
            }

            // Verificar si estamos en modo edición
            boolean esEdicion = getIntent().getBooleanExtra(EXTRA_MODO_EDICION, false);

            if (esEdicion) {
                // Actualizamos la reserva en el DataManager
                ReservasDataManager.getInstance().actualizarReserva(nuevaReserva);

                // Añadimos esto para devolver el resultado a la actividad anterior
                Intent resultIntent = new Intent();
                resultIntent.putExtra("reserva_actualizada", nuevaReserva);
                setResult(RESULT_OK, resultIntent);

                Toast.makeText(this, "Reserva actualizada exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                // Devolver la nueva reserva a la actividad anterior
                Intent resultIntent = new Intent();
                resultIntent.putExtra("nueva_reserva", nuevaReserva);
                setResult(RESULT_OK, resultIntent);
                Toast.makeText(this, "Reserva guardada exitosamente", Toast.LENGTH_SHORT).show();
            }

            // Finaliza la actividad y regresa a la actividad anterior
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error en los campos numéricos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar la reserva", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        // Validar campos comunes
        if (actvTipoReserva.getText().toString().isEmpty()) {
            Toast.makeText(this, "Seleccione el tipo de reserva", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etCodigo.getText().toString().isEmpty()) {
            etCodigo.setError("Ingrese el código");
            return false;
        }

        if (etCliente.getText().toString().isEmpty()) {
            etCliente.setError("Ingrese el nombre del cliente");
            return false;
        }

        if (etFechaEntrada.getText().toString().isEmpty()) {
            etFechaEntrada.setError("Ingrese la fecha de entrada");
            return false;
        }

        if (etFechaSalida.getText().toString().isEmpty()) {
            etFechaSalida.setError("Ingrese la fecha de salida");
            return false;
        }

        if (etPrecioTotal.getText().toString().isEmpty()) {
            etPrecioTotal.setError("Ingrese el precio total");
            return false;
        }

        // Validar campos específicos según el tipo de reserva
        String tipoReserva = actvTipoReserva.getText().toString();

        switch(tipoReserva) {
            case "Hotel":
                if (actvTipoHabitacion.getText().toString().isEmpty()) {
                    actvTipoHabitacion.setError("Seleccione el tipo de habitación");
                    return false;
                }

                if (etNumeroHuespedes.getText().toString().isEmpty()) {
                    etNumeroHuespedes.setError("Ingrese el número de huéspedes");
                    return false;
                }
                break;

            case "Cabaña":
                if (etMetrosCuadrados.getText().toString().isEmpty()) {
                    etMetrosCuadrados.setError("Ingrese los metros cuadrados");
                    return false;
                }

                if (etCapacidadMaxima.getText().toString().isEmpty()) {
                    etCapacidadMaxima.setError("Ingrese la capacidad máxima");
                    return false;
                }
                break;

            case "Glamping":
                if (etMetrosCuadradosGlamping.getText().toString().isEmpty()) {
                    etMetrosCuadradosGlamping.setError("Ingrese los metros cuadrados");
                    return false;
                }

                if (etCapacidadMaximaGlamping.getText().toString().isEmpty()) {
                    etCapacidadMaximaGlamping.setError("Ingrese la capacidad máxima");
                    return false;
                }

                if (etTipoExperiencia.getText().toString().isEmpty()) {
                    etTipoExperiencia.setError("Ingrese el tipo de experiencia");
                    return false;
                }

                if (etActividadesIncluidas.getText().toString().isEmpty()) {
                    etActividadesIncluidas.setError("Ingrese las actividades incluidas");
                    return false;
                }
                break;
        }

        return true;
    }
    private void mostrarCamposEspecificos(String tipoReserva) {
        // Ocultar todos primero
        llHotelFields.setVisibility(View.GONE);
        llCabanaFields.setVisibility(View.GONE);
        llGlampingFields.setVisibility(View.GONE);

        // Mostrar solo los correspondientes
        switch(tipoReserva) {
            case "Hotel":
                llHotelFields.setVisibility(View.VISIBLE);
                break;
            case "Cabaña":
                llCabanaFields.setVisibility(View.VISIBLE);
                break;
            case "Glamping":
                llGlampingFields.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void cargarDatosHotel(ReservaHotel hotel) {
        actvTipoReserva.setText("Hotel");
        mostrarCamposEspecificos("Hotel");

        // Campos comunes
        etCodigo.setText(hotel.getCodigo());
        etCliente.setText(hotel.getCliente());
        etFechaEntrada.setText(hotel.getFechaEntrada());
        etFechaSalida.setText(hotel.getFechaSalida());
        etPrecioTotal.setText(String.valueOf(hotel.getPrecioTotal()));
        cbConfirmarReserva.setChecked(hotel.isReserve());

        // Campos específicos de Hotel
        actvTipoHabitacion.setText(hotel.getTipoHabitacion());
        cbIncluyeDesayuno.setChecked(hotel.getIncluyeDesayuno());
        etNumeroHuespedes.setText(String.valueOf(hotel.getNumeroHuespedes()));
    }

    private void cargarDatosCabana(ReservaCabana cabana) {
        actvTipoReserva.setText("Cabaña");
        mostrarCamposEspecificos("Cabaña");

        // Campos comunes
        etCodigo.setText(cabana.getCodigo());
        etCliente.setText(cabana.getCliente());
        etFechaEntrada.setText(cabana.getFechaEntrada());
        etFechaSalida.setText(cabana.getFechaSalida());
        etPrecioTotal.setText(String.valueOf(cabana.getPrecioTotal()));
        cbConfirmarReserva.setChecked(cabana.isReserve());

        // Campos específicos de Cabaña
        etMetrosCuadrados.setText(String.valueOf(cabana.getMetrosCuadrados()));
        cbIncluyeChimenea.setChecked(cabana.getIncluyeChimenea());
        etCapacidadMaxima.setText(String.valueOf(cabana.getCapacidadMaxima()));
    }

    private void cargarDatosGlamping(ReservaGlamping glamping) {
        actvTipoReserva.setText("Glamping");
        mostrarCamposEspecificos("Glamping");

        // Campos comunes
        etCodigo.setText(glamping.getCodigo());
        etCliente.setText(glamping.getCliente());
        etFechaEntrada.setText(glamping.getFechaEntrada());
        etFechaSalida.setText(glamping.getFechaSalida());
        etPrecioTotal.setText(String.valueOf(glamping.getPrecioTotal()));
        cbConfirmarReserva.setChecked(glamping.isReserve());

        // Campos heredados de Cabaña
        etMetrosCuadradosGlamping.setText(String.valueOf(glamping.getMetrosCuadrados()));
        cbIncluyeChimeneaGlamping.setChecked(glamping.getIncluyeChimenea());
        etCapacidadMaximaGlamping.setText(String.valueOf(glamping.getCapacidadMaxima()));

        // Campos específicos de Glamping
        etTipoExperiencia.setText(glamping.getTipoExperiencia());
        etActividadesIncluidas.setText(glamping.getActividadesIncluidas());
    }

}