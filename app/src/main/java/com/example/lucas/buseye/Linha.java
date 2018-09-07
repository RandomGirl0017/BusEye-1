package com.example.lucas.buseye;

import java.util.ArrayList;
import java.util.List;

public class Linha {
    String nome, horario, sentido;
    List<String> trajeto = new ArrayList<String>();
    double extensao;
    boolean noturno;
    Onibus onibus;
}
