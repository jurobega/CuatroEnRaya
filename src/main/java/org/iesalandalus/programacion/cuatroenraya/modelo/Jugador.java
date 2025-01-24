package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public record Jugador(String nombre, Ficha colorFichas) {

    public Jugador {
        validarNombre(nombre);
        validarColor(colorFichas);
    }

    private void validarNombre(String nombre) {
        Objects.requireNonNull(nombre,"El nombre no puede ser nulo.");
    }

    private void validarColor(Ficha colorFicha) {
        Objects.requireNonNull(colorFicha,"La ficha no puede ser nula.");
    }

    @Override
    public String toString() {
        return String.format("%s, %s]", nombre, colorFichas);
    }
}
