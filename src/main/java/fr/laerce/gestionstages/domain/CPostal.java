package fr.laerce.gestionstages.domain;

import com.opencsv.bean.CsvBindByName;
import javax.persistence.*;
import javax.xml.soap.Name;
import java.util.Objects;

@Entity
//@Table(uniqueConstraints={@UniqueConstraint(columnNames={"code_commune","lieu_dit"})}) CONTRAINTE D'UNICITE

@Table(name="adresse", indexes = { @Index(name = "IDX_CODE_POSTAL", columnList = "code_postal") })
public class CPostal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name="code_commune", length = 5)
    @CsvBindByName(column = "Code_commune_INSEE")
    private String codeCommune;

    @Column(name="nom_commune", length = 50)
    @CsvBindByName(column = "Nom_commune")
    private String nomCommune;

    @Column(name="code_postal", length = 5)
    @CsvBindByName(column = "Code_postal")
    private String codePostal;

    @Column(name="lib_achemin", length = 50)
    @CsvBindByName(column = "Libelle_acheminement")
    private String libelleAchemin;

    @Column(name="lieu_dit", length = 50)
    @CsvBindByName(column = "Ligne_5")
    private String lieuDit;

    @Column(name="gps", length = 50)
    @CsvBindByName(column = "coordonnees_gps")
    private String gps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCommune() {
        return codeCommune;
    }

    public void setCodeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
    }

    public String getNomCommune() {
        return nomCommune;
    }

    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getLibelleAchemin() {
        return libelleAchemin;
    }

    public void setLibelleAchemin(String libelleAchemin) {
        this.libelleAchemin = libelleAchemin;
    }

    public String getLieuDit() {
        return lieuDit;
    }

    public void setLieuDit(String lieuDit) {
        this.lieuDit = lieuDit;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPostal CPostal = (CPostal) o;
        return getCodePostal().equals(CPostal.getCodePostal()) &&

                Objects.equals(getCodeCommune(), CPostal.getCodeCommune()) &&
                Objects.equals(getNomCommune(), CPostal.getNomCommune()) &&
                Objects.equals(getLibelleAchemin(), CPostal.getLibelleAchemin()) &&
                Objects.equals(getLieuDit(), CPostal.getLieuDit()) &&
                Objects.equals(getGps(), CPostal.getGps());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCodeCommune(), getNomCommune(), getCodePostal(), getLibelleAchemin(), getLieuDit(), getGps());
    }

    @Override
    public String toString() {
        return "CPostal{" +
                "id=" + id +
                ", codeCommune='" + codeCommune + '\'' +
                ", nomCommune='" + nomCommune + '\'' +
                ", codePostal=" + codePostal +
                ", libelleAchemin='" + libelleAchemin + '\'' +
                ", lieuDit='" + lieuDit + '\'' +
                ", gps=" + gps +
                '}';
    }
}
