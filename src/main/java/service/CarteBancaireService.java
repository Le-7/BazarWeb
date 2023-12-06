package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CarteBancaireRepository;
import model.CarteBancaire;
import java.time.LocalDate;
import java.util.List;

@Service
public class CarteBancaireService {

    private final CarteBancaireRepository carteBancaireRepository;

    @Autowired
    public CarteBancaireService(CarteBancaireRepository carteBancaireRepository) {
        this.carteBancaireRepository = carteBancaireRepository;
    }

    public List<CarteBancaire> getAllCartesBancaires() {
        return carteBancaireRepository.findAll();
    }

    public boolean carteExiste(String numeroCarte) {
        return carteBancaireRepository.findByNumeroCarte(numeroCarte) != null;
    }

    public void mettreAJourSolde(String numeroCarte, int codeSecurite, double montant, String nomProprietaire, LocalDate dateExpiration) {
        CarteBancaire carte = carteBancaireRepository.findByNumeroCarte(numeroCarte);

        if (carte != null &&
            codeSecuriteValide(carte, codeSecurite) &&
            proprietaireValide(carte, nomProprietaire) &&
            dateExpirationValide(carte, dateExpiration)) {

            double nouveauSolde = carte.getSolde() + montant;
            carte.setSolde(nouveauSolde);
            carteBancaireRepository.save(carte);
        }
    }

    private boolean codeSecuriteValide(CarteBancaire carte, int codeSecurite) {
        return carte.getCodeSecurite() == codeSecurite;
    }

    private boolean proprietaireValide(CarteBancaire carte, String nomProprietaire) {
        return carte.getTitulaire().equals(nomProprietaire);
    }

    private boolean dateExpirationValide(CarteBancaire carte, LocalDate dateExpiration) {
        return carte.getDateExpiration().equals(dateExpiration);
    }
}
