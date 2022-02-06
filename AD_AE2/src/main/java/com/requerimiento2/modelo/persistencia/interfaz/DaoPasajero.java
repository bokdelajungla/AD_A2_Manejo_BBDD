package com.requerimiento2.modelo.persistencia.interfaz;

import java.util.List;

import com.requerimiento2.modelo.entidad.Pasajero;

//define un CRUD (Create-Read-Update-Delete) para el objeto Pasajero

public interface DaoPasajero {
  public boolean add(Pasajero p);
  public boolean del(int id);
  public Pasajero get(int id);
  public boolean addToCar(int idPass, int idCar);
  public boolean delFromCar(int idPass, int idCar);
  public List<Pasajero> list();
  public List<Pasajero> listFromCar(int id);

}
