package fr.laerce.gestionstages.dao;

import fr.laerce.gestionstages.domain.Formateur;
import fr.laerce.gestionstages.domain.Individu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormateurRepository extends JpaRepository <Formateur, Long> {

    public Formateur findByCodeSynchro(String code);

}
