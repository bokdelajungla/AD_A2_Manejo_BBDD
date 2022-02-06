package com.requerimiento2.modelo.entidad;


public class Pasajero {
	
	private int id;
	private String nombre;
	private int edad;
	private int peso;
	private int idCoche;
	
	public Pasajero() {
		id = 0;
		nombre = null;
		edad = 0;
		peso = 0;
		idCoche = 0;
	}
	
	public Pasajero(int id, String nombre, int edad, int peso, int idCoche) {
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.peso = peso;
		this.idCoche = idCoche;
	}
	

	public Pasajero(String nombre, int edad, int peso) {
		id = 0;
		this.nombre = nombre;
		this.edad = edad;
		this.peso = peso;
		idCoche = 0;
	}


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

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getIdCoche() {
		return idCoche;
	}

	public void setIdCoche(int idCoche) {
		this.idCoche = idCoche;
	}

	@Override
	public String toString() {
		return "Pasajero [id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", peso=" + peso + ", idCoche="
				+ idCoche + "]";
	}
	
	

}
