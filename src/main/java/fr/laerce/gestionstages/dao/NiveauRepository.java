package fr.laerce.gestionstages.dao;

import fr.laerce.gestionstages.domain.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NiveauRepository extends JpaRepository <Niveau, Long> {

    public Niveau findByCode(String code);



}
