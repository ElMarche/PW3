package MarceloPelleriti.Album.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import MarceloPelleriti.Album.model.Perfil;

import MarceloPelleriti.Album.model.Figurita;
import MarceloPelleriti.Album.model.Usuario;
import MarceloPelleriti.Album.service.IEquiposService;
import MarceloPelleriti.Album.service.IFiguritasService;
import MarceloPelleriti.Album.service.IUsuariosService;

@Controller
public class HomeController {
	
	@Autowired
	private IEquiposService serviceEquipos;
	
	@Autowired
	private IFiguritasService serviceFiguritas;
	
	@Autowired 
	private IUsuariosService serviceUsuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String mostrarHome(Model model){
		
		//List<Figurita> lista = serviceFiguritas.buscarTodas();
		//model.addAttribute("figuritas", lista);
		return "home";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		String username = auth.getName();
		System.out.println("Nombre del usuario: " + username);
		
		for (GrantedAuthority rol: auth.getAuthorities()) {
			System.out.println("ROL: " + rol.getAuthority());
		}
		
		if(session.getAttribute("usuario") == null) {
			Usuario usuario = serviceUsuarios.buscarPorUsername(username);
			usuario.setPassword(null);
			System.out.println("Usuario: " + usuario);
			session.setAttribute("usuario", usuario);
		}
		
		return "redirect:/";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		Figurita figuritaSearch = new Figurita();
		figuritaSearch.reset();
		model.addAttribute("figuritas", serviceFiguritas.buscarTodas());
		model.addAttribute("equipos", serviceEquipos.buscarTodos());
		model.addAttribute("search", figuritaSearch);
	}
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Figurita figurita, Model model) {
		System.out.println("Buscando por: " + figurita);
		
		ExampleMatcher matcher;
		
			matcher = ExampleMatcher.
					// where description like '%?%'
					matching().withMatcher("nombre", ExampleMatcher.GenericPropertyMatchers.contains());
		
		
		Example<Figurita> example = Example.of(figurita, matcher);
		List<Figurita> 	lista = serviceFiguritas.buscarByExample(example);
		model.addAttribute("figuritas", lista);
		return "home";
	}
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Figurita> lista = serviceFiguritas.buscarTodas();
		model.addAttribute("figuritas", lista);
		
		return "tabla";
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("Ocurrió un error: "+ error.getDefaultMessage());
			}
			
			return "formRegistro";
		}
		
		// falta password encriptada
		String pwdPlano = usuario.getPassword();
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);
		usuario.setPassword(pwdEncriptado);
		usuario.setEstatus(1); // Activado por defecto
		usuario.setFechaRegistro(new Date());
		Perfil perfilTemp = new Perfil();
		perfilTemp.setId(3); // Perfil USUARIO
		usuario.agregar(perfilTemp);
		serviceUsuarios.guardar(usuario);
		attributes.addFlashAttribute("msg", "Registro guardado");
		System.out.println("Guardando usuario: " + usuario);
		
		return "redirect:/index";
	}
	
	@GetMapping("/login")
	public String mostrarLogin() {
		return "formLogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/login";
	}
	
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto) {
		return texto + " Encriptado en BCrypt: " + passwordEncoder.encode(texto);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		/*
		 * InitBinder para Strings si los detecta vacíos en el Data Binding los setea a NULL
		 * @param Binder
		 */
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

}
