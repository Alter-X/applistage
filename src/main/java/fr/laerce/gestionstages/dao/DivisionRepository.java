package fr.laerce.gestionstages.dao;

import fr.laerce.gestionstages.domain.Division;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DivisionRepository extends JpaRepository <Division, Long> {

    public Division findByCode(String code);

}
