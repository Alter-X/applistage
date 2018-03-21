package fr.laerce.gestionstages.dao;

import fr.laerce.gestionstages.domain.Individu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndividuRepository extends JpaRepository <Individu, Long> {

    List<Individu> findAllByOrderByIdAsc();
    public Individu findByCodeSynchro(String code);
    public int countAllByLogin(String login);
    public Individu findByLogin (String login);

}
