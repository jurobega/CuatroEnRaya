package org.iesalandalus.programacion.cuatroenraya.modelo;

public enum Ficha {
    AZUL,
    VERDE;

    @Override
    public String toString() {
       return String.format("%c", name().charAt(0));
    }

}
