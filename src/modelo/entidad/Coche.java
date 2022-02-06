package modelo.entidad;

import java.io.Serializable;
import java.util.List;

public class Coche implements Serializable{ //Hay que implementar Serializable para que se pueda guardar en un fichero como objeto
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String matricula;
	private String marca;
	private String modelo;
	private String color;
	private int capacidad;
	private List<Pasajero> pasajeros;
	
	public final static String MATRICULA_VALIDA = "^[0-9]{4}[A-Z]{3}$"; //Expresión regular para la matrícula 
	
	public Coche() {
		super();
	}
	public Coche (String matricula, String marca, String modelo, String color, int capacidad) throws MatriculaNoValida{
		if(validarMatricula(matricula)) {
			this.matricula = matricula;
			this.marca = marca;
			this.modelo = modelo;
			this.color = color;
			this.capacidad = capacidad;
		}
		else {
			throw new MatriculaNoValida("Matrícula No válida. Debe cumplir el formato 0000XXX");
		}
	}

	/*Método para validar la matrícula*/
	public static boolean validarMatricula (String matricula) {
		if(matricula.toUpperCase().matches(MATRICULA_VALIDA)) {
            return true;
        } else {
            return false;
        }
	}
	
	@Override
	public String toString() {
		return "Coche:\nID:" + id + "\nmatricula:" + matricula + "\nmodelo:" + modelo + "\nmarca:" + marca + "\ncolor=" + color + "\ncapacidad=" + capacidad + "\n";
	}
	
	/*Método para convertir al formato del fichero de texto*/
	public String toTxt() {
		return id +"-"+ matricula +"-"+ marca +"-"+ modelo +"-"+ color;
	}
	
	/* GETTERS & SETTERS */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
}
