package org.example;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Random;
class ReadAndWriteCsvTest {
    public static void main(String[] args) throws SQLException, IOException {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tp_integrador_grupo10", "root", "F1deosverdes");
            Statement stmt = con.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void setRonda(Statement statement, int n) throws  SQLException {
        statement.execute("INSERT INTO ronda\n"+
                "VALUES("+n+", 'org.example.Ronda"+n+"')");
    }

    public static int getIDEquipo(Statement statement, String equipo) throws SQLException {
        ResultSet rs= statement.executeQuery("SELECT equipo_id FROM equipo WHERE nombre LIKE '"+equipo+"'");
        int id = 0;
        while (rs.next()){
            id = rs.getInt(1);};
        return id;
    }

    public static void setPartido(Statement statement, int id, int ronda_id, String primero, int goles_local, String segundo, int goles_visitante) throws SQLException {
        statement.execute("INSERT INTO partido\n"+
                "VALUES("+id+", "+ronda_id+", "+getIDEquipo(statement,primero)+", "+goles_local+", "+getIDEquipo(statement, segundo)+", "+goles_visitante);
    }
}