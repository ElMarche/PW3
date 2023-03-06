package MarceloPelleriti.Album.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MarceloPelleriti.Album.model.Figurita;
import MarceloPelleriti.Album.repository.FiguritasRepository;
import MarceloPelleriti.Album.service.IFiguritasService;

@Service
@Primary
public class FiguritasServiceJpa implements IFiguritasService{
	
	@Autowired
	private FiguritasRepository figuritasRepo;

	@Override
	public List<Figurita> buscarTodas() {
		return figuritasRepo.findAll();
	}

	@Override
	public Figurita buscarPorId(Integer idFigurita) {
		Optional<Figurita> optional = figuritasRepo.findById(idFigurita);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardar(Figurita figurita) {
		figuritasRepo.save(figurita);
	}

	@Override
	public void eliminar(Integer idFigurita) {
		figuritasRepo.deleteById(idFigurita);
	}

	@Override
	public List<Figurita> buscarByExample(Example<Figurita> example) {
		return figuritasRepo.findAll(example);
	}

	@Override
	public Page<Figurita> buscarTodas(Pageable page) {
		return figuritasRepo.findAll(page);
	}

}
