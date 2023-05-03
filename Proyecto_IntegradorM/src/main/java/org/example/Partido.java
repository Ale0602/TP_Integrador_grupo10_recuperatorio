package org.example;

import com.opencsv.bean.CsvBindByPosition;

public class Partido {
    public Partido(Equipo primero, Equipo segundo, int goles_local, int goles_visitante) {
        this.primero = primero;
        this.segundo = segundo;
        this.goles_local = goles_local;
        this.goles_visitante = goles_visitante;
    }
    private Equipo primero;
    private Equipo segundo;
    private int goles_local;
    private int goles_visitante;
    public ResultadoEnum resultado;

    public void setResultado(ResultadoEnum resultado) {this.resultado = resultado;}

    public Equipo getPrimero() {return primero;}
    public Equipo getSegundo() {return segundo;}
}
