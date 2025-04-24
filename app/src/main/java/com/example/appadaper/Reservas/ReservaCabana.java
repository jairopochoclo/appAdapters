package com.example.appadaper.Reservas;

public class ReservaCabana extends Reserva{
    private int metrosCuadrados;
    private Boolean incluyeChimenea;
    private int capacidadMaxima;

    public ReservaCabana(int codigo, String cliente, String fechaEntrada, String fechaSalida, int precioTotal, boolean isReserve, String cardColor, String urlImagen,int metrosCuadrados, Boolean incluyeChimenea, int capacidadMaxima) {
        super(codigo, cliente, fechaEntrada, fechaSalida, precioTotal, isReserve, cardColor, urlImagen);
        this.metrosCuadrados = metrosCuadrados;
        this.incluyeChimenea = incluyeChimenea;
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getMetrosCuadrados() {return metrosCuadrados;}

    public void setMetrosCuadrados(int metrosCuadrados) {this.metrosCuadrados = metrosCuadrados;}

    public Boolean getIncluyeChimenea() {return incluyeChimenea;}

    public void setIncluyeChimenea(Boolean incluyeChimenea) {this.incluyeChimenea = incluyeChimenea;}

    public int getCapacidadMaxima() {return capacidadMaxima;}

    public void setCapacidadMaxima(int capacidadMaxima) {this.capacidadMaxima = capacidadMaxima;}
}
