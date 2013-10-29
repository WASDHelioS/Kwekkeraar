/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.Controller;
import Model.Gebruiker;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Nick
 */
@ManagedBean
@RequestScoped
public class LoginBean {

    private Gebruiker gebruiker;
    private final Controller controller = Controller.Instance();
    private boolean loggedIn;

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public LoginBean() {
        gebruiker = new Gebruiker();
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    @PostConstruct
    public void getLoggedInUser() {
        if (gebruiker.getNaam() == null || gebruiker.getNaam().isEmpty()) {
            gebruiker = Controller.Instance().getLoggedIn();
        }
    }

    public String login() {

        String salt = controller.getSaltFromDB(gebruiker.getNaam());
        if (salt != null) {
            String hashedPassword = gebruiker.hashSHA512(gebruiker.getWachtwoord(), salt);

            loggedIn = controller.Login(gebruiker.getNaam(), hashedPassword);

            if (loggedIn) {
                gebruiker = Controller.Instance().getLoggedIn();
                return "favorieten.xhtml?faces-redirect=true";
            } else {
                FacesMessage msg = new FacesMessage("Login fout! Uw naam of wachtwoord is incorrect.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "Login.xhtml";
            }
        } else {
            FacesMessage msg = new FacesMessage("Login fout!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "Login.xhtml";
        }
    }

    public String logout() {
        Controller.Instance().Logout();
        loggedIn = false;
        return "homepage.xhtml?faces-redirect=true";
    }

    public String deleteAccount() {
        Controller.Instance().removeUser(gebruiker);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account succesvol verwijderd!!", null));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        Controller.Instance().Logout();
        return "homepage.xhtml?faces-redirect=true";
    }
}
