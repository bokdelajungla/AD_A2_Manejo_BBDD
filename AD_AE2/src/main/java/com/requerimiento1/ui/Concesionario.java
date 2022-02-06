package com.requerimiento1.ui;

import java.util.List;
import java.util.Scanner;

import com.requerimiento1.modelo.entidad.Coche;
import com.requerimiento1.modelo.persistencia.DaoCocheMySql;

public class Concesionario {

	
	public static void main(String[] args) {
		boolean salir = false;
		int opc;
		int id;
		String matricula;
		Scanner sc = new Scanner(System.in);
		DaoCocheMySql dc = new DaoCocheMySql();
		
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
					
					List<Coche> listaCoches = dc.list();
					if(listaCoches != null) {
						for(Coche coche: listaCoches) {
							System.out.println(coche.toString());
						}
					}
					break;
				case 6:
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
				"6 - Salir\n" +
				"**************************\n\n" +
				"Introduzca una opción:");
	}
	
}
