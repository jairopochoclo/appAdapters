package com.example.appadaper.Reservas;

public class ReservaHotel extends Reserva{
    private String tipoHabitacion;
    private Boolean incluyeDesayuno;
    private int numeroHuespedes;

    public ReservaHotel(String codigo, String cliente, String fechaEntrada, String fechaSalida, int precioTotal,boolean isReserve,String cardColor, String urlImagen,int price ,String tipoHabitacion, Boolean incluyeDesayuno, int numeroHuespedes) {
        super(codigo, cliente, fechaEntrada, fechaSalida, precioTotal, isReserve, cardColor, urlImagen, price);
        this.tipoHabitacion = tipoHabitacion;
        this.incluyeDesayuno = incluyeDesayuno;
        this.numeroHuespedes = numeroHuespedes;
    }

    public String getTipoHabitacion() {return tipoHabitacion;}

    public void setTipoHabitacion(String tipoHabitacion) {this.tipoHabitacion = tipoHabitacion;}

    public Boolean getIncluyeDesayuno() {return incluyeDesayuno;}

    public void setIncluyeDesayuno(Boolean incluyeDesayuno) {this.incluyeDesayuno = incluyeDesayuno;}

    public int getNumeroHuespedes() {return numeroHuespedes;}

    public void setNumeroHuespedes(int numeroHuespedes) {this.numeroHuespedes = numeroHuespedes;}
}
