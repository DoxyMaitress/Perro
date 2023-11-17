package org.example;

import javax.persistence.*;

@Entity
@Table(name = "perros")
public class Perro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "raza")
    private String raza;

    @Column(name = "edad")
    private int edad;

    @Column(name = "color")
    private String color;

    @Column(name = "dueño")
    private String dueño;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;

    @Column(name = "eliminado")
    private boolean eliminado;

    public Perro() {
    }

    public Perro(String nombre, String raza, int edad, String color, String dueño, String fechaNacimiento) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.color = color;
        this.dueño = dueño;
        this.fechaNacimiento = fechaNacimiento;
        this.eliminado = false; // Por defecto, el perro no está eliminado
    }

    // Getters y setters (puedes generarlos automáticamente en tu IDE)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDueño() {
        return dueño;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nNombre: " + nombre +
                "\nRaza: " + raza +
                "\nEdad: " + edad +
                "\nColor: " + color +
                "\nDueño: " + dueño +
                "\nFecha de Nacimiento: " + fechaNacimiento +
                "\n----------------------";
    }
}
