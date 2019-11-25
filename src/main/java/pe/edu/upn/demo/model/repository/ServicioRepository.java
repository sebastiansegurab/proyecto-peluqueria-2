package pe.edu.upn.demo.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upn.demo.model.entidades.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
	@Query(value = "SELECT s.cod_servicio, s.nom_servicio, s.duracion_servicio, s.pre_servicio, s.imagen_servicio, "
			+ "COUNT(s.cod_servicio) AS cantidad"
			+ " FROM Cita c, Servicio s WHERE c.cod_servicio = s.cod_servicio "
			+ "GROUP BY s.cod_servicio, s.nom_servicio, s.duracion_servicio, s.pre_servicio, s.imagen_servicio "
			+ "ORDER BY cantidad DESC", nativeQuery = true)
	List<Servicio> ranking();
}
