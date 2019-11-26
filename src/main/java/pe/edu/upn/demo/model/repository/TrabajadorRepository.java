package pe.edu.upn.demo.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upn.demo.model.entidades.Servicio;
import pe.edu.upn.demo.model.entidades.Trabajador;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer>{
	@Query(value = "SELECT p.cod_pe, p.nom_pe, p.dni_pe, p.sexo_pe , p.telf_pe, p.dir_pe, "
			+ "COUNT(p.cod_pe) AS cantidad"
			+ " FROM Cita c, Peluquero p WHERE c.cod_pe = p.cod_pe "
			+ "GROUP BY p.cod_pe, p.nom_pe, p.dni_pe, p.sexo_pe , p.telf_pe, p.dir_pe "
			+ "ORDER BY cantidad DESC", nativeQuery = true)
	List<Trabajador> ranking();
}
