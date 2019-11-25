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

import pe.edu.upn.demo.model.entidades.Horario;
import pe.edu.upn.demo.service.HorarioService;

@RestController
@RequestMapping("/api/horario")
public class HorarioRestController {
	
	@Autowired
	private HorarioService horarioService;
	
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< List<Horario> > fetchHorario() {
		try {
			List<Horario> horario = horarioService.findAll();
			return new ResponseEntity<List<Horario>>(horario, HttpStatus.OK);   
		} catch (Exception e) {
			return new ResponseEntity<List<Horario>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< Horario > saveHorario(@RequestBody Horario horario) {
		try {
			Horario newHorario = horarioService.save(horario);
			return new ResponseEntity< Horario >(newHorario, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity< Horario >(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< Horario > updateHorario(@PathVariable("id") Integer id, 
			@RequestBody Horario horario) {
		try {
			if(id.equals(horario.getCodigo())) {
				Optional<Horario> optional = horarioService.findById(id);
				if(optional.isPresent()) {
					Horario updatehorario = horarioService.update(horario);
					return new ResponseEntity<Horario>(updatehorario, 
							HttpStatus.OK);
				} else {
					return new ResponseEntity<Horario>(HttpStatus.NOT_FOUND);
				}				
			} else {
				return new ResponseEntity<Horario>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Horario>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Horario> deleteHorario(@PathVariable("id") Integer id) {
		try {			
			Optional<Horario> optional = horarioService.findById(id);
			if(optional.isPresent()) {
				horarioService.deleteById(id);
				return new ResponseEntity<Horario>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Horario>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Horario>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	
	
}
















