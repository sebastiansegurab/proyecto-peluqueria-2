package pe.edu.upn.demo.service;

import java.util.List;

import pe.edu.upn.demo.model.entidades.Trabajador;

public interface TrabajadorService extends CrudService<Trabajador, Integer> {
	List<Trabajador> ranking() throws Exception;
}
