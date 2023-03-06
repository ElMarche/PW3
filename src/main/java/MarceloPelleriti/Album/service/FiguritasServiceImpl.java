package MarceloPelleriti.Album.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import MarceloPelleriti.Album.model.Figurita;

@Service
public class FiguritasServiceImpl implements IFiguritasService{
	
	private List<Figurita> lista = null;
	
	public FiguritasServiceImpl() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		lista = new LinkedList<Figurita>();
		
		try {
			Figurita figurita1 = new Figurita();
			Figurita figurita2 = new Figurita();
			Figurita figurita3 = new Figurita();
			Figurita figurita4 = new Figurita();
			Figurita figurita5 = new Figurita();
			
			figurita1.setId(1);
			figurita1.setAltura(1.83);
			figurita1.setDescripcion("Sin descripción");
			figurita1.setFechaNacimiento(sdf.parse("19-08-1986"));
			figurita1.setNombre("Sebastián Sosa");
			figurita1.setPuesto("Arquero");
			figurita1.setImagen("1_-_sosa.jpg");
			
			figurita2.setId(2);
			figurita2.setAltura(1.69);
			figurita2.setDescripcion("Sin descripción");
			figurita2.setFechaNacimiento(sdf.parse("27-08-1993"));
			figurita2.setNombre("Lucas Rodríguez");
			figurita2.setPuesto("Defensor");
			figurita2.setImagen("5_-_rodríguez.jpg");
			
			figurita3.setId(3);
			figurita3.setAltura(1.67);
			figurita3.setDescripcion("Sin descripción");
			figurita3.setFechaNacimiento(sdf.parse("18-04-1994"));
			figurita3.setNombre("Lucas Romero");
			figurita3.setPuesto("Volante");
			figurita3.setImagen("44_-_romero.jpg");
			
			figurita4.setId(4);
			figurita4.setAltura(1.76);
			figurita4.setDescripcion("Sin descripción");
			figurita4.setFechaNacimiento(sdf.parse("24-06-1996"));
			figurita4.setNombre("Damián Batallini");
			figurita4.setPuesto("Volante");
			figurita4.setImagen("8_-_batallini-01.jpg");
			
			figurita5.setId(5);
			figurita5.setAltura(1.71);
			figurita5.setDescripcion("Sin descripción");
			figurita5.setFechaNacimiento(sdf.parse("03-08-1998"));
			figurita5.setNombre("Alan Soñora");
			figurita5.setPuesto("Volante");
			figurita5.setImagen("15_-_soñora.jpg");
			
			lista.add(figurita1);
			lista.add(figurita2);
			lista.add(figurita3);
			lista.add(figurita4);
			lista.add(figurita5);
			
		}catch(ParseException e){
			// este catch para la excepción del parse (fechas)
			System.out.println("Error: "+ e.getMessage());
		}
		
	}

	@Override
	public List<Figurita> buscarTodas() {
		// Devuelve la lista creada en el constructor.
		return lista;
	}

	@Override
	public Figurita buscarPorId(Integer idFigurita) {
		// TODO Auto-generated method stub
		for(Figurita f: lista) {
			if(f.getId()==idFigurita) {
				return f;
			}
			
		}
		
		return null;
	}

	@Override
	public void guardar(Figurita figurita) {
		// TODO Auto-generated method stub
		int maxId = 0;
		for(Figurita f : lista) {
			if(f.getId()>maxId) {
				maxId = f.getId();
			}
		}
		figurita.setId(maxId+1);
		lista.add(figurita);
	}

	@Override
	public void eliminar(Integer idFigurita) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Figurita> buscarByExample(Example<Figurita> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Figurita> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

}
