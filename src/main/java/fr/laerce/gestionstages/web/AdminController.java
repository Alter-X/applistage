package fr.laerce.gestionstages.web;

import fr.laerce.gestionstages.dao.*;
import fr.laerce.gestionstages.service.ImportFromSTS;
import fr.laerce.gestionstages.service.ReadFromCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class AdminController {

    @Autowired
    ReadFromCSV rfcsv;

    @Autowired
    ImportFromSTS importFromSTS;

    @Autowired
    DisciplineRepository repoDiscipline;

    @Autowired
    DivisionRepository repoDivision;

    @Autowired
    IndividuRepository repoIndividu;

    @Autowired
    NiveauRepository repoNiveau;

    @Value("${gestionstages.sts.file}")
    private String stsFileName;

    private static String UPLOADED_FOLDER = "/home/bobby/Bureau/pfmp/src/main/java/fr/laerce/gestionstages/temp/";

    // on affiche le nombre de tuples dans chaque entité, sans remettre à jour la base de données
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("niveaux",     repoNiveau.count());
        model.addAttribute("disciplines", repoDiscipline.count());
        model.addAttribute("divisions",   repoDivision.count());
        model.addAttribute("individus",   repoIndividu.count());
        return "index";
    }

    @GetMapping("/index/update")
    public String populate(Model model) {
        // on met à jour la base de données PFMP avec le fichier XML 'sts_emp_0940321S_2017.xml'
        importFromSTS.parseAndImport(stsFileName);

        model.addAttribute("niveaux",     importFromSTS.getDicoNiveaux().size());
        model.addAttribute("disciplines", importFromSTS.getDicoDisciplines().size());
        model.addAttribute("divisions",   importFromSTS.getDicoDivisions().size());
        model.addAttribute("individus",   importFromSTS.getDicoFormateurs().size());
        return "index";
    }

    // méthode appelée pour contrôler ce qui est envoyé sur la page de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // méthode appelée pour contrôler ce qui est envoyé sur la page d'erreur d'authentification
    @GetMapping("/login-error")
    public String loginError() {
        return "login-error";
    }

    // méthode appelée pour contrôler ce qui est envoyé sur la page de non autorisation d'accès
    @GetMapping("/error")
    public String loginUnauthorized() {
        return "login-unauthorized";
    }

    @GetMapping("/logout")
    public String logout() throws ServletException {
        return "logout";
    }

    @PostMapping("/login-success")
    public String loginSuccess() {
        return "redirect:/index";
    }

    // Insertion TEST "UPLOAD" - Cours Capuozzo
    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Selectionnez votre fichier à téléverser : ");
            return "redirect:uploadStatus";
        }
        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "Féliciation ! Votre fichier a bien été téléversé ;-) '" + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    @GetMapping("/commune")
    public String adresseCSV() {
        rfcsv.run();
        return "redirect:uploadStatus";
    }
}
