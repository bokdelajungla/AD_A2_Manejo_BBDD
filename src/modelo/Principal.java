package modelo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.entidad.MatriculaNoValida;
import modelo.entidad.Pasajero;

import modelo.persistencia.DAOCocheMySql;
import modelo.persistencia.DAOPasajeroMySql;

/*
 * Antes de poder ejecutar el programa hay que asegurarse de haber creado la base de datos "bbdd",
 * Y haber creado las tablas correspondientes:
 * 
 * CREATE TABLE pasajeros (
 * 		id int(11) NOT NULL AUTO_INCREMENT,
 * 		nombre varchar(45) DEFAULT NULL, 
 *		edad int(3) DEFAULT NULL, 
 *		peso double DEFAULT NULL,
 *		PRIMARY KEY (id) );
 *
 *CREATE TABLE coches ( 
 * 		id int(11) NOT NULL AUTO_INCREMENT, 
 * 		matricula varchar(7) NOT NULL, 
 * 		marca varchar(15) DEFAULT NULL, 
 * 		modelo varchar(30) DEFAULT NULL, 
 *		color varchar(15) DEFAULT NULL, 
 *		capacidad int(3) DEFAULT 4,
 *		PRIMARY KEY (id) );
 *
 *CREATE TABLE asignaciones (
 *		id_coche int(11),
 *		id_pasajero int(11), 
 *		FOREIGN KEY (id_coche) REFERENCES coches(id),
 *		FOREIGN KEY (id_pasajero) REFERENCES pasajeros(id),
 *		PRIMARY KEY (id_coche,id_pasajero) );
 *
 */
public class Principal {

	public static void main(String[] args) {
		//Variables
		boolean salir = false; //Variable de control del bucle del menu Coches
		boolean submenu = false; //Variable de control del bucle del menu Pasajeros
		int opcion = 0; //Variable de control del switch
		Scanner sc = new Scanner(System.in); //Entrada de texto del usuario
		
		List<Coche> listaCoches = new ArrayList<Coche>(); //ArrayList que contendrá los objetos Coche
		List<Pasajero> listaPasajeros = new ArrayList<Pasajero>(); //ArrayList que contendrá los objetos Pasajero
		
		
		//Creamos los DAO
		DAOCocheMySql gestorCoches = new DAOCocheMySql();
		DAOPasajeroMySql gestorPasajeros = new DAOPasajeroMySql();
		
		//Inicio menú
		System.out.println("**** Programa de Gestión de Transporte ****");
		
		//Bucle principal
		while(!salir){
			//Mostramos el Menú
			mostrarMenuCoches();
			
			//Try-Catch para el Scanner
			try {
				opcion=sc.nextInt();
			}
			catch(InputMismatchException e) {
				//Tratamos el error
				opcion = 0; //Inicializamos la opción
			}
			finally {
				sc.nextLine();//Limpiamos el buffer
				int id; //Variable para almacenar el id
				int capacidad; //Variable para almacenar la capacidad
				boolean aux=false; //Variable auxiliar
				Coche coche; //Variable para almacenar un objeto Coche
				Pasajero pasajero; //Variable para almacenar un objeto Pasajero
				
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
					break;
					
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
					break;
					
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
					break;
				
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
					break;
					
				case 5: //Listado de coches
					System.out.println("Listar todos los coches:");
					//Devuelve la lista completa con todos los Coches
					listaCoches = gestorCoches.listar();
					//Se imprimen por pantalla cada uno de ellos
					for(Coche c: listaCoches) {
						System.out.println(c);
					}
					break;

				case 6: //Gestionar pasajeros
					submenu=true;
					while(submenu) {
						mostrarMenuPasajeros();
						//Try-Catch para el Scanner
						try {
							opcion=sc.nextInt();
						}
						catch(InputMismatchException e) {
							//Tratamos el error
							opcion = 0; //Inicializamos la opción
						}
						finally {
							sc.nextLine();//Limpiamos el buffer
							
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
								break;
							
							case 2: //Borrar Pasajero
								
								break;
								
							case 3: //Consulta pasajero por id
								
								break;
							case 4: //Modificar pasajero por id
								
								break;
							case 5: //Listado de pasajeros
								
								break;
							case 6: //Añadir pasajero a un coche
								
								break;
							case 7: //Eliminar pasajero de un coche
								
								break;
							case 8: //Listar pasajeros de un coche"
								
								break;
							case 9: //Volver
								
								break;
								
								
							}
						}
					}
					break;
					
				case 7: //Terminar el programa
					salir = true;
					break;
					
				default: //Opción no válida
					System.out.println("Opción no válida. Introduzca un núero entre 1 y 6");
					break;
				}
				
			}
		}
		sc.close(); //Cerramos el Scanner
	}

	private static void mostrarMenuCoches () {
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
	
	private static void mostrarMenuPasajeros () {
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
	
	private static Coche introducirCoche(Scanner sc) {
		int capacidad = 4;
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
	
	private static Pasajero introducirPasajero(Scanner sc) {
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
