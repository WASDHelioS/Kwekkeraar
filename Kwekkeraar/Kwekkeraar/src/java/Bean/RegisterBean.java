/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

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
public class RegisterBean {

    public Gebruiker gebruiker;

    public RegisterBean() {
        gebruiker = new Gebruiker();
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public String register() {
        
        if (!Controller.Controller.Instance().doesNameExistInDB(gebruiker.getNaam())) {
            Controller.Controller.Instance().addUser(gebruiker.getNaam(), gebruiker.getWachtwoord());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesvol geregistreerd!", null));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return "Login.xhtml?faces-redirect=true";
        } else {
            FacesMessage msg = new FacesMessage("Register fout! Gebruiker bestaat al.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "register.xhtml";
        }

    }
}
