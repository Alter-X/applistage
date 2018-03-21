package fr.laerce.gestionstages.configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordApp {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
        System.out.println(encoder.encode("password"));
    }

}
