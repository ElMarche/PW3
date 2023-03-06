package MarceloPelleriti.Album.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import MarceloPelleriti.Album.model.Figurita;
import MarceloPelleriti.Album.service.IEquiposService;
import MarceloPelleriti.Album.service.IFiguritasService;
import MarceloPelleriti.Album.util.Utileria;

@Controller
@RequestMapping("/figuritas")
public class FiguritasController {
	
	@Value("${albumapp.ruta.imagenes}")
	private String ruta;
	
	@Autowired
	private IFiguritasService serviceFiguritas;
	
	@Autowired
	private IEquiposService serviceEquipos;
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Figurita> lista = serviceFiguritas.buscarTodas(page);
		model.addAttribute("figuritas", lista);
		return "figuritas/listFiguritas";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idFigurita, RedirectAttributes attributes) {
		serviceFiguritas.eliminar(idFigurita);
		attributes.addFlashAttribute("msg", "Figurita Eliminada");
		System.out.println("Borrando figurita con id: " + idFigurita);
		return "redirect:/figuritas/indexPaginate";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idFigurita, Model model) {
		Figurita figurita = serviceFiguritas.buscarPorId(idFigurita);
		model.addAttribute("figurita", figurita);
		model.addAttribute("equipos", serviceEquipos.buscarTodos());
		return "figuritas/formFiguritas";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("equipos", serviceEquipos.buscarTodos());
	}
	
	
	@GetMapping("/create")
	public String crear(Figurita figurita, Model model) {
		model.addAttribute("equipos", serviceEquipos.buscarTodos());
		return "figuritas/formFiguritas";
	}
	
	@PostMapping("/save")
	public String guardar(Figurita figurita, BindingResult result, RedirectAttributes attributes,
			@RequestParam("archivoImagen") MultipartFile multiPart) {
		
		if(result.hasErrors()) {
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("Ocurrió un error: "+ error.getDefaultMessage());
			}
		return "figuritas/formFiguritas";
	}
		if(!multiPart.isEmpty()) {
			// Ruta configurada en application.properties
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if(nombreImagen!=null) {
				figurita.setImagen(nombreImagen);
			}
		}
		serviceFiguritas.guardar(figurita);
		attributes.addFlashAttribute("msg", "Registro Guardado");
		System.out.println("Figurita: "+ figurita);
		return "redirect:/figuritas/indexPaginate";
	}
	
	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idFigurita, Model model) {
		
		Figurita figurita = serviceFiguritas.buscarPorId(idFigurita);
		
		System.out.println("Figurita: " + figurita);
		model.addAttribute("figurita", figurita);
		return "figuritas/detalle";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		String workingDir = System.getProperty("user.dir");
		System.out.println("Directorio de trabajo: " + workingDir);
		List<Figurita> lista = serviceFiguritas.buscarTodas();
		model.addAttribute("figuritas", lista);
		return "figuritas/listFiguritas";
	}
	
	// Método para dar formato a la fecha
		@InitBinder
		public void initBinder(WebDataBinder webDataBinder) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		}

}
