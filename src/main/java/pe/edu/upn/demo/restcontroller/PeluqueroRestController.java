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

import pe.edu.upn.demo.model.entidades.Trabajador;
import pe.edu.upn.demo.service.TrabajadorService;

@RestController
@RequestMapping("/api/trabajador")
public class PeluqueroRestController {
	
	@Autowired
	private TrabajadorService trabajadorService;
	
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< List<Trabajador> > fetchTrabajador() {
		try {
			List<Trabajador> trabajador = trabajadorService.findAll();
			return new ResponseEntity<List<Trabajador>>(trabajador, HttpStatus.OK);   
		} catch (Exception e) {
			return new ResponseEntity<List<Trabajador>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< Trabajador > saveTrabajador(@RequestBody Trabajador trabajador) {
		try {
			Trabajador newTrabajador = trabajadorService.save(trabajador);
			return new ResponseEntity< Trabajador >(newTrabajador, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity< Trabajador >(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< Trabajador > updateTrabajador(@PathVariable("id") Integer id, 
			@RequestBody Trabajador trabajador) {
		try {
			if(id.equals(trabajador.getCodigo())) {
				Optional<Trabajador> optional = trabajadorService.findById(id);
				if(optional.isPresent()) {
					Trabajador updateTrabajador = trabajadorService.update(trabajador);
					return new ResponseEntity<Trabajador>(updateTrabajador, 
							HttpStatus.OK);
				} else {
					return new ResponseEntity<Trabajador>(HttpStatus.NOT_FOUND);
				}				
			} else {
				return new ResponseEntity<Trabajador>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Trabajador>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Trabajador> deleteTrabajador(@PathVariable("id") Integer id) {
		try {			
			Optional<Trabajador> optional = trabajadorService.findById(id);
			if(optional.isPresent()) {
				trabajadorService.deleteById(id);
				return new ResponseEntity<Trabajador>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Trabajador>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Trabajador>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	
	
}
















