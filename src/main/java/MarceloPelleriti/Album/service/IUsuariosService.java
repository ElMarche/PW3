package MarceloPelleriti.Album.service;

import java.util.List;

import MarceloPelleriti.Album.model.Usuario;

public interface IUsuariosService {
	void guardar(Usuario usuario);
	
	void eliminar(Integer idUsuario);
	
	List<Usuario> buscarTodos();
	
	Usuario buscarPorUsername(String username);

}
