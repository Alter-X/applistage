package fr.laerce.gestionstages.configuration;

// cette classe se trouve dans le dossier 'configuration', un dossier permettant de stocker toutes les classes
// permettant de configurer l'application, notamment au niveau de la sécurité

import fr.laerce.gestionstages.service.UserAuthJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // méthode abstraite
    // l'objet HttpSecurity définit un DSL pour définir des paramètres d'authentification et d'autorisation à des utilisateurs,
    // en fonction des URLs vers lequels les utilisateurs peuvent se rendre dans l'application
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // DSL sur l'autorisation qui va à partir de l'objet HttpSecurity autoriser des types d'utilisateurs
                // à accéder à certains URLs
                .authorizeRequests()

                // Ant : système de construction d'application Java qui ne gère que la partie Build d'une application
                // /webjars/** : définit tout ce qui se trouve dans les URLs commençant par '/webjars'
                .antMatchers("/webjars/**","/index","/testpassword", "/", "/static/**").permitAll()

                // la méthode 'hasAuthority' définit un droit d'accès à un seul type d'utilisateur,
                // alors que la méthode 'hasAnyAuthority' définit un droit d'accès à plusieurs types d'utilisateur
                .antMatchers("/crud/**").hasAnyAuthority("USER, ADMIN")
                .antMatchers("/index/update").hasAuthority("ADMIN")
                .antMatchers("/upload").hasAuthority("ADMIN")
                .antMatchers("/adresse").hasAuthority("ADMIN")
                .antMatchers("/cpvilles").permitAll()
//                .antMatchers("/testupload").hasAuthority("ADMIN")


                // on appelle la méthode 'and()' pour rattacher aux autorisations, les appels de méthode
                // formLogin() et loginPage(), formLogin() devant être appelé en premier pour spécifier ensuite
                // l'URL à rattacher au controller permettant d'afficher la page d'authentification
                .and()

                .rememberMe()
                .rememberMeCookieName("Alex_Super_Cookie")
                .tokenValiditySeconds(7 * 24 * 60 * 60)
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userAuthJPA)

                .and()

                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies()
                .logoutSuccessUrl("/index")

                .and()

                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .successForwardUrl("/login-success");

//                // on définit une redirection vers une page de login à l'application avec l'URL '/login',
//                // cette page devant être récupéré par un Controller avec un @GetMapping,
//                // le @PostMapping étant géré par Spring Security
//                // on définit aussi une redirection vers une page d'erreur '/login-error' lorsque
//                // l'authentification échoue
//                .loginPage("/login")
//                .failureUrl("/login-error");
//
//        http.logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//                .logoutSuccessUrl("/index");

//        http
//                .rememberMe()
//                .rememberMeCookieName("ALEX-TEST-remember-me")
//                .token


    }

    @Autowired
    UserAuthJPA userAuthJPA;

    // l'objet AuthentificationManagerBuilder va être chargé de dire à Spring comment authentifier les utilisateurs,
    // et comment on récupère les authentifications associés à ces utilisateurs
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userAuthJPA);
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("{noop}secret")
//                .authorities("ADMIN");
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthJPA);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean // @Bean est un composant, c'est une classe de base
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(11);
    }
}
