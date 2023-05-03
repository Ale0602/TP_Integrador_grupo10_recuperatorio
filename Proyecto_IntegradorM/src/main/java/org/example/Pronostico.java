package org.example;

import java.util.Objects;
public class Pronostico {
    public Pronostico(String participante, Partido partido, ResultadoEnum resultado) {
        this.participante = participante;
        this.partido = partido;
        this.resultado = resultado;
        if (partido.resultado == this.resultado) {
            acierta = true;
        }
    }
    private String participante;
    private Partido partido;
    private ResultadoEnum resultado;
    public boolean acierta = false;
    public Partido getPartido() {
        return partido;
    }

    public String getResultadoString() {
        if (resultado == ResultadoEnum.GANADOR_PRIMERO) {
            return "Gana " + partido.getPrimero().getNombre();
        } else if (resultado == ResultadoEnum.GANADOR_SEGUNDO) {
            return "Gana " + partido.getSegundo().getNombre();
        } else {
            return "Empate";
        }
    }

    public String getParticipante() {
        return participante;
    }
}