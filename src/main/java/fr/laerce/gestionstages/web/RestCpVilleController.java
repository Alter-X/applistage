package fr.laerce.gestionstages.web;

import fr.laerce.gestionstages.dao.CPostalRepository;
import fr.laerce.gestionstages.domain.CPostal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestCpVilleController {
    @Autowired
    CPostalRepository cPostalRepository;

    @GetMapping("/cpvilles")
    public List<CPostal> cpostal(@RequestParam(value="cp", defaultValue="77400") String cp) {
        return cPostalRepository.findByCodePostalStartsWith(cp);
    }
}
