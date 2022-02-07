package modelo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.entidad.Pasajero;
import modelo.persistencia.DAOCocheMySql;
import modelo.persistencia.DAOPasajeroMySql;

public class MenuPasajero {

	Scanner sc;
	Pasajero pasajero;
	List<Pasajero> listaPasajeros; //ArrayList que contendrá los objetos Pasajero
	List<Coche> listaCoches; //ArrayList que contendrá los objetos Coche
	DAOPasajeroMySql gestorPasajeros;
	DAOCocheMySql gestorCoches;
	
	public MenuPasajero (Scanner sc) {
		this.sc = sc;
		this.pasajero = new Pasajero();
		this.listaPasajeros = new ArrayList<Pasajero>();
		this.listaCoches = new ArrayList<Coche>();
		this.gestorPasajeros = new DAOPasajeroMySql();
		this.gestorCoches = new DAOCocheMySql();
	}
	
	public void mostrarMenu () {
		System.out.println("GESTIÓN de PASAJEROS:");
		System.out.println("Elija una opción:");
		System.out.println("1 - Añadir nuevo pasajero");
		System.out.println("2 - Borrar pasajero por id");
		System.out.println("3 - Consulta pasajero por id");
		System.out.println("4 - Modificar pasajero por id");
		System.out.println("5 - Listado de pasajeros");
		System.out.println("6 - Añadir pasajero a un coche");
		System.out.println("7 - Eliminar pasajero de un coche");
		System.out.println("8 - Listar pasajeros de un coche");
		System.out.println("9 - Volver");
	}
	
