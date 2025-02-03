package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;

    private final Casilla[][] casillas;


    public Tablero() {
        casillas =  new Casilla[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++){
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    public boolean estaVacio() {
        boolean vacio = true;
        for (int i = 0; i < COLUMNAS && vacio ; i++){
            vacio  = columnaVacia(i);
        }
        return vacio;
    }

    private boolean columnaVacia(int columna) {
       return !casillas[0][columna].estaOcupado();
    }

    public boolean estaLleno() {
       boolean lleno = true;
       for (int i = 0; i < COLUMNAS && lleno ;i++) {
           lleno = columnaLlena(i);
       }
       return lleno;
    }

    private boolean columnaLlena(int columnas) {
       return casillas[FILAS - 1][columnas].estaOcupado();
    }

    public boolean introducirFicha(int columna, Ficha ficha) throws CuatroEnRayaExcepcion {
        comprobarColumna(columna);
        comprobarFicha(ficha);
        if (columnaLlena(columna)){
            throw new CuatroEnRayaExcepcion("Columna llena.");
        }
        int fila = getPrimeraFilaVacia(columna);
        casillas[fila][columna].setFicha(ficha);
        return comprobarTirada(fila,columna);
    }


    private void comprobarFicha(Ficha ficha ) {
        Objects.requireNonNull(ficha,"La ficha no puede ser nula.");

    }

    private void comprobarColumna(int columna) {
        if (columna < 0 || columna > COLUMNAS) {
            throw new IllegalArgumentException("Esta columna no es valida.");
        }
    }

    private int getPrimeraFilaVacia(int columna) {
        int filaVacia = 0;
        while (filaVacia < FILAS && casillas[filaVacia][columna].estaOcupado()){
            filaVacia++;
        }
        return filaVacia;
    }

    private boolean comprobarTirada(int fila,int columna){
        Ficha ficha = casillas[fila][columna].getFicha();
        return comprobarHorizontal(fila,ficha) || comprobarVertical(columna,ficha) || comprobarDiagonalNE(fila,columna,ficha) || comprobarDiagonalNO(fila,columna,ficha);
    }

    private boolean objetivoAlcanzado(int fichasIgualesConsecutivas) {
        return fichasIgualesConsecutivas >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private boolean comprobarHorizontal(int fila, Ficha ficha){
        int fichasIgualesConsecutivas = 0;
        for (int columna = 0; !objetivoAlcanzado(fichasIgualesConsecutivas) && columna < COLUMNAS; columna++){
            if (casillas[fila][columna].estaOcupado() && casillas[fila][columna].getFicha().equals(ficha)) {
                fichasIgualesConsecutivas++;
            }else {
                fichasIgualesConsecutivas = 0;
            }
        }
        return objetivoAlcanzado(fichasIgualesConsecutivas);
    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        int fichasIgualesConsecutivas = 0;
        for (int fila = 0 ; !objetivoAlcanzado(fichasIgualesConsecutivas) && fila < getPrimeraFilaVacia(columna); fila++){
            if (casillas[fila][columna].getFicha().equals(ficha)){
                fichasIgualesConsecutivas++;
            }else {
                fichasIgualesConsecutivas = 0;
            }
        }
        return objetivoAlcanzado(fichasIgualesConsecutivas);
    }

    private boolean comprobarDiagonalNE(int filaActual, int columnaActual,Ficha ficha) {
        int fichasIgualesConsecutivas = 0;
        int desplazamineto = menor(filaActual,columnaActual);
        int filaInicial = filaActual - desplazamineto;
        int columnaInicila = columnaActual - desplazamineto;
        for (int fila = filaInicial , columna = columnaInicila; !objetivoAlcanzado(fichasIgualesConsecutivas) && (fila < FILAS && columna < COLUMNAS); fila++,columna++ ) {
            if (casillas[fila][columna].estaOcupado() && casillas[fila][columna].getFicha().equals(ficha)){
                fichasIgualesConsecutivas++;
            }else  {
                fichasIgualesConsecutivas = 0;
            }
        }
        return objetivoAlcanzado(fichasIgualesConsecutivas);
    }

    private boolean comprobarDiagonalNO(int filaActual , int columnaActual,Ficha ficha) {
        int fichasIgualesConsecutivas = 0 ;
        int desplazamiento  = menor(filaActual,COLUMNAS - 1 - columnaActual) ;
        int filaInicial = filaActual - desplazamiento;
        int columnaInicial = columnaActual + desplazamiento;
        for (int fila = filaInicial , columa = columnaInicial ; !objetivoAlcanzado(fichasIgualesConsecutivas) && (fila < FILAS && columa >= 0 ); fila++,columa--){
            if (casillas[fila][columa].estaOcupado() && casillas[fila][columa].getFicha().equals(ficha)){
                fichasIgualesConsecutivas++;
            }else {
                fichasIgualesConsecutivas = 0;
            }
        }
        return objetivoAlcanzado(fichasIgualesConsecutivas);
    }

    private int menor(int fila,int columna) {
        return Math.min(fila, columna);
    }

    @Override
    public String toString() {
        StringBuilder salida = new StringBuilder("|");
        for (int i  = FILAS - 1 ; i  >= 0; i--){
            for (int j = 0; j < COLUMNAS; j++){
                salida.append(casillas[i][j]);
            }
            salida.append(i == 0 ? "|\n" : "|\n|");
        }
        String barraHorizontal = String.format(String.format("%%0%dd%n" , COLUMNAS), 0 ).replace('0','-');
        salida.append(barraHorizontal);
        return salida.toString();
    }
}
