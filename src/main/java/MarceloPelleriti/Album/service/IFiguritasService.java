package MarceloPelleriti.Album.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import MarceloPelleriti.Album.model.Figurita;

public interface IFiguritasService {
	List<Figurita> buscarTodas();
	Figurita buscarPorId(Integer idFigurita);
	void guardar(Figurita figurita);
	void eliminar(Integer idFigurita);
	List<Figurita> buscarByExample(Example<Figurita> example);
	Page<Figurita> buscarTodas(Pageable page);
}