	public boolean elegirOpcion(int opcion) {
		
		boolean aux = false;
		int id = 0;
		int id_coche = 0;
		
		switch(opcion) {
		
		case 1: //Añadir nuevo pasajero
			System.out.println("Añadir nuevo pasajero:");
			pasajero = introducirPasajero(sc);
			aux = gestorPasajeros.alta(pasajero);
			if(aux) {
				System.out.println("Se ha añadido el pasajero a la BD.");
				System.out.println(pasajero);
			}
			else {
				System.out.println("No se ha añadido el pasajero a la BD");
			}
			return false;
		
		case 2: //Borrar Pasajero
			System.out.println("Eliminar pasajero:");
			System.out.println("Intoduzca ID:");
			try {
				id=sc.nextInt();
			}
			catch(InputMismatchException e) {
				//Tratamos el error
				System.out.println("El id debe ser un número entero");
				id = 0; //Inicializamos la opción
			}
			sc.nextLine();//Limpiamos el buffer
			if(gestorPasajeros.baja(id)) {
				System.out.println("Se ha eliminado el pasajero de la BD");
			}
			else {
				System.out.println("No se ha eliminado el pasajero a la BD");
			}
			return false;
			
		case 3: //Consulta pasajero por id
			System.out.println("Consultar pasajero:");
			System.out.println("Intoduzca ID:");
			try {
				id=sc.nextInt();
			}
			catch(InputMismatchException e) {
				//Tratamos el error
				System.out.println("El id debe ser un número entero");
				id = 0; //Inicializamos la opción
			}
			sc.nextLine();//Limpiamos el buffer
			
			pasajero = gestorPasajeros.obtener(id);
			if (pasajero == null) {
				System.out.println("No hay ningún pasajero con ese ID");
			}
			else {
				System.out.println("El pasajero solicitado:");
				System.out.println(pasajero);
			}
			return false;

		case 4: //Modificar pasajero por id
			System.out.println("Modificar pasajero por ID:");
			System.out.println("Intoduzca ID:");
			try {
				id=sc.nextInt();
			}
			catch(InputMismatchException e) {
				//Tratamos el error
				System.out.println("El id debe ser un número entero");
				id = 0; //Inicializamos la opción
			}
			sc.nextLine();//Limpiamos el buffer
			
			pasajero = introducirPasajero(sc);
			aux = gestorPasajeros.modificar(pasajero, id);
			if(aux) {
				System.out.println("Se ha modifiacado el pasajero a la BD.");
				System.out.println(pasajero);
			}
			else {
				System.out.println("No se ha modififcado el pasajero a la BD");
			}
			return false;
			
		case 5: //Listado de pasajeros
			System.out.println("Listar todos los pasajeros:");
			//Devuelve la lista completa con todos los Pasajeros
			listaPasajeros = gestorPasajeros.listar();
			//Se imprimen por pantalla cada uno de ellos
			for(Pasajero p: listaPasajeros) {
				System.out.println(p);
			}
			return false;
			
		case 6: //Añadir pasajero a un coche
			System.out.println("Añadir pasajero a un coche:");
			System.out.println("Mostrando lista de coches:");
			listaCoches = gestorCoches.listar();
			for(Coche c: listaCoches) {
				System.out.println(c);
			}
			System.out.println("Intoduzca ID del coche:");
			try {
				id=sc.nextInt();
			}
			catch(InputMismatchException e) {
				//Tratamos el error
				System.out.println("El id debe ser un número entero");
				id = 0; //Inicializamos la opción
			}
			sc.nextLine();//Limpiamos el buffer
			id_coche=id;
			System.out.println("Intoduzca ID del pasajero:");
			try {
				id=sc.nextInt();
			}
			catch(InputMismatchException e) {
				//Tratamos el error
				System.out.println("El id debe ser un número entero");
				id = 0; //Inicializamos la opción
			}
			sc.nextLine();//Limpiamos el buffer
			
			aux = gestorPasajeros.addPasajero(id, id_coche);
			if(aux) {
				System.out.println("Se ha añadido el pasajero al coche.");
			}
			else {
				System.out.println("No se ha añadido el pasajero al coche");
			}
			return false;
			
		case 7: //Eliminar pasajero de un coche
			System.out.println("Eliminar pasajero de un coche:");
			System.out.println("Mostrando lista de coches y sus pasajeros:");
			listaCoches = gestorCoches.listar();
			for(Coche c: listaCoches) {
				System.out.println("Coche con ID " + c.getId()+":");
				listaPasajeros = gestorPasajeros.listPasajeros(c.getId());
				for(Pasajero p: listaPasajeros) {
					System.out.println("\t"+p);
				}
			}
			System.out.println("Intoduzca ID del coche:");
			try {
				id=sc.nextInt();
			}
			catch(InputMismatchException e) {
				//Tratamos el error
				System.out.println("El id debe ser un número entero");
				id = 0; //Inicializamos la opción
			}
			sc.nextLine();//Limpiamos el buffer
			id_coche=id;
			System.out.println("Intoduzca ID del pasajero:");
			try {
				id=sc.nextInt();
			}
			catch(InputMismatchException e) {
				//Tratamos el error
				System.out.println("El id debe ser un número entero");
				id = 0; //Inicializamos la opción
			}
			sc.nextLine();//Limpiamos el buffer
			
			aux = gestorPasajeros.deletePasajero(id, id_coche);
			if(aux) {
				System.out.println("Se ha eliminado el pasajero del coche.");
			}
			else {
				System.out.println("No se ha eliminado el pasajero del coche");
			}
			return false;
			
		case 8: //Listar pasajeros de un coche"
			System.out.println("Intoduzca ID del coche:");
			try {
				id=sc.nextInt();
			}
			catch(InputMismatchException e) {
				//Tratamos el error
				System.out.println("El id debe ser un número entero");
				id = 0; //Inicializamos la opción
			}
			sc.nextLine();//Limpiamos el buffer
			id_coche=id;
			System.out.println("los pasajeros en el Coche con id "+id_coche+" son:");
			listaPasajeros = gestorPasajeros.listPasajeros(id_coche);
			for(Pasajero p: listaPasajeros) {
					System.out.println("\t"+p);
			}
			return false;
			
		case 9: //Volver
			return true;
		default:
			System.out.println("Opción no válida. Introduzca un núero entre 1 y 9");
			return false;
		}
	}
	
	
	private static Pasajero introducirPasajero(Scanner sc) {
		sc.nextLine();//Limpiamos el buffer
		System.out.println("Intoduzca Nombre:");
		String nombre = sc.nextLine();
		System.out.println("Intoduzca Edad:");
		int edad=0;
		try {
			edad = sc.nextInt();
		}
		catch(InputMismatchException e) {
			//Tratamos el error
			System.out.println("ERROR: La edad debe ser un número entero");
			edad = 0; //Inicializamos la opción
		}
		sc.nextLine();
		System.out.println("Intoduzca Peso:");
		double peso = 0;
		try {
			peso=sc.nextDouble();
		}
		catch(InputMismatchException e) {
			//Tratamos el error
			System.out.println("ERROR: El peso debe ser numérico");
			peso = 0; //Inicializamos la opción
			return null;
		}
		sc.nextLine();//Limpiamos el buffer
		//Intentamos crear el objeto Pasajero invocando su constructor
		Pasajero pasajero = new Pasajero(nombre, edad, peso);
		return pasajero;

	}
}
