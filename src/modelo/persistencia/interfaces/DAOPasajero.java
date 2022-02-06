package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidad.Pasajero;

public interface DAOPasajero {
	
	public boolean alta(Pasajero p);
	public boolean baja(int id);
	public Pasajero obtener(int id);
	public boolean modificar(Pasajero p, int id);
	public List<Pasajero> listar();
		

}
