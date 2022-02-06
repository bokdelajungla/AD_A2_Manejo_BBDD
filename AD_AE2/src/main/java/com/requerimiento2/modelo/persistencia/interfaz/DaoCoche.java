package com.requerimiento2.modelo.persistencia.interfaz;

import java.util.List;

import com.requerimiento2.modelo.entidad.Coche;

//define un CRUD (Create-Read-Update-Delete) para el objeto Coche

public interface DaoCoche {
    public boolean add(Coche c);
    public boolean del(int id);
    public Coche get(int id);
    public boolean update(int id);
    public List<Coche> list();

}
