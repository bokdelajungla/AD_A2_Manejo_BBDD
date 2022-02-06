package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidad.Pasajero;

public interface DaoPasajero {
	public boolean alta(Pasajero p);
	public boolean baja(int id);
	public boolean modificar(Pasajero p);
	public Pasajero obtener(int id);
	public List<Pasajero> listar();
	public boolean aniadirPasajeroACoche(int idCoche, int idPasajero);
	public boolean eliminarPasajeroACoche(int idPasajero);
	public List<Pasajero> listarPasajerosDeUnCoche(int idCoche);
}
