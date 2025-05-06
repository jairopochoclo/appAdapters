package com.example.appadaper.Reservas;

import java.util.List;

public class ReservaGlamping extends ReservaCabana{
    private String tipoExperiencia;
    private String actividadesIncluidas;
    private String img;

    public ReservaGlamping(String codigo, String cliente, String fechaEntrada, String fechaSalida, int precioTotal, boolean isReserve, String cardColor, String urlImagen, int price, int metrosCuadrados, Boolean incluyeChimenea, int capacidadMaxima, String tipoExperiencia, String actividadesIncluidas, String img, int esperanzaVida, List<String> datos) {
        super(codigo, cliente, fechaEntrada, fechaSalida, precioTotal, isReserve, cardColor, urlImagen, price, metrosCuadrados, incluyeChimenea, capacidadMaxima, esperanzaVida, datos);
        this.tipoExperiencia = tipoExperiencia;
        this.actividadesIncluidas = actividadesIncluidas;
        this.img = img;
    }


    public String getTipoExperiencia() {return tipoExperiencia;}

    public void setTipoExperiencia(String tipoExperiencia) {this.tipoExperiencia = tipoExperiencia;}

    public String getActividadesIncluidas() {return actividadesIncluidas;}

    public void setActividadesIncluidas(String actividadesIncluidas) {this.actividadesIncluidas = actividadesIncluidas;}
    public String getImg() {return img;}

    public void setImg(String img) {this.img = img;}
}
