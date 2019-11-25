package pe.edu.upn.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upn.demo.model.entidades.Cita;
import pe.edu.upn.demo.model.entidades.Usuario;
import pe.edu.upn.demo.service.CitaService;
import pe.edu.upn.demo.service.UsuarioService;
import pe.edu.upn.demo.service.impl.NotificacionServiceImpl;

@Controller
@RequestMapping("/usuario")
@SessionAttributes({ "usuario", "cita" })
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CitaService citaService;

	@Autowired
	NotificacionServiceImpl notificacionServiceImpl;

	@GetMapping
	public String inicio(Model model) {
		try {
			List<Usuario> usuario = usuarioService.findAll();
			model.addAttribute("usuarios", usuario);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "usuario/inicio";
	}

	@GetMapping("login")
	public String login() {
		// TODO: handle exception
		return "usuario/login";
	}

	@GetMapping("register")
	public String register(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "usuario/register";
	}
	
	@GetMapping("editar/{id}")
	public String editar(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			Optional<Usuario> optional = usuarioService.findByUsername(username);
			if (optional.isPresent()) {
				model.addAttribute("usuario", optional.get());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "usuario/editar";
	}

	@GetMapping("carrito")
	public String carrito(Model model) {
		// Para obtener el username
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			Optional<Usuario> optional = usuarioService.findByUsername(username);
			if (optional.isPresent()) {
				List<Cita> citasNoPagadas = citaService.noPagadas(optional.get().getId());
				model.addAttribute("cita", citasNoPagadas);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "usuario/carrito";
	}

	@GetMapping("citas")
	public String citas(Model model) {
		// Para obtener el username
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			Optional<Usuario> optional = usuarioService.findByUsername(username);
			if (optional.isPresent()) {
				model.addAttribute("usuario", optional.get()); // muestra usuario
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "usuario/citas";
	}

	@GetMapping("perfil")
	public String PerId(Model model, Cita cita) {
		// Para obtener el username
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			Optional<Usuario> optional = usuarioService.findByUsername(username);
			if (optional.isPresent()) {
				model.addAttribute("usuario", optional.get());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "usuario/perfil";

	}

	@GetMapping("delu/{id}")
	public String eliminar(@PathVariable("id") int id, Model model) {
		try {
			Optional<Cita> cita = citaService.findById(id);
			if (cita.isPresent()) {
				citaService.deleteById(id);
			}
		} catch (Exception e) {

			model.addAttribute("dangerDel", "ERROR - Violación contra el principio de Integridad referencia");
			try {
				List<Cita> cita = citaService.findAll();
				model.addAttribute("cita", cita);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			return "usuario";
		}
		return "redirect:/usuario/verid";
	}

	@PostMapping("save")
	public String save(@ModelAttribute("usuario") Usuario usuario, Model model, SessionStatus status) {

		try {
			// Verificar que el username ya exista.
			Optional<Usuario> optional = usuarioService.findByUsername(usuario.getUsername());
			if (optional.isPresent()) {
				model.addAttribute("dangerRegister", "ERROR - El username " + usuario.getUsername() + " ya existe ");
				return "/usuario/register";
			} else {
				usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
				usuario.addAuthority("ROLE_CLIENTE");
				
				usuarioService.save(usuario);
				notificacionServiceImpl.sendNotificacionRegistro(usuario);
				status.setComplete();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/usuario/login";
	}
	
	@PostMapping("/editarsave")
	public String saveEditar(@ModelAttribute("usuario") Usuario usuario, Model model, SessionStatus status) {

		try {
			// Verificar que el username ya exista.
			Optional<Usuario> optional = usuarioService.findByUsername(usuario.getUsername());
			if (optional.isPresent()) {
				usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
				usuarioService.save(usuario);
				status.setComplete();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/usuario/login";
	}

}
