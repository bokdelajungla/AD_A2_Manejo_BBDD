package mainpage;

//
//CREATE TABLE coches ( 
//id int(11) NOT NULL AUTO_INCREMENT, 
//matricula varchar(45) DEFAULT NULL, 
//marca varchar(45) DEFAULT NULL, 
//modelo varchar(45) DEFAULT NULL,
//color varchar(45) DEFAULT NULL,
//PRIMARY KEY (id) );
//
//CREATE TABLE pasajeros ( 
//id int(11) NOT NULL AUTO_INCREMENT, 
//nombre varchar(45) DEFAULT NULL, 
//edad int(3) DEFAULT NULL, 
//peso double DEFAULT NULL,
//coche int(11) DEFAULT NULL,
//PRIMARY KEY (id),
//FOREIGN KEY (coche) REFERENCES coches(id)
//);


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.entidad.Pasajero;
import modelo.persistencia.DaoCocheMySql;
import modelo.persistencia.DaoPasajeroMySql;
import modelo.persistencia.interfaces.DaoCoche;
import modelo.persistencia.interfaces.DaoPasajero;

public class MainPage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		boolean salir = false;
		int numero = 0;
		int numero2 = 0;
		DaoCoche dc = new DaoCocheMySql();
		DaoPasajero dp = new DaoPasajeroMySql();

		while(!salir){
			
			System.out.println("MENU: Escriba el numero con la opción deseada");
			System.out.println("1 - Añadir nuevo coche");
			System.out.println("2 - Borrar coche por id");
			System.out.println("3 - Consulta de coche por id");
			System.out.println("4 - Modificar coche por id");
			System.out.println("5 - Listado de coches");
			System.out.println("6 - Terminar el programa");
			System.out.println("7 - Gestión de pasajeros");
			
			
			try {
				numero =sc.nextInt();
			}
			catch(InputMismatchException e) {
				numero = 0; 
				sc.next();
			}
			finally {
				String[] cocheDatos = new String[5];
				Coche c = new Coche();
				int idCoche =0;
				int idPasajero = 0;
				switch(numero) {
					case 1: // Añadir nuevo coche
						sc.nextLine();
						System.out.println("Introduzca los datos del Coche:");
						System.out.println("Matricula:");
						cocheDatos[0] = sc.nextLine();
						System.out.println("Marca:");
						cocheDatos[1] = sc.nextLine();
						System.out.println("Modelo:");
						cocheDatos[2] = sc.nextLine();
						System.out.println("Color:");
						cocheDatos[3] = sc.nextLine();
						String matricula = cocheDatos[1];
							try {
								c.setMatricula(cocheDatos[0]);
								c.setMarca(cocheDatos[1]);
								c.setModelo(cocheDatos[2]);
								c.setColor(cocheDatos[3]);
								boolean alta = dc.alta(c);
								
								if(alta){
									System.out.println("El coche se ha dado de alta");
								}else{
									System.out.println("El coche NO se ha dado de alta");
								}
							}catch(NumberFormatException e) {
								System.out.println("Ha introducido algún campo no válido");
								break;
							}
						break;
					case 2: //  Borrar coche por id
						System.out.println("Introduzca ID");
						try {
							idCoche = sc.nextInt();
							boolean baja = dc.baja(idCoche);
							if(baja){
								System.out.println("El coche se ha dado de baja");
							}else{
								System.out.println("El coche NO se ha dado de baja");
							}
						}
						catch(InputMismatchException e) {
							System.out.println("Introduzca un ID válido");
						}
						break;
					case 3: // Consulta de coche por id"
						System.out.println("Introduzca ID");
						try {
							idCoche = sc.nextInt();
							Coche coche = dc.obtener(idCoche);
							if (coche != null) {
								System.out.println(coche);
							} else {
								System.out.println("No hay ningun coche con este ID");
							}
						}
						catch(InputMismatchException e) {
							System.out.println("Introduzca un ID válido");
						}
						break;
					case 4: // Modificar coche por id
						sc.nextLine();
						System.out.println("Introduzca los datos del Coche:");
						System.out.println("Id:");
						cocheDatos[0] = sc.nextLine();
						System.out.println("Matricula:");
						cocheDatos[1] = sc.nextLine();
						System.out.println("Marca:");
						cocheDatos[2] = sc.nextLine();
						System.out.println("Modelo:");
						cocheDatos[3] = sc.nextLine();
						System.out.println("Color:");
						cocheDatos[4] = sc.nextLine();
							try {
								c.setId(Integer.parseInt(cocheDatos[0]));
								c.setMatricula(cocheDatos[1]);
								c.setMarca(cocheDatos[2]);
								c.setModelo(cocheDatos[3]);
								c.setColor(cocheDatos[4]);
								boolean modificar = dc.modificar(c);
								if(modificar){
									System.out.println("El coche se ha modificado");
								}else{
									System.out.println("El coche NO se ha modificado");
								}
							}catch(NumberFormatException e) {
								System.out.println("Ha introducido algún campo no válido");
								break;
							}
						break;
					case 5: // Listado de coches
						List<Coche> listaCoches = dc.listar();
						System.out.println("********* LISTANDO TODOS LOS COCHES **********");
						for(Coche coc1 : listaCoches){
							System.out.println(coc1);
						}
						break;
					case 6: // Terminar el programa
						salir = true;
						break;
					case 7: // Gestión de pasajeros
						System.out.println("GESTIÓN DE PASAJEROS: Escriba el numero con la opción deseada");
						System.out.println("1 - Añadir nuevo pasajero");
						System.out.println("2 - Borrar pasajero por id");
						System.out.println("3 - Consulta de coche por id");
						System.out.println("4 - Listado de todos los pasajeros");
						System.out.println("5 - Añadir pasajero al coche");
						System.out.println("6 - Eliminar pasajero de un coche");
						System.out.println("7 - Listar todos los pasajeros de un coche");
						
						try {
							numero2 =sc.nextInt();
						}
						catch(InputMismatchException e) {
							numero2 = 0; 
							sc.next();
						}
						finally {
							String[] pasajeroDatos = new String[3];
							double peso = 0.00;
							Pasajero p = new Pasajero();
							switch (numero2) {
							case 1: // Añadir nuevo pasajero
								sc.nextLine();
								System.out.println("Introduzca los datos del Pasajero:");
								System.out.println("Nombre:");
								pasajeroDatos[0] = sc.nextLine();
								System.out.println("Edad:");
								pasajeroDatos[1] = sc.nextLine();
								System.out.println("Peso:");
								peso = sc.nextDouble();
									try {
										p.setNombre(pasajeroDatos[0]);
										p.setEdad(Integer.parseInt(pasajeroDatos[1]));
										p.setPeso(peso);
										boolean alta = dp.alta(p);
										
										if(alta){
											System.out.println("El pasajero se ha dado de alta");
										}else{
											System.out.println("El pasajero NO se ha dado de alta");
										}
									}catch(NumberFormatException e) {
										System.out.println("Ha introducido algún campo no válido");
										break;
									}
								break;
							case 2: // Borrar pasajero por id
								System.out.println("Introduzca ID");
								try {
									idPasajero = sc.nextInt();
									boolean baja = dp.baja(idPasajero);
									if(baja){
										System.out.println("El pasajero se ha dado de baja");
									}else{
										System.out.println("El pasajero NO se ha dado de baja");
									}
								}
								catch(InputMismatchException e) {
									System.out.println("Introduzca un ID válido");
								}
								break;
							case 3: // Consulta de coche por id
								System.out.println("Introduzca ID");
								try {
									idPasajero = sc.nextInt();
									Pasajero pasajero = dp.obtener(idPasajero);
									if (pasajero != null) {
										System.out.println(pasajero);
									} else {
										System.out.println("No hay ningun pasajero con este ID");
									}
								}
								catch(InputMismatchException e) {
									System.out.println("Introduzca un ID válido");
								}
								break;
							case 4: // Listado de todos los pasajeros
								List<Pasajero> listaPasajero = dp.listar();
								System.out.println("********* LISTANDO TODOS LOS PASAJEROS **********");
								for(Pasajero pas : listaPasajero){
									System.out.println(pas);
								}
								break;
							case 5: // Añadir pasajero al coche
								System.out.println("5 - Añadir pasajero al coche");
								List<Coche> listaCoches2 = dc.listar();
								System.out.println("********* COCHES DISPONIBLES **********");
								for(Coche coc2 : listaCoches2){
									System.out.println(coc2);
								}
								
								System.out.println("Introduzca ID del coche");
								try {
									idCoche = sc.nextInt();
									System.out.println("Introduzca ID del pasajero que quiere asociar al coche");
									idPasajero = sc.nextInt();
									boolean aniadir = dp.aniadirPasajeroACoche(idCoche, idPasajero);
									if (aniadir) {
										System.out.println("Se ha añadido al coche correctamente");
									} else {
										System.out.println("No se ha podido añadir al coche");
									}
								}
								catch(InputMismatchException e) {
									System.out.println("Introduzca un ID válido");
								}
								
								break;
							case 6: // Eliminar pasajero de un coche
								List<Coche> listaCoches5 = dc.listar();
								System.out.println("********* LISTANDO TODOS LOS COCHES Y SUS PASAJEROS **********");
								for(Coche coc1 : listaCoches5){
									System.out.println(coc1);
									List<Pasajero> listaPasajero3 = dp.listarPasajerosDeUnCoche(coc1.getId());
									for(Pasajero pas : listaPasajero3){
										System.out.print("Pasajeros: ");
										System.out.println(pas);
									}
								}
								System.out.println("Introduzca ID del pasajero:");
								try {
									idPasajero= sc.nextInt();
									boolean baja = dp.eliminarPasajeroACoche(idPasajero);
									if(baja){
										System.out.println("El pasajero se ha dado de baja del coche");
									}else{
										System.out.println("El pasajero NO se ha dado de baja del coche");
									}
								}
								catch(InputMismatchException e) {
									System.out.println("Introduzca un ID válido");
								}
								break;
							case 7: // Listar todos los pasajeros de un coche
								System.out.println("Introduzca ID del coche");
								try {
									idCoche = sc.nextInt();
									boolean aniadir = dp.aniadirPasajeroACoche(idCoche, idPasajero);
									List<Pasajero> listaPasajero3 = dp.listarPasajerosDeUnCoche(idCoche);
									System.out.println("********* LISTANDO TODOS LOS PASAJEROS DEL COCHE SELECCIONADO **********");
									for(Pasajero pas : listaPasajero3){
										System.out.println(pas);
									}
								}
								catch(InputMismatchException e) {
									System.out.println("Introduzca un ID válido");
								}
								break;
							default:
								break;
							}
						}
						
						break;
					default:
						System.out.println("Opción no válida");
				}
			}
		}
		sc.close();
	}

}
