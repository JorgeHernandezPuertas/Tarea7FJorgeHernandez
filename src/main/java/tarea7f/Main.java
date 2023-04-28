/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tarea7f;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Format;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jorge
 */
public class Main {

    public static void main(String[] args) {

        // Declaro la ruta en la que esta el fichero que voy a leer
        String ruta = "./RelPerCen.csv";

        // Leo el fichero y lo guardo en una lista de empleados
        List<Empleado> empleados = leerArchivo(ruta);

        // Imprimo la lista para comprobar
        System.out.println("Imprimo la lista de empleados:");
        empleados.forEach(e -> System.out.println(e));
        System.out.println("------------------------------------------------------");
        
        // Genero el fichero json
        String rutaJson = "./empleados.json";
        // Filtro la lista inicial de empleados para cumplir el enunciado
        generarJson(rutaJson, filtrarLista(empleados));
        
        // Cuantos profesores de tecnologia hay
        int profesoresTecnologia = contarTecnologiaNoStream(empleados);
        int profesoresTecnologia2 = (int) empleados.stream()
                .filter(e -> e.getPuesto().contains("Tecnología"))
                .count();
        System.out.println("Cuántos profesores de tecnología hay:");
        System.out.println("Sin API Stream: " + profesoresTecnologia);
        System.out.println("Con API Stream: " + profesoresTecnologia2);
        System.out.println("------------------------------------------------------");
        
        // Compruebo si algún profesor de informática es también cordinado
        Boolean coordinador = informaticaEsCoordinadorSinStream(empleados);
        Boolean coordinador2 = empleados.stream()
                .anyMatch(e -> e.getPuesto().contains("Informática") && e.isCoordinador());
        
        System.out.println("¿Algún profesor de informática es coordinador?");
        System.out.println("Sin API Stream: " + coordinador);
        System.out.println("Con API Stream: " + coordinador2);
        System.out.println("------------------------------------------------------");

        // Obtengo una nueva lista ordenada alfabéticamente con todos 
        // los apellidos de los empleados cuyo NIF contenga la letra J.
        List<String> listaApellidos = listaOrdenadaNoStream(empleados);
        List<String> listaApellidos2 = empleados.stream()
                .filter(e -> e.getDni().contains("J"))
                .map(e -> e.getNombreCompleto().split(",")[0]) // Cojo solo los apellidos
                .toList();
                        
        
        System.out.println("Listas de apellidos ordenadas y filtradas:");
        System.out.println("Sin API Stream: ");
        listaApellidos.forEach(a -> System.out.println(a));
        System.out.println("------------------------------------------------------");
        System.out.println("Con API Stream: ");
        listaApellidos2.forEach(a -> System.out.println(a));
        System.out.println("------------------------------------------------------");
                
        
        // Verifico que ningún profesor se llame jonh
        Boolean hayJonh = verificarJonh(empleados);
        Boolean hayJonh2 = empleados.stream()
                .anyMatch(e -> e.getNombreCompleto().contains("Jonh"));
        System.out.println("¿Hay algún Jonh?");
        System.out.println("Sin API Stream: " + hayJonh);
        System.out.println("Con API Stream: " + hayJonh2);
        System.out.println("------------------------------------------------------");
        
    }

    // Método para leer el fichero
    private static List<Empleado> leerArchivo(String ruta) {
        List<Empleado> empleados = new ArrayList<>();
        String linea = "";
        String[] tokens;

        try ( Scanner flujo = new Scanner(new FileReader(ruta))) {
            flujo.nextLine(); // Me salto la primera linea con el encabezado
            while (flujo.hasNext()) {
                linea = flujo.nextLine();
                tokens = linea.split(",");
                empleados.add(new Empleado(tokens[0] + "," + tokens[1], tokens[2], tokens[3],
                        formatearFecha(tokens[4]), formatearFecha(tokens[5]),
                        tokens[6], parsearBoolean(tokens[7]),
                        parsearBoolean(tokens[8])));
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        return empleados;
    }

    // Método para formatear la fecha
    private static LocalDate formatearFecha(String fecha) {
        LocalDate fechaFormateada = null;
        if (!fecha.equals("")) {
            fechaFormateada = LocalDate.parse(fecha,
                    DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        }
        return fechaFormateada;
    }

    // Método para escribir un fichero Json
    private static void generarJson(String ruta, List<Empleado> empleados) {
        // Creo el mapeador que uso para crear el fichero json
        ObjectMapper mapeador = new ObjectMapper();

        // Hago que el mapeador pueda usar el JavaTime
        mapeador.registerModule(new JavaTimeModule());

        // Hago que el formato Json este bien indentado
        mapeador.configure(SerializationFeature.INDENT_OUTPUT, true);

        // Escribo el fichero json con el mapeador
        try {
            mapeador.writeValue(new File(ruta), empleados);
            System.out.println("El fichero json se ha generado correctamente");
        } catch (IOException e) {
            System.out.println("Ha habido un error generando el fichero json.");
            System.out.println(e.getMessage());
        }
    }
    
    // Hago un método que devuelva una lista con los que llevan trabajando entre 10
    // y 15 años a partir de la otra lista
    private static List<Empleado> filtrarLista(List<Empleado> lista){
        List<Empleado> listafiltrada = lista.stream()
                .filter(e -> e.getFechaPosesion().
                        until(LocalDate.now(), ChronoUnit.YEARS) >= 10
                && e.getFechaPosesion().
                        until(LocalDate.now(), ChronoUnit.YEARS) <= 15)
                .toList();
        return listafiltrada;
    }
    
    // Método para contar profesores de Tecnología en la lista sin Stream
    private static int contarTecnologiaNoStream(List<Empleado> lista){
        int profesores = 0;
        for(Empleado e:lista){
            if (e.getPuesto().contains("Tecnología")){
                profesores++;
            }
        }
        return profesores;
    }
    
    // Método para saber si algún profesor/a de Informática es también coordinador sin Stream
    private static boolean informaticaEsCoordinadorSinStream(List<Empleado> lista){
        boolean esCoordinador = false;
        for(Empleado e:lista){
            if(e.getPuesto().contains("Informática") && e.isCoordinador()){
                esCoordinador = true;
                break; // Ya he encontrado uno asi que paro
            }
        }
        return esCoordinador;
    }

    // Método para obtener la lista ordenada sin api stream
    private static List<String> listaOrdenadaNoStream(List<Empleado> lista){
        List<String> listaOrdenada = new ArrayList<>();
        String[] tokens;
        // Hago la lista con el filtro
        for(Empleado e:lista){
            if(e.getDni().contains("J")){
                tokens = e.getNombreCompleto().split(",");
                listaOrdenada.add(tokens[0]);
            }
        }
        
        // Ordeno la lista
        listaOrdenada.sort((e1, e2) -> e1.
                compareToIgnoreCase(e2));
        return listaOrdenada;
    }
    
    // Método para verificar que ningún profesor se llama "Jonh".
    private static Boolean verificarJonh(List<Empleado> lista){
        boolean hayJohn = false;
        for(Empleado e:lista){
            if (e.getNombreCompleto().contains("Jonh")){
                hayJohn = true;
                break; // He encontrado uno y me salgo
            }
        }
        return hayJohn;
    }
    
    // Método para parsear los boolean
    private static Boolean parsearBoolean(String frase){
        Boolean parseado = false;
        if (frase.equalsIgnoreCase("Sí")){
            parseado = true;
        }
        return parseado;
    }
    
}
