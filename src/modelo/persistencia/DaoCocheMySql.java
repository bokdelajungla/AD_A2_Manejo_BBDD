package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entidad.Coche;
import modelo.persistencia.interfaces.DaoCoche;

public class DaoCocheMySql implements DaoCoche {
	
	private Connection conexion;
	
	/**
	 * Metodo para abrir la conexi�n a la BBDD
	 * @return Devuelve true si se ha podido abrir la conexi�n y false si 
	 * se ha producido alg�n fallo
	 */
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
	/**
	 * Metodo para cerrar la conexi�n a la BBDD
	 * @return Devuelve true si se ha podido cerrar la conexi�n y false si 
	 * se ha producido alg�n fallo
	 */
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
	 * M�todo que a�ade un Objeto Coche a la BBDD
	 * @param coche el objeto de la clase Coche que se quiere a�adir a la BBDD
	 * @return Devuelve true en caso de que el coche se haya podido a�adir a la BBDD 
	 * 		   o false si en algun caso no se puede a�adir, ya sea porque no est� la conexion abierta 
	 * 			o no se ha cambiado nada en la BBDD
	 */
	@Override
	public boolean alta(Coche c) {
		if(!abrirConexion()){
			return false;
		}
		boolean alta = true;
		
		String query = "insert into coches (MATRICULA,MARCA,MODELO,COLOR) "
				+ " values(?,?,?,?)";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, c.getMatricula());
			ps.setString(2, c.getMarca());
			ps.setString(3, c.getModelo());
			ps.setString(4, c.getColor());
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				alta = false;
		} catch (SQLException e) {
			System.out.println("alta -> Error al insertar: " + c);
			alta = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		return alta;
	}
	/**
	 * M�todo que elimina un objeto Coche de la BBDD 
	 * @param id el ID del coche que se quiere eliminar
	 * @return true si se elimina de la BBDD o false en caso de que  no exista ning�n coche con 
	 * 		   el ID que se pasa como par�metro.
	 */
	@Override
	public boolean baja(int id) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean borrado = true;
		String query = "delete from coches where id = ?";
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
	 * M�todo para modificar un Coche 
	 * @param el objeto Coche con los datos que queremos modificar
	 * @return un true si ha encontrado el id del coche y false si no se ha podido
	 * conectar o si no ha afectado a ninguna fila 
	 */
	@Override
	public boolean modificar(Coche c) {
		if(!abrirConexion()){
			return false;
		}
		boolean modificado = true;
		String query = "update coches set MATRICULA=?, MARCA=?, "
				+ "MODELO=?, COLOR=? WHERE ID=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, c.getMatricula());
			ps.setString(2, c.getMarca());
			ps.setString(3, c.getModelo());
			ps.setString(4, c.getColor());
			ps.setInt(5, c.getId());
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				modificado = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("modificar -> error al modificar la "
					+ " persona " + c);
			modificado = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		return modificado;
	}
	
	/**
	 * M�todo para obtener un Coche 
	 * @param el ID del coche que queremos buscar
	 * @return coche si exist�a en la BBDD y null si no se ha podido realizar
	 * la conexi�n o si no hab�a ninguno con ese ID
	 */
	@Override
	public Coche obtener(int id) {
		if(!abrirConexion()){
			return null;
		}		
		Coche coche = null;
		
		String query = "select ID,MATRICULA,MARCA,MODELO,COLOR from coches "
				+ "where id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				coche = new Coche();
				coche.setId(rs.getInt(1));
				coche.setMatricula(rs.getString(2));
				coche.setMarca(rs.getString(3));
				coche.setModelo(rs.getString(4));
				coche.setColor(rs.getString(5));
			}
		} catch (SQLException e) {
			System.out.println("obtener -> error al obtener la "
					+ "persona con id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return coche;
	}
	/**
	 * M�todo que devuelve un List con todos los Coches
	 * @return un objeto de tipo List que contiene todos los objetos Coches a�adidos a la BBDD
	 */
	@Override
	public List<Coche> listar() {
		if(!abrirConexion()){
			return null;
		}		
		List<Coche> listaCoches= new ArrayList<>();
		
		String query = "select ID,MATRICULA,MARCA,MODELO,COLOR from coches";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Coche coche = new Coche();
				coche.setId(rs.getInt(1));
				coche.setMatricula(rs.getString(2));
				coche.setMarca(rs.getString(3));
				coche.setModelo(rs.getString(4));
				coche.setColor(rs.getString(5));
				
				listaCoches.add(coche);
			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los "
					+ "coches");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return listaCoches;
	}

}
