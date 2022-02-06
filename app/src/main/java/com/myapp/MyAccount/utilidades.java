package com.myapp.MyAccount;
//Clase complementaria para buscar productos en la base de datos
public class utilidades {
    public static final String TABLA_PRODUCTO="productos";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_CANTIDAD="cantidad";
    public static final String CAMPO_PRECIOUND="precio_und";
    public static final String CAMPO_PRECIOTOT="precio_total";
    public static final String CAMPO_EVALUADOR="evaluador";

    public static final String CREAR_TABLA_PRODUCTO="CREATE TABLE "+TABLA_PRODUCTO+"("+CAMPO_CANTIDAD+" INTEGER, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_PRECIOUND+" INTEGER, "+CAMPO_PRECIOTOT+" INTEGER, "+CAMPO_EVALUADOR+" INTEGER)";

}
