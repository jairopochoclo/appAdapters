package com.example.appadaper.Reservas;

public class ReservaGlamping extends ReservaCabana{
    private String tipoExperiencia;
    private String actividadesIncluidas;

    public ReservaGlamping(int codigo, String cliente, String fechaEntrada, String fechaSalida, int precioTotal,boolean isReserve,String cardColor,String urlImagen, int metrosCuadrados, Boolean incluyeChimenea, int capacidadMaxima, String tipoExperiencia, String actividadesIncluidas) {
        super(codigo, cliente, fechaEntrada, fechaSalida, precioTotal, isReserve,cardColor,urlImagen, metrosCuadrados, incluyeChimenea, capacidadMaxima);
        this.tipoExperiencia = tipoExperiencia;
        this.actividadesIncluidas = actividadesIncluidas;
    }

    public String getTipoExperiencia() {return tipoExperiencia;}

    public void setTipoExperiencia(String tipoExperiencia) {this.tipoExperiencia = tipoExperiencia;}

    public String getActividadesIncluidas() {return actividadesIncluidas;}

    public void setActividadesIncluidas(String actividadesIncluidas) {this.actividadesIncluidas = actividadesIncluidas;}
}
