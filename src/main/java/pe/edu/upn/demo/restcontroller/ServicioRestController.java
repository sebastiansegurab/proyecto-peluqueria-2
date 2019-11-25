package pe.edu.upn.demo.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.upn.demo.model.entidades.Servicio;
import pe.edu.upn.demo.service.ServicioService;

@RestController
@RequestMapping("/api/servicio")
public class ServicioRestController {
	
	@Autowired
	private ServicioService servicioService;
	
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< List<Servicio> > fetchServicio() {
		try {
			List<Servicio> servicio = servicioService.findAll();
			return new ResponseEntity<List<Servicio>>(servicio, HttpStatus.OK);   
		} catch (Exception e) {
			return new ResponseEntity<List<Servicio>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< Servicio > saveServicio(@RequestBody Servicio servicio) {
		try {
			Servicio newServicio = servicioService.save(servicio);
			return new ResponseEntity< Servicio >(newServicio, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity< Servicio >(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< Servicio > updateServicio(@PathVariable("id") Integer id, 
			@RequestBody Servicio servicio) {
		try {
			if(id.equals(servicio.getCodigo())) {
				Optional<Servicio> optional = servicioService.findById(id);
				if(optional.isPresent()) {
					Servicio updateservicio = servicioService.update(servicio);
					return new ResponseEntity<Servicio>(updateservicio, 
							HttpStatus.OK);
				} else {
					return new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND);
				}				
			} else {
				return new ResponseEntity<Servicio>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Servicio>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Servicio> deleteServicio(@PathVariable("id") Integer id) {
		try {			
			Optional<Servicio> optional = servicioService.findById(id);
			if(optional.isPresent()) {
				servicioService.deleteById(id);
				return new ResponseEntity<Servicio>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Servicio>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	
	
}
















