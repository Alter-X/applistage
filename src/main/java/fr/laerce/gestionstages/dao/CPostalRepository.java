package fr.laerce.gestionstages.dao;

import fr.laerce.gestionstages.domain.CPostal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "adresse", path = "adresse")
public interface CPostalRepository extends CrudRepository<CPostal, Long> {

    public List<CPostal> findByCodePostal (String codePostal);
    public List<CPostal> findByCodePostalStartsWith (String codePostal);

}
