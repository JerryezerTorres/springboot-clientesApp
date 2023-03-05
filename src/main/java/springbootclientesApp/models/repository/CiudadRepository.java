package springbootclientesApp.models.repository;

import org.springframework.data.repository.CrudRepository;

import springbootclientesApp.models.entity.Ciudad;

public interface CiudadRepository extends CrudRepository<Ciudad, Long> {
	
	

}
