/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tarea7f;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author jorge
 */
public class Empleado {
    
    // Atributos
    private String nombreCompleto;
    private String dni;
    private String puesto;
    private LocalDate fechaPosesion;
    private LocalDate fechaCese;
    private String telefono;
    private boolean evaluador;
    private boolean coordinador;

    // constructores
    public Empleado() {
    }
    
    public Empleado(String nombreCompleto, String dni, String puesto, LocalDate fechaPosesion, LocalDate fechaCese, String telefono, boolean evaluador, boolean coordinador) {
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.puesto = puesto;
        this.fechaPosesion = fechaPosesion;
        this.fechaCese = fechaCese;
        this.telefono = telefono;
        this.evaluador = evaluador;
        this.coordinador = coordinador;
    }
    
    // getters y setters
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public LocalDate getFechaPosesion() {
        return fechaPosesion;
    }

    public void setFechaPosesion(LocalDate fechaPosesion) {
        this.fechaPosesion = fechaPosesion;
    }

    public LocalDate getFechaCese() {
        return fechaCese;
    }

    public void setFechaCese(LocalDate fechaCese) {
        this.fechaCese = fechaCese;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEvaluador() {
        return evaluador;
    }

    public void setEvaluador(boolean evaluador) {
        this.evaluador = evaluador;
    }

    public boolean isCoordinador() {
        return coordinador;
    }

    public void setCoordinador(boolean coordinador) {
        this.coordinador = coordinador;
    }
    
    // toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombreCompleto).append(",");
        sb.append(dni).append(",");
        sb.append(puesto).append(",");
        sb.append(fechaPosesion).append(",");
        sb.append(fechaCese).append(",");
        sb.append(telefono).append(",");
        sb.append((evaluador) ? "Sí":"No").append(",");
        sb.append((coordinador) ? "Sí":"No");
        return sb.toString();
    }
    
    // equals y hashcode
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.dni);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Empleado other = (Empleado) obj;
        return Objects.equals(this.dni, other.dni);
    }
    
    
}
