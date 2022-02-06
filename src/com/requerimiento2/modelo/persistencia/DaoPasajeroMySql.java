package com.requerimiento2.modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.requerimiento2.modelo.entidad.Pasajero;
import com.requerimiento2.modelo.persistencia.interfaz.DaoPasajero;

public class DaoPasajeroMySql implements DaoPasajero {

	private Connection con;

    public boolean openCon() {
        String url ="jdbc:mysql://localhost:3306/concesionario";
        String user = "root";
        String pass = "";
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean closeCon() {
        try {
            con.close();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

	
	public boolean add(Pasajero p) {
		if(!openCon()) {
			return false;
		}
		boolean add = true;
		
		String query = "insert into pasajeros(NOMBRE,EDAD,PESO) values(?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, p.getNombre());
			ps.setInt(2, p.getEdad());
			ps.setInt(3, p.getPeso());
			
			int numFilasAfectadas = ps.executeUpdate();
			if(numFilasAfectadas == 0) {
				add = false;
			}
		} catch(SQLException e) {
			System.out.println("add -> Error al insertar el pasajero: " + p.toString());
			add = false;
			e.printStackTrace();
		} finally {
			closeCon();
		}
		return add;
	}

	public boolean del(int id) {
		if(!openCon()) {
			return false;
		}
		boolean del = true;
		
		String query = "delete from pasajeros where ID=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			
			int numFilasAfectadas = ps.executeUpdate();
			if(numFilasAfectadas == 0) {
				del = false;
			}
		} catch(SQLException e) {
			System.out.println("del -> Error al dar de baja el pasajero con id : " + id);
			del = false;
			e.printStackTrace();
		} finally {
			closeCon();
		}
		return del;
	}

	public Pasajero get(int id) {
		if(!openCon()) {
			return null;
		}
		Pasajero pasajero = null;
		
		String query = "select * from pasajeros where ID=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				pasajero = new Pasajero();
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getInt(4));
			}
		} catch(SQLException e) {
			System.out.println("get -> Error al obtener el pasajero con id: " + id);
			pasajero = null;
			e.printStackTrace();
		} finally {
			closeCon();
		}
		return pasajero;
	}

	public List<Pasajero> list() {
		if(!openCon()) {
			return null;
		}
		List<Pasajero> listaPasajeros = new ArrayList<Pasajero>();
		
		String query = "select * from pasajeros";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Pasajero pasajero = new Pasajero();
				pasajero = new Pasajero();
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getInt(4));
				
				listaPasajeros.add(pasajero);
			}
		} catch(SQLException e) {
			System.out.println("get -> Error al obtener los pasajeros");
			listaPasajeros = null;
			e.printStackTrace();
		} finally {
			closeCon();
		}
		return listaPasajeros;
	}

	public boolean addToCar(int idPass, int idCar) {
		if(!openCon()) {
			return false;
		}
		boolean addToCar = true;
		
		String query = "update pasajeros set COCHE where ID=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idCar);
			ps.setInt(2, idPass);
				
			int numFilasAfectadas = ps.executeUpdate();
			if(numFilasAfectadas == 0) {
				addToCar = false;
			}
		} catch(SQLException e) {
			System.out.println("add -> Error al añadir el pasajero con id: " + idPass + 
					"al coche con id: " + idCar);
			addToCar = false;
			e.printStackTrace();
		} finally {
			closeCon();
		}
		
		return addToCar;
	}

	public boolean delFromCar(int idPass, int idCar) {
		if(!openCon()) {
			return false;
		}
		boolean delFromCar = true;
		
		String query = "delete COCHE from pasajeros where ID=? AND COCHE=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idPass);
			ps.setInt(2, idCar);
			
			int numFilasAfectadas = ps.executeUpdate();
			if(numFilasAfectadas == 0) {
				delFromCar = false;
			}
		} catch(SQLException e) {
			System.out.println("del -> Error al dar de baja el pasajero con id : " + idPass 
					+ "del coche con id " + idCar);
			delFromCar = false;
			e.printStackTrace();
		} finally {
			closeCon();
		}
		return delFromCar;
	}

	public List<Pasajero> listFromCar(int id) {
		if(!openCon()) {
			return null;
		}
		List<Pasajero> listFromCar = new ArrayList<Pasajero>();
		
		String query = "select * from pasajeros where CAR=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Pasajero pasajero = new Pasajero();
				pasajero = new Pasajero();
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getInt(4));
				
				listFromCar.add(pasajero);
			}
		} catch(SQLException e) {
			System.out.println("get -> Error al obtener los pasajeros del coche con id " + id);
			listFromCar = null;
			e.printStackTrace();
		} finally {
			closeCon();
		}
		return listFromCar;
	}

}
