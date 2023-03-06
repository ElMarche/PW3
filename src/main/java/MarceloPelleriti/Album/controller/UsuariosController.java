package MarceloPelleriti.Album.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import MarceloPelleriti.Album.model.Usuario;
import MarceloPelleriti.Album.service.IUsuariosService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model, Authentication auth) {
		List<Usuario> lista = serviceUsuarios.buscarTodos();
		model.addAttribute("usuarios", lista);
		String currentusername = auth.getName();
		model.addAttribute("currentusername", currentusername);
		return "/usuarios/listUsuarios";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {
		serviceUsuarios.eliminar(idUsuario);
		attributes.addFlashAttribute("msg", "Usuario Eliminada");
		System.out.println("Borrando usuario con id: " + idUsuario);
		return "redirect:/usuarios/index";
	
	}

}
