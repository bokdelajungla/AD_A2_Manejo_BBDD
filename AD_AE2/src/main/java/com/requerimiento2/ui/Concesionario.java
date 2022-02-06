package com.requerimiento2.ui;

import java.util.List;
import java.util.Scanner;

import com.requerimiento2.modelo.entidad.Coche;
import com.requerimiento2.modelo.persistencia.DaoCocheMySql;
import com.requerimiento2.modelo.persistencia.DaoPasajeroMySql;
import com.requerimiento2.modelo.entidad.Pasajero;

public class Concesionario {

	
	public static void main(String[] args) {
		boolean salir = false;
		int opc;
		int id;
		int idCoche;
		String matricula;
		Scanner sc = new Scanner(System.in);
		List<Coche> listaCoches;
		List<Pasajero> listaPasajeros;
		
		DaoCocheMySql dc = new DaoCocheMySql();
		DaoPasajeroMySql dp = new DaoPasajeroMySql();
		
		while(!salir) {
			menu();
			opc = sc.nextInt();
			//limpiamos el buffer
			sc.nextLine();
			switch(opc) {
				case 1:
					System.out.println("** Introduzca los datos del coche: **");
					do {
						System.out.print("Matricula: ");
						matricula = sc.nextLine(); 
						if(!Coche.matriculaValida(matricula)) {
							System.out.println("Debe introducir una matricula valida");
						}
					} while(!Coche.matriculaValida(matricula));
					System.out.print("Marca: ");
					String marca = sc.nextLine();
					System.out.print("Modelo: ");
					String modelo = sc.nextLine();
					System.out.print("Color: ");
					String color = sc.nextLine();
					
					if(dc.add(new Coche(matricula, marca, modelo, color))) {
						System.out.println("Coche añadido correctamente");
					}
					break;
				case 2:
					System.out.println("** Introduzca el id del coche a borrar: **");
					System.out.print("Id: ");
					id = sc.nextInt(); 
					//limpiamos el buffer
					sc.nextInt();
	
					if(dc.del(id)) {
						System.out.println("Coche borrado correctamente");
					}
					break;
				case 3:
					System.out.println("** Introduzca el id del coche a consultar: **");
					System.out.print("Id: ");
					id = sc.nextInt(); 
					//limpiamos el buffer
					sc.nextInt();
	
					Coche c = dc.get(id);
					if(c != null) {
						System.out.println(c.toString());
					}
					break;
				case 4:
					System.out.println("** Introduzca el id del coche a modificar: **");
					System.out.print("Id: ");
					id = sc.nextInt();
					//limpiamos el buffer
					sc.nextInt();
	
					if(dc.update(id)) {
						System.out.println("Coche modificado correctamente");
					}
					break;
				case 5:
					System.out.println("** Listado de coches: **");
					
					listaCoches = dc.list();
					if(listaCoches != null) {
						for(Coche coche: listaCoches) {
							System.out.println(coche.toString());
						}
					}
					break;
				case 6:
					while(!salir) {
						subMenu();
						opc = sc.nextInt();
						//limpiamos el buffer
						sc.nextLine();
						switch(opc) {
							case 1:
								System.out.println("** Introduzca los datos del pasajero: **");
								System.out.print("Nombre: ");
								String nombre = sc.nextLine(); 
								System.out.print("Edad: ");
								int edad = sc.nextInt();
								System.out.print("Peso: ");
								int peso = sc.nextInt();
								//limpiamos el buffer
								sc.nextLine();
								
								if(dp.add(new Pasajero(nombre, edad, peso))) {
									System.out.println("Pasajero añadido correctamente");
								}
								break;
							case 2:
								System.out.println("** Introduzca el id del pasajero a borrar: **");
								System.out.print("Id: ");
								id = sc.nextInt(); 
								//limpiamos el buffer
								sc.nextInt();
								
								if(dp.del(id)) {
									System.out.println("Pasajero eliminado correctamente");
								}
								break;
							case 3:
								System.out.println("** Introduzca el id del pasajero a consultar: **");
								System.out.print("Id: ");
								id = sc.nextInt(); 
								//limpiamos el buffer
								sc.nextInt();
								
								Pasajero p = dp.get(id);
								if(p != null) {
									System.out.println(p.toString());
								}
								break;
							case 4:
								System.out.println("** Listado de pasajeros: **");
	
								listaPasajeros = dp.list();
								if(listaPasajeros != null) {
									for(Pasajero pasajero: listaPasajeros) {
										System.out.println(pasajero.toString());
									}
								}
								break;
							case 5:
								System.out.println("** Añadir pasajero a un coche **");
								System.out.print("Coches disponibles: ");
								listaCoches = dc.list();
								if(listaCoches != null) {
									for(Coche coche: listaCoches) {
										System.out.println(coche.toString());
									}
								}
								id = sc.nextInt();  
								System.out.print("Id del coche: ");
								idCoche = sc.nextInt();  
								//limpiamos el buffer
								sc.nextInt();

								if(dp.addToCar(id, idCoche)) {
									System.out.println("Pasajero añadido al coche correctamente");
								}
								break;
							case 6:
								System.out.println("** Eliminar pasajero de un coche **");
								System.out.print("Coches con sus pasajeros: ");
								listaCoches = dc.list();
								if(listaCoches != null) {
									for(Coche coche: listaCoches) {
										System.out.println(coche.toString());
										System.out.print("pasajeros: ");
										listaPasajeros = dp.listFromCar(coche.getId());
										for(Pasajero pasajero: listaPasajeros) {
											System.out.println(pasajero.toString());
										}
									}
								}
								System.out.print("Id del pasajero: ");
								id = sc.nextInt();  
								System.out.print("Id del coche: ");
								idCoche = sc.nextInt();  
								//limpiamos el buffer
								sc.nextInt();

								if(dp.delFromCar(id, idCoche)) {
									System.out.println("Pasajero eliminado del coche correctamente");
								}
								break;
							case 7:
								System.out.println("** Listado de pasajeros de un coche **");
								System.out.print("Id del coche: ");
								id = sc.nextInt();  
								//limpiamos el buffer
								sc.nextInt();
								
								List<Pasajero> lp = dp.listFromCar(id);
								if(lp != null) {
									for(Pasajero pasajero: lp) {
										System.out.println(pasajero.toString());
									}
								}
								break;
							case 8:
								System.out.println("Volvemos al menu principal");
								salir = true;
								break;
							default:
								System.out.println("Opción no válida");
								break;
						}
					}
					salir = false;
					break;
				case 7:
					System.out.println("Programa finalizado");
					salir = true;
					break;
				default:
					System.out.println("Opción no válida");
					break;
			}
		}
		sc.close();

	}
	
	public static void menu() {
		System.out.println("\n********** Menú **********\n" + 
				"1 - Añadir coche\n" +
				"2 - Borrar coche por id\n" +
				"3 - Consultar coche por id\n" +
				"4 - Modificar coche por id\n" +
				"5 - Listado de coches\n" +
				"6 - Gestionar pasajeros\n" +
				"7 - Salir\n" +
				"**************************\n\n" +
				"Introduzca una opción:");
	}
	
	public static void subMenu() {
		System.out.println("\n****** Menú Pasajeros ******\n" + 
				"1 - Añadir pasajero\n" +
				"2 - Borrar pasajero por id\n" +
				"3 - Consultar pasajero por id\n" +
				"4 - Listar todos los pasajeros\n" +
				"5 - Añadir pasajero a un coche\n" +
				"6 - Eliminar pasajero de un coche\n" +
				"7 - Listar los pasajeros de un coche\n" +
				"8 - Volver al menu\n" +
				"**************************\n\n" +
				"Introduzca una opción:");
	}
	
}
