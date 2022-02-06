package modelo.entidad;

import java.io.Serializable;

public class Pasajero implements Serializable{ //Hay que implementar Serializable para que se pueda guardar en un fichero como objeto
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nombre;
	private int edad;
	private double peso;
	
	public Pasajero () {
		super();
	}
	
	public Pasajero (String nombre, int edad, double peso) {
			this.nombre = nombre;
			this.edad = edad;
			this.peso = peso;
		}

	@Override
	public String toString() {
		return "Pasajero [id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", peso=" + peso + "]";
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

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	
	}