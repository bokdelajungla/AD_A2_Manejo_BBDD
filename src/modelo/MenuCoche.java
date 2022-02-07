package modelo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.entidad.MatriculaNoValida;
import modelo.persistencia.DAOCocheMySql;

public class MenuCoche {
	
	Scanner sc;
	Coche coche;
	List<Coche> listaCoches; //ArrayList que contendrá los objetos Coche
	DAOCocheMySql gestorCoches;
	MenuPasajero menuPasajero;
	
	public MenuCoche (Scanner sc) {
		this.sc = sc;
		this.coche = new Coche();
		this.listaCoches = new ArrayList<Coche>();
		this.gestorCoches = new DAOCocheMySql();
		this.menuPasajero = new MenuPasajero(sc);
	}
	
	public void mostrarMenu () {
		
		System.out.println("GESTIÓN de COCHES:");
		System.out.println("Elija una opción:");
		System.out.println("1 - Añadir nuevo coche");
		System.out.println("2 - Borrar coche por id");
		System.out.println("3 - Consulta coche por id");
		System.out.println("4 - Modificar coche por id");
		System.out.println("5 - Listado de coches");
		System.out.println("6 - Gestionar pasajeros");
		System.out.println("7 - Terminar el programa");
	}
	
	public boolean elegirOpcion(int opcion) {
		
		boolean aux = false;
		int id = 0;
		
		switch(opcion) {
		case 1: //Añadir nuevo coche
			System.out.println("Añadir nuevo coche:");
			coche = introducirCoche(sc);
			aux = gestorCoches.alta(coche);
			if(aux) {
				System.out.println("Se ha añadido el coche a la BD.");
				System.out.println(coche);
			}
			else {
				System.out.println("No se ha añadido el coche a la BD");
			}
			return false;
			
		case 2: //Borrar coche por id
			System.out.println("Eliminar coche:");
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
			//deleteCoche devuelve el objeto coche borrado
			//o null si no existe ningún coche con el ID especificado
			if(gestorCoches.baja(id)) {
				System.out.println("Se ha eliminado el coche de la BD");
			}
			else {
				System.out.println("No se ha eliminado el coche a la BD");
			}
			return false;
			
		case 3: //Consulta coche por id
			System.out.println("Consultar coche:");
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
			
			coche = gestorCoches.obtener(id);
			if (coche == null) {
				System.out.println("No hay ningún coche con ese ID");
			}
			else {
				System.out.println("El Coche solicitado:");
				System.out.println(coche);
			}
			return false;
		
		case 4:
			System.out.println("Modificar coche por ID:");
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
			
			coche = introducirCoche(sc);
			aux = gestorCoches.modificar(coche, id);
			if(aux) {
				System.out.println("Se ha modifiacado el coche a la BD.");
				System.out.println(coche);
			}
			else {
				System.out.println("No se ha modififcado el coche a la BD");
			}
			return false;
			
		case 5: //Listado de coches
			System.out.println("Listar todos los coches:");
			//Devuelve la lista completa con todos los Coches
			listaCoches = gestorCoches.listar();
			//Se imprimen por pantalla cada uno de ellos
			for(Coche c: listaCoches) {
				System.out.println(c);
			}
			return false;

		case 6: //Gestionar pasajeros
			boolean submenu=false;
			while(!submenu) {
				menuPasajero.mostrarMenu();
				//Try-Catch para el Scanner
				try {
					opcion=sc.nextInt();
				}
				catch(InputMismatchException e) {
					//Tratamos el error
					opcion = 0; //Inicializamos la opción
				}
				finally {
					submenu = menuPasajero.elegirOpcion(opcion);
				}
			}
			return false;
			
		case 7: //Terminar el programa
			System.out.println("Ha elegido SALIR. Finalizando...");
			return true;
			
		default: //Opción no válida
			System.out.println("Opción no válida. Introduzca un núero entre 1 y 6");
			return false;
		}
	}
	
	private static Coche introducirCoche(Scanner sc) {
		int capacidad = 4;
		sc.nextLine();//Limpiamos el buffer
		System.out.println("Intoduzca Matricula:");
		String matricula = sc.nextLine();
		System.out.println("Intoduzca Marca:");
		String marca = sc.nextLine();
		System.out.println("Intoduzca Modelo:");
		String modelo = sc.nextLine();
		System.out.println("Intoduzca Color:");
		String color = sc.nextLine();
		System.out.println("Intoduzca Capacidad:");
		try {
			capacidad=sc.nextInt();
		}
		catch(InputMismatchException e) {
			//Tratamos el error
			System.out.println("ERROR: La capacidad debe ser un número entero");
			capacidad = 4; //Ponemos la capacidad por defecto
		}
		sc.nextLine();//Limpiamos el buffer
		try {
			//Intentamos crear el objeto Coche invocando su constructor
			Coche coche = new Coche(matricula, marca, modelo, color, capacidad);
			return coche;
		}
		//Si la matricula no cumple el formato se lanza una excepcion MatriculaNoValida
		catch(MatriculaNoValida e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
