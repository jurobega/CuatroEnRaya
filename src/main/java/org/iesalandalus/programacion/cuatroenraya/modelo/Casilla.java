package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Casilla {
    private Ficha ficha;

    public Casilla() {
        this.ficha = null;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) throws CuatroEnRayaExcepcion {
        if (estaOcupado()) {
            throw new CuatroEnRayaExcepcion("La casilla ya contiene una ficha.");
        }
        this.ficha = Objects.requireNonNull(ficha,"No se puede poner una ficha nula.");
    }


    public boolean estaOcupado() {
        boolean ocupado;
        if (getFicha() == null) {
            ocupado = false;
        }else {
            ocupado = true;
        }
        return ocupado;
    }

    @Override
    public String toString() {
        String stringResultado;
        if (estaOcupado()){
            stringResultado = String.format("%s",this.ficha);
        }else {
            stringResultado = String.format("%s"," ");
        }
        return String.format("%s",stringResultado);
    }
}
