package MarceloPelleriti.Album.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MarceloPelleriti.Album.model.Equipo;

@Service
public class EquiposServiceImpl implements IEquiposService{
	
	private List<Equipo> lista = null;
	
	public EquiposServiceImpl() {
		
		lista = new LinkedList<Equipo>();
		
		Equipo equipo1 = new Equipo();
		Equipo equipo2 = new Equipo();
		
		equipo1.setId(1);
		equipo1.setNombre("Independiente");
		equipo1.setDescripcion("Rey de Copas");
		
		equipo2.setId(2);
		equipo2.setNombre("Racing Club");
		equipo2.setDescripcion("La Academia");
		
		lista.add(equipo1);
		lista.add(equipo2);
		
	}

	@Override
	public void guardar(Equipo equipo) {
		// TODO Auto-generated method stub
		int maxId = 0;
		for(Equipo e : lista) {
			if(e.getId()>maxId) {
				maxId=e.getId();
			}
		}
		equipo.setId(maxId+1);
		lista.add(equipo);
	}

	@Override
	public List<Equipo> buscarTodos() {
		// TODO Auto-generated method stub
		return lista;
	}

	@Override
	public Equipo buscarPorId(Integer idEquipo) {
		// TODO Auto-generated method stub
		for(Equipo e : lista) {
			if(e.getId()==idEquipo) {
				return e;
			}
		}
		return null;
	}

	@Override
	public void eliminar(Equipo equipo) {
		// TODO Auto-generated method stub
		lista.remove(equipo);
	}

	@Override
	public Page<Equipo> buscarTodos(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
