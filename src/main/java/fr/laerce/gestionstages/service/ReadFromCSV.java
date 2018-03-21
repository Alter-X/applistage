package fr.laerce.gestionstages.service;

import com.opencsv.bean.CsvToBeanBuilder;
import fr.laerce.gestionstages.dao.CPostalRepository;
import fr.laerce.gestionstages.domain.CPostal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.zip.ZipInputStream;

@Service
public class ReadFromCSV {

    @Autowired
    CPostalRepository repoAdresse;
    //@Value("${communes.file") VOIR PHOTO M.VARNI

    public void run() {

        ZipInputStream zip = null;
        try {
            zip = new ZipInputStream(new FileInputStream(
                    "/home/bobby/Bureau/pfmp/src/main/java/fr/laerce/gestionstages/temp/laposte_hexasmal.csv.zip"));

            zip.getNextEntry();

            List<CPostal> beans = new CsvToBeanBuilder(new InputStreamReader(zip)).withSeparator(';').withType(CPostal.class).build().parse();


            for (CPostal CPostal : beans) {
                try {
//                    List<CPostal> adresses = repoAdresse.findByCodePostal(CPostal.getCodePostal());
//                    if (!adresses.contains(CPostal)) {
                       repoAdresse.save(CPostal);
//                    }
//                    else {
//                        // TODO si code commune existe déjà... à gérer (POUR LE STAGIAIRE !)
//
//
//                    }
                } catch (Exception e) {
                    System.out.println("CPostal import : " + CPostal);
                    //System.out.println("CPostal Database :" + repoAdresse.findByCodeCommuneAndLieuDit(CPostal.getCodeCommune(), CPostal.getLieuDit()));
                    break;
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zip != null) {
                    zip.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
