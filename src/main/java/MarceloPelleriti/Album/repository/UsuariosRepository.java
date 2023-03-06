package MarceloPelleriti.Album.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import MarceloPelleriti.Album.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
	
	Usuario findByUsername(String username);

}
