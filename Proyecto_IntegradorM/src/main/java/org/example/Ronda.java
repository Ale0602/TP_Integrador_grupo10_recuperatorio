package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ronda {
    public Ronda(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    private int numero;
    private String nombre;
    public ArrayList<Partido> partidos = new ArrayList<Partido>();

    public int getNumero() {return numero;}
    public String getNombre() {
        return nombre;
    }
}