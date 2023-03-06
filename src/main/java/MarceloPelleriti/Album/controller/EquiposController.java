package MarceloPelleriti.Album.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import MarceloPelleriti.Album.model.Equipo;
import MarceloPelleriti.Album.model.Figurita;
import MarceloPelleriti.Album.service.IEquiposService;

@Controller
@RequestMapping("/equipos")
public class EquiposController {
	
	@Autowired
	private IEquiposService serviceEquipos;
	
	/*
	@RequestMapping("/index")
	public String mostrarIndex(Model model) {
		List<Equipo> lista = serviceEquipos.buscarTodos();
		model.addAttribute("equipos", lista);
		return "equipos/listEquipos";
	}
	*/
	
	@GetMapping("/create")
	public String crear(Equipo equipo) {
		return "equipos/formEquipos";
	}
	
	@PostMapping("/save")
	public String guardar(Equipo equipo, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()){		
			System.out.println("Existieron errores");
			return "equipos/formEquipos";
		}	
		
		// Guadamos el objeto categoria en la bd
		serviceEquipos.guardar(equipo);
		attributes.addFlashAttribute("msg", "Los datos del equipo fueron guardados!");		
		return "redirect:/equipos/indexPaginate";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idEquipo, RedirectAttributes attributes) {
		System.out.println("Borrando Equipo con id: " + idEquipo);
		Equipo equipo = serviceEquipos.buscarPorId(idEquipo);
		serviceEquipos.eliminar(equipo);
		attributes.addFlashAttribute("msg", "El equipo fué eliminado");
		return "redirect:/equipos/indexPaginate";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idEquipo, Model model) {
		Equipo equipo = serviceEquipos.buscarPorId(idEquipo);
		model.addAttribute("equipo", equipo);
		model.addAttribute("equipos", serviceEquipos.buscarTodos());
		return "equipos/formEquipos";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Equipo> lista = serviceEquipos.buscarTodos(page);
		model.addAttribute("equipos", lista);
		return "equipos/listEquipos";
	}

}
