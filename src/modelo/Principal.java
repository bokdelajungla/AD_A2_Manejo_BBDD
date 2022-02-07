package modelo;

import java.util.InputMismatchException;
import java.util.Scanner;

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
		int opcion = 0; //Variable de control del switch
		Scanner sc = new Scanner(System.in); //Entrada de texto del usuario
		
		//Inicio menú
		System.out.println("**** Programa de Gestión de Transporte ****");
		
		//Instanciamos las clases que gestionaran los menús
		MenuCoche menuCoche = new MenuCoche(sc);
		
		//Bucle principal
		while(!salir){
			//Mostramos el menu de Coches
			menuCoche.mostrarMenu();
			
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
			
				salir = menuCoche.elegirOpcion(opcion);
			}
		}
		sc.close(); //Cerramos el Scanner
	}
}
