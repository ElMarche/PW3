package MarceloPelleriti.Album.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MarceloPelleriti.Album.model.Equipo;
import MarceloPelleriti.Album.repository.EquiposRepository;
import MarceloPelleriti.Album.service.IEquiposService;

@Service
@Primary
public class EquiposServiceJpa implements IEquiposService {
	
	@Autowired
	private EquiposRepository equiposRepo;

	@Override
	public void guardar(Equipo equipo) {
		equiposRepo.save(equipo);
		
	}

	@Override
	public List<Equipo> buscarTodos() {
		return equiposRepo.findAll();
	}

	@Override
	public Equipo buscarPorId(Integer idEquipo) {
		Optional<Equipo> optional = equiposRepo.findById(idEquipo);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void eliminar(Equipo equipo) {
		equiposRepo.delete(equipo);
		
	}

	@Override
	public Page<Equipo> buscarTodos(Pageable page) {
		return equiposRepo.findAll(page);
	}

}
