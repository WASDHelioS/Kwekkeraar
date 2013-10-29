/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Nick
 */
@ManagedBean
@RequestScoped
public class NavigationBean {

    public String toRegister() {
        return "register.xhtml?faces-redirect=true";
    }
    
    public String toLogin() {
        return "Login.xhtml?faces-redirect=true";
    }
    
    public String toGebruikerLijst() {
        return "gebruikerLijst.xhtml?faces-redirect=true";
    }
    
    public String toFavorieten() {
        return "favorieten.xhtml?faces-redirect=true";
    }
    
    public String toHomepage() {
        return "homepage.xhtml?faces-redirect=true";
    }
    
    public String toVoegKwekToe() {
        return "voegKwekToe.xhtml?faces-redirect=true";
    }
    
    public String toLijstMetKweksVanGebruiker() {
        return "lijstMetKweksVanGebruiker.xhtml?faces-redirect=true";
    }
    
    public String toAccount() {
        return "account.xhtml?faces-redirect=true";
    }
}
