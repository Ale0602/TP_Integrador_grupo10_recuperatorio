package org.example;

import com.google.protobuf.Value;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.Key;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        String[] participantes = new String[]{"JULIAN", "FLOR", "LUCIANO"};
        HashMap<String, Integer> puntajes = new HashMap<>();
        HashMap<String, Integer> aciertos = new HashMap<>();
        for (String participante: participantes) {
            puntajes.put(participante, 0);
            aciertos.put(participante, 0);
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tp_integrador_grupo10", "root", "F1deosverdes");
            Statement stmt = con.createStatement();
            Statement stmt_temp = con.createStatement();

            for (int i = 1; i <= getNRondas(stmt); i++) {
                int ronda_id = i;
                Ronda ronda = new Ronda(ronda_id, getNombreFromIdRonda(stmt_temp, ronda_id));
                print(ronda.getNombre());
                ResultSet rs = stmt.executeQuery("SELECT * FROM partido WHERE ronda_id = " + ronda_id);

                while(rs.next()){
                    int partido_id = rs.getInt(1);
                    String primero = getNombreFromIdEquipo(stmt_temp, rs.getInt(3));
                    String segundo = getNombreFromIdEquipo(stmt_temp,rs.getInt(5));
                    int goles_local = rs.getInt(4);
                    int goles_visitante = rs.getInt(6);

                    Partido partido = new Partido(new Equipo(primero), new Equipo(segundo), goles_local, goles_visitante);
                    print(primero+" vs "+segundo+" = "+ partido.resultado);

                    for (String participante: participantes) {
                        ResultadoEnum resultado = null;
                        int ganador_id = getIdGanador(stmt_temp, partido_id, participante);
                        if (ganador_id == 0) {
                            resultado = ResultadoEnum.EMPATE;
                        } else if (ganador_id == rs.getInt(3)) {
                            resultado = ResultadoEnum.GANADOR_PRIMERO;
                        } else if (ganador_id == rs.getInt(5)) {
                            resultado = ResultadoEnum.GANADOR_SEGUNDO;
                        }

                        Pronostico pronostico = new Pronostico(participante, partido, resultado);
                        print(pronostico.getParticipante() + ": "+ pronostico.getResultadoString() +" -->> "+ pronostico.acierta);

                        if (pronostico.acierta) {
                            aciertos.replace(pronostico.getParticipante(), aciertos.get(pronostico.getParticipante()), aciertos.get(pronostico.getParticipante()) + 1);
                            puntajes.replace(pronostico.getParticipante(), puntajes.get(pronostico.getParticipante()), puntajes.get(pronostico.getParticipante()) + 1);
                        }
                    }
                    print("-------------------------------");
                }
                print("\nAciertos "+aciertos);
                print("*************************************************************");

                for (String participante: participantes) {
                    if (aciertos.get(participantes) == 6) {
                        puntajes.replace(participante, puntajes.get(participante), puntajes.get(participante) + 1);
                    }
                }

                for (String participante: participantes) {
                    aciertos.put(participante, 0);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        print("Puntajes: "+puntajes);
    }
    public static void VerRonda(Ronda ronda) {
        System.out.println("org.example.Ronda " + ronda.getNumero());
        for (Partido partido : ronda.partidos) {
            System.out.println(partido.getPrimero().getNombre() + " vs " + partido.getSegundo().getNombre());
        }
        System.out.println("-------------------------------");
    }

    public static int getNRondas(Statement statement) throws  SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT ronda_id FROM ronda");
        int ult_ronda = 0;
        while (resultSet.next()) {
            ult_ronda = resultSet.getInt(1);
        }
        return ult_ronda;
    }

    public static int getIDEquipo(Statement statement, String equipo) throws SQLException {
        ResultSet rs= statement.executeQuery("SELECT equipo_id FROM equipo WHERE nombre LIKE '"+equipo+"'");
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);};
        return id;
    }

    public static String getNombreFromIdEquipo(Statement statement, int id) throws SQLException {
        ResultSet rs= statement.executeQuery("SELECT nombre FROM equipo WHERE equipo_id = "+id);
        String nombre_equipo = null;
        while (rs.next()) {
            nombre_equipo = rs.getString(1);};
        return nombre_equipo;
    }

    public static String getNombreFromIdRonda(Statement statement, int id) throws SQLException {
        ResultSet rs= statement.executeQuery("SELECT nombre FROM ronda WHERE ronda_id = "+id);
        String nombre_ronda = null;
        while (rs.next()) {
            nombre_ronda = rs.getString(1);};
        return nombre_ronda;
    }

    public static void print(String string) {System.out.println(string);}

    public static int getIdGanador(Statement statement, int partido_id, String participante) throws SQLException {
        int id_ganador = 0;
        ResultSet rs = statement.executeQuery("SELECT id_ganador FROM pronostico WHERE partido_id = "+partido_id+" " +
                "AND participante = '"+participante+"'");
        while (rs.next()) {
            id_ganador = rs.getInt(1);
        }
        return id_ganador;
    }
}