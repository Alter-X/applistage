package fr.laerce.gestionstages.dao;

import fr.laerce.gestionstages.domain.Discipline;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DisciplineRepository extends CrudRepository <Discipline, Long> {

   public Discipline findByCode(String code);

}
