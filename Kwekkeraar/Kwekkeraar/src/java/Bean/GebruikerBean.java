/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.Controller;
import Model.Enums.Accountsoort;
import Model.Enums.Recht;
import Model.Gebruiker;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Nick
 */
@ManagedBean
@SessionScoped
public class GebruikerBean {

    private final ArrayList<Gebruiker> gebruikers;
    private Gebruiker favorietGeklikt;

    public GebruikerBean() {
        gebruikers = Controller.Instance().getAllGebruikers();
    }

    public boolean gebruikerExists(String gebruiker) {
        for (Gebruiker gebr : Controller.Instance().getAllGebruikers()) {
            if (gebr.getNaam().equals(gebruiker)) {
                return true;
            }
        }
        return false;
    }

    public List<Gebruiker> getGebruikers() {
        return gebruikers;
    }

    public Gebruiker getFavorietGeklikt() {
        return favorietGeklikt;
    }

    public String voegToeAanFavorieten(Gebruiker gebruiker) {
        favorietGeklikt = gebruiker;
        if (Controller.Instance().voegToeAanFavorieten(gebruiker, Controller.Instance().getLoggedIn().getPersoon_favoriet())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Favoriet toegevoegd!", null));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return "gebruikerLijst.xhtml?faces-redirect=true";
        } else {
            FacesMessage msg = new FacesMessage("Deze persoon stond al in uw favorieten!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
    }

    public String toggleRecht(Gebruiker gebruiker) {
        int index = -1;
        ArrayList<Gebruiker> allGebruikers = Controller.Instance().getAllGebruikers();
        for (int i = 0; i < allGebruikers.size(); i++) {

            if (allGebruikers.get(i).getNaam().equals(gebruiker.getNaam())) {
                index = i;
                break;
            } else {
                index = -1;
            }
        }
        
        if (index != -1) {
            Gebruiker g = Controller.Instance().toggleRecht(gebruiker.getNaam());
            gebruikers.set(index, g);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Recht gewijzigd bij " + g.getNaam() + " naar " + g.getRecht().toString(), null));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        return "gebruikerLijst.xhtml?faces-redirect=true"; 
        } else {
            FacesMessage msg = new FacesMessage("Gebruiker bestaat niet meer");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
    }

    public String rolToString(Gebruiker gebruiker) {
        if (gebruiker.getRecht() == Recht.ja) {
            return "ja";
        } else {
            return "nee";
        }
    }
    
    public boolean checkIfGebruikerIsPlatinum() {
        if(Controller.Instance().getLoggedIn().getAccountsoort() == Accountsoort.platinum) {
            return true;
        } else {
            return false;
        }
    }
}