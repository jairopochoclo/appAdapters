package com.example.appadaper.Reservas;

import java.io.Serializable;

public class Reserva implements Serializable {
    private String codigo;
    private String cliente;
    private String fechaEntrada;
    private String fechaSalida;
    private int precioTotal;
    private boolean isReserve;
    private String cardColor;
    private String urlImagen;
    private int price;

    public Reserva(String codigo, String cliente, String fechaEntrada, String fechaSalida, int precioTotal, boolean isReserve, String cardColor, String urlImagen, int price) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.precioTotal = precioTotal;
        this.isReserve = isReserve;
        this.cardColor = cardColor;
        this.urlImagen = urlImagen;
        this.price = price;
    }

    public String getCodigo() {return codigo;}

    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getCliente() {return cliente;}

    public void setCliente(String cliente) {this.cliente = cliente;}

    public String getFechaEntrada() {return fechaEntrada;}

    public void setFechaEntrada(String fechaEntrada) {this.fechaEntrada = fechaEntrada;}

    public String getFechaSalida() {return fechaSalida;}

    public void setFechaSalida(String fechaSalida) {this.fechaSalida = fechaSalida;}

    public int getPrecioTotal() {return precioTotal;}

    public void setPrecioTotal(int precioTotal) {this.precioTotal = precioTotal;}
    public boolean isReserve() {return isReserve;}

    public void setReserve(boolean reserve) {isReserve = reserve;}
    public String getCardColor() {return cardColor;}

    public void setCardColor(String cardColor) {this.cardColor = cardColor;}
    public String getUrlImagen() {return urlImagen;}

    public void setUrlImagen(String urlImagen) {this.urlImagen = urlImagen;}
    public int getPrice() {return price;}

    public void setPrice(int price) {this.price = price;}

}
