package fr.laerce.gestionstages.service;

import fr.laerce.gestionstages.dao.IndividuRepository;
import fr.laerce.gestionstages.domain.Individu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//UserDetailsService.jpa = nom de la classe pour le prof

@Service
public class UserAuthJPA implements UserDetailsService {

    @Autowired
    IndividuRepository repoIndividu;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Individu individu = repoIndividu.findByLogin(login);
        User user = null;

        if (individu != null) {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            String [] autorisations = individu.getRole().split(",");

            for (int i =0; i<autorisations.length;i++) {
                authorities.add(new SimpleGrantedAuthority(autorisations[i]));
            }
            user = new User(login, individu.getMdp(),authorities);
        }
        else {
            throw new UsernameNotFoundException("Utilisateur non trouvé :"+login);
        }
        return user;
    }
}
