package pe.edu.upn.demo.service;

import java.util.List;

import pe.edu.upn.demo.model.entidades.Servicio;

public interface ServicioService extends CrudService<Servicio, Integer> {
	List<Servicio> ranking() throws Exception;
}
