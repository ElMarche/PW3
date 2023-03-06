package MarceloPelleriti.Album.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import MarceloPelleriti.Album.model.Figurita;

public interface FiguritasRepository extends JpaRepository<Figurita, Integer> {
	

}
