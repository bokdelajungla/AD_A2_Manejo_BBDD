package com.requerimiento1.modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.requerimiento1.modelo.entidad.Coche;
import com.requerimiento1.modelo.persistencia.interfaz.DaoCoche;

public class DaoCocheMySql implements DaoCoche {

    private Connection con;

    public boolean openCon() {
        String url ="jdbc:mysql://localhost:3306/listacoches";
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

	public boolean add(Coche c) {
		if(!openCon()) {
			return false;
		}
		boolean add = true;
		
		String query = "insert into coches(MATRICULA,MARCA,MODELO,COLOR) values(?,?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, c.getMatricula());
			ps.setString(2, c.getMarca());
			ps.setString(3, c.getModelo());
			ps.setString(4, c.getColor());
			
			int numFilasAfectadas = ps.executeUpdate();
			if(numFilasAfectadas == 0) {
				add = false;
			}
		} catch(SQLException e) {
			System.out.println("add -> Error al insertar el coche: " + c.toString());
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
		
		String query = "delete from coches where ID=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			
			int numFilasAfectadas = ps.executeUpdate();
			if(numFilasAfectadas == 0) {
				del = false;
			}
		} catch(SQLException e) {
			System.out.println("del -> Error al dar de baja el coche con id : " + id);
			del = false;
			e.printStackTrace();
		} finally {
			closeCon();
		}
		return del;
	}

	public Coche get(int id) {
		if(!openCon()) {
			return null;
		}
		Coche coche = null;
		
		String query = "select * from coches where ID=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				coche = new Coche();
				coche.setId(rs.getInt(1));
				coche.setMatricula(rs.getString(2));
				coche.setMarca(rs.getString(3));
				coche.setModelo(rs.getString(4));
				coche.setColor(rs.getString(5));
			}
		} catch(SQLException e) {
			System.out.println("get -> Error al obtener el coche con id: " + id);
			coche = null;
			e.printStackTrace();
		} finally {
			closeCon();
		}
		return coche;
	}
	
	public boolean update(int id) {
		if(!openCon()) {
			return false;
		}
		Coche coche = null;
		boolean update = true;
		
		String query = "select * from coches where ID=?";
		
		// Debido a la transacionalidad ejecutamos todas las queries como si fueran una única
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				coche  = new Coche();
				coche.setId(rs.getInt(1));
				coche.setMatricula(rs.getString(2));
				coche.setMarca(rs.getString(3));
				coche.setModelo(rs.getString(4));
				coche.setColor(rs.getString(5));
			}
			query = "update coches set MATRICULA=?, MARCA=?, MODELO=?, COLOR=? where ID=?";
			
			ps = con.prepareStatement(query);
			ps.setString(1, coche.getMatricula());
			ps.setString(2, coche.getMarca());
			ps.setString(3, coche.getModelo());
			ps.setString(4, coche.getColor());
			
			int numFilasAfectadas = ps.executeUpdate();
			if(numFilasAfectadas == 0) {
				update = false;
			}
		} catch(SQLException e) {
			System.out.println("add -> Error al modificar el coche: " + coche.toString());
			update = false;
			e.printStackTrace();
		} finally {
			closeCon();
		}
		
		return update;
	}

	public List<Coche> list() {
		if(!openCon()) {
			return null;
		}
		List<Coche> listaCoches = new ArrayList<Coche>();
		
		String query = "select * from coches";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Coche coche = new Coche();
				coche = new Coche();
				coche.setId(rs.getInt(1));
				coche.setMatricula(rs.getString(2));
				coche.setMarca(rs.getString(3));
				coche.setModelo(rs.getString(4));
				coche.setColor(rs.getString(5));
				
				listaCoches.add(coche);
			}
		} catch(SQLException e) {
			System.out.println("get -> Error al obtener los coches");
			listaCoches = null;
			e.printStackTrace();
		} finally {
			closeCon();
		}
		return listaCoches;
	}
}
