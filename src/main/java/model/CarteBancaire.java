package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class CarteBancaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double solde;
    private String numeroCarte;
    private String titulaire;
    private LocalDate dateExpiration;
    private int codeSecurite;

    public CarteBancaire() {
    }

    public CarteBancaire(double solde ,String numeroCarte, String titulaire, LocalDate dateExpiration, int codeSecurite) {
        this.solde = solde;
    	this.numeroCarte = numeroCarte;
        this.titulaire = titulaire;
        this.dateExpiration = dateExpiration;
        this.codeSecurite = codeSecurite;
    }

    public Long getId() {
        return id;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
    
    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public int getCodeSecurite() {
        return codeSecurite;
    }

    public void setCodeSecurite(int codeSecurite) {
        this.codeSecurite = codeSecurite;
    }
}
