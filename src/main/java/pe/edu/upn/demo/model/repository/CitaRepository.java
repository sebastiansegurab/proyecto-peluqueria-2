package pe.edu.upn.demo.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upn.demo.model.entidades.Cita;
import pe.edu.upn.demo.model.entidades.Servicio;


@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer>{

	@Query(value="SELECT * FROM cita "
			+ "WHERE fecha_cita LIKE '2019-11%' "
			+ "ORDER BY cod_servicio", nativeQuery=true) 
	List<Cita> fecha();
	
	@Query(value="SELECT * FROM cita "
			+ "WHERE est_cita = 'NO' and cod_u = :cod_u",nativeQuery=true)
	List<Cita> noPagadas(@Param("cod_u") Long cod_u);
	
	@Query(value = "SELECT s.cod_servicio, s.nom_servicio, s.duracion_servicio, s.pre_servicio, s.imagen_servicio, "
			+ "COUNT(s.cod_servicio) AS cantidad"
			+ " FROM Cita c, Servicio s WHERE c.cod_servicio = s.cod_servicio "
			+ "GROUP BY s.cod_servicio, s.nom_servicio, s.duracion_servicio, s.pre_servicio, s.imagen_servicio "
			+ "ORDER BY cantidad DESC", nativeQuery = true)
	List<Servicio> ranking();
}
