package MarceloPelleriti.Album.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import MarceloPelleriti.Album.model.Equipo;

public interface IEquiposService {
	void guardar(Equipo equipo);
	List<Equipo> buscarTodos();
	Equipo buscarPorId(Integer idEquipo);
	void eliminar(Equipo equipo);
	Page<Equipo> buscarTodos(Pageable page);

}
