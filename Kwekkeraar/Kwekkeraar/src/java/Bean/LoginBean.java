/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.Controller;
import Model.Gebruiker;
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

    public String login() {

        String salt = controller.getSaltFromDB(gebruiker.getNaam());
        if (salt != null) {
            String hashedPassword = gebruiker.hashSHA512(gebruiker.getWachtwoord(), salt);

            loggedIn = controller.Login(gebruiker.getNaam(), hashedPassword);

            if (loggedIn) {
                gebruiker = Controller.Instance().getLoggedIn();
                return "favorieten.xhtml?faces-redirect=true";
            } else {
                return "Login.xhtml?faces-redirect=true";
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
        return "homepage.xhtml";
    }

}
