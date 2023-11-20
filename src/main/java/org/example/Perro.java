package org.example;

import javax.persistence.*;

@Entity

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

    @Column(name = "dueno")
    private String dueno;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "eliminado")
    private boolean eliminado;
    public Perro() {
    }

    public Perro(String nombre, String raza, int edad, String color, String dueno, String fechaNacimiento) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.color = color;
        this.dueno = dueno;
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

    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
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
                "\nDueno: " + dueno +
                "\nFecha de Nacimiento: " + fechaNacimiento +
                "\n----------------------";
    }}

