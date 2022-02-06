package com.myapp.MyAccount;

//Clase practica para buscar o consultar en la base de datos
public class Producto  {
    private Integer cantidad;
    private String nombre;
    private Integer preciound;
    private Integer preciotot;
    private Integer evaluador;

    public Producto(int cantidad, String nombre, int preciound, int precitot, int evaluador){
        this.cantidad =cantidad;
        this.nombre = nombre;
        this.preciound = preciound;
        this.preciotot = precitot;
        this.evaluador = evaluador;
    }
    public Producto(){

    }
    public Integer getCantidad(){ return cantidad;}
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}
    public String getNombre(){ return nombre;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public Integer getPreciound(){ return preciound;}
    public void setPreciound(Integer preciound){this.preciound = preciound;}
    public Integer getPreciotot(){return preciotot;}
    public void setPreciotot(Integer preciotot){this.preciotot = preciotot; }
    public Integer getEvaluador() { return evaluador;  }
    public void setEvaluador(Integer evaluador) { this.evaluador = evaluador; }
    }
