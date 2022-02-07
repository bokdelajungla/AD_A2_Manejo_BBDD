package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidad.Coche;
import modelo.entidad.Pasajero;

public interface DAOCoche {
	
	public boolean alta(Coche c);
	public boolean baja(int id);
	public Coche obtener(int id);
	public boolean modificar(Coche c, int id);
	public List<Coche> listar();
	
}
