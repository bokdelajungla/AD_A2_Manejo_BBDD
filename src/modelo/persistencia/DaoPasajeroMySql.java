package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidad.Pasajero;
import modelo.persistencia.interfaces.DaoPasajero;

public class DaoPasajeroMySql implements DaoPasajero{

	private Connection conexion;
	
	
	public boolean abrirConexion(){
		String url = "jdbc:mysql://localhost:3306/bbdd";
		String usuario = "root";
		String password = "";
		try {
			conexion = DriverManager.getConnection(url,usuario,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * Método que añade un Objeto Pasajero a la BBDD
	 * @param pasajero el objeto de la clase Pasajero que se quiere añadir a la BBDD
	 * @return Devuelve true en caso de que el coche se haya podido añadir a la BBDD 
	 * 		   o false si en algún caso no se puede añadir, ya sea porque no está la conexión abierta 
	 * 			o no se ha cambiado nada en la BBDD
	 */
	@Override
	public boolean alta(Pasajero p) {
		if(!abrirConexion()){
			return false;
		}
		boolean alta = true;
		
		String query = "insert into pasajeros (NOMBRE,EDAD,PESO) "
				+ " values(?,?,?)";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, p.getNombre());
			ps.setInt(2, p.getEdad());
			ps.setDouble(3, p.getPeso());
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				alta = false;
		} catch (SQLException e) {
			System.out.println("alta -> Error al insertar: " + p);
			alta = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		
		return alta;
	}
	
	/**
	 * Método que elimina un objeto Pasajero de la BBDD 
	 * @param id el ID del coche que se quiere eliminar
	 * @return true si se elimina de la BBDD o false en caso de que  no exista ningún coche con 
	 * 		   el ID que se pasa como parámetro.
	 */
	@Override
	public boolean baja(int id) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean borrado = true;
		String query = "delete from pasajeros where id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				borrado = false;
		} catch (SQLException e) {
			borrado = false;
			System.out.println("baja -> No se ha podido dar de baja"
					+ " el id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return borrado; 
	}
	
	/**
	 * Método para obtener un Pasajero 
	 * @param el ID del pasajero que queremos buscar
	 * @return pasajero si existía en la BBDD y null si no se ha podido realizar
	 * la conexión o si no había ninguno con ese ID
	 */
	@Override
	public Pasajero obtener(int id) {
		if(!abrirConexion()){
			return null;
		}		
		Pasajero pasajero = null;
		
		String query = "select ID,NOMBRE,EDAD,PESO from pasajeros "
				+ "where id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				pasajero = new Pasajero();
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getDouble(4));
			}
		} catch (SQLException e) {
			System.out.println("obtener -> error al obtener el "
					+ "pasajero con id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		
		
		return pasajero;
	}
	/**
	 * Método que devuelve un List con todos los Pasajeros
	 * @return un objeto de tipo List que contiene todos los objetos Pasajero añadidos a la BBDD
	 */
	@Override
	public List<Pasajero> listar() {
		if(!abrirConexion()){
			return null;
		}		
		List<Pasajero> listaPasajeros = new ArrayList<>();
		
		String query = "select ID,NOMBRE,EDAD,PESO,COCHE from pasajeros";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Pasajero pasajero = new Pasajero();
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getDouble(4));
				pasajero.setCoche(rs.getInt(5));
				
				listaPasajeros.add(pasajero);
			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los "
					+ "pasajeros");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		
		
		return listaPasajeros;
	}

	@Override
	public boolean modificar(Pasajero p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Método que asigna un pasajero a un coche
	 * @paramel el ID del pasajero que se quiere asignar y el ID del coche donde quiere ser asignado.
	 * @return Devuelve true en caso de que el coche se haya podido añadir al coche 
	 * 		   o false si en algun caso no se puede añadir, ya sea porque no está la conexion abierta 
	 * 			o no se ha cambiado nada en la BBDD
	 */
	@Override
	public boolean aniadirPasajeroACoche(int idCoche, int idPasajero) {
		if(!abrirConexion()){
			return false;
		}
		boolean modificado = true;
		String query = "update pasajeros set coche=? WHERE ID=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, idCoche);
			ps.setInt(2, idPasajero);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				modificado = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("modificar -> error al modificar el "
					+ " pasajero " + idPasajero);
			modificado = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		return modificado;
	}
	
	/**
	 * Método que elimina un pasajero a un coche
	 * @paramel el ID del pasajero que se quiere eliminar y el ID del coche donde quiere ser eliminado.
	 * @return Devuelve true en caso de que el coche se haya podido eliminar el pasajero del coche 
	 * 		   o false si en algun caso no se puede eliminar, ya sea porque no está la conexion abierta 
	 * 			o no se ha cambiado nada en la BBDD
	 */
	@Override
	public boolean eliminarPasajeroACoche(int idPasajero) {
		if(!abrirConexion()){
			return false;
		}
		boolean modificado = true;
		String query = "update pasajeros set coche=NULL WHERE ID=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, idPasajero);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				modificado = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("modificar -> error al modificar el "
					+ " pasajero " + idPasajero);
			modificado = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		return modificado;
	}
	
	/**
	 * Método para listar todos los pasajeros que tiene un coche
	 * @paramel el ID del pasajero que se quiere comprobar
	 * @return Devuelve un List de Pasajeros con si había alguno asociado
	 * y devuelve null si no habia ninguno.
	 */
	@Override
	public List<Pasajero> listarPasajerosDeUnCoche(int idCoche) {
		if(!abrirConexion()){
			return null;
		}		
		List<Pasajero> listaPasajeros = new ArrayList<>();
		
		String query = "select ID,NOMBRE,EDAD,PESO,COCHE from pasajeros where COCHE=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, idCoche);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Pasajero pasajero = new Pasajero();
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getDouble(4));
				pasajero.setCoche(rs.getInt(5));
				
				listaPasajeros.add(pasajero);
			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los "
					+ "pasajeros");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return listaPasajeros;
	}

}
