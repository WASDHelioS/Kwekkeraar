/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Model.Bericht;
import Model.Enums.Recht;
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
public class BerichtBean {

    private Bericht bericht;

    public BerichtBean() {
        bericht = new Bericht();
    }

    public Bericht getBericht() {
        return bericht;
    }

    public void setBericht(Bericht bericht) {
        this.bericht = bericht;
    }

    public String addBericht() {
        if (Controller.Controller.Instance().getLoggedIn().getRecht().equals(Recht.ja)) {
            Controller.Controller.Instance().addBericht(bericht.getInhoud(), Controller.Controller.Instance().getLoggedIn());
            FacesMessage msg = new FacesMessage("Uw bericht is geplaatst!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bericht is geplaatst!", null));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return "homepage.xhtml?faces-redirect=true";
        }
        else {
            FacesMessage msg = new FacesMessage("U heeft het recht niet om een bericht te plaatsen!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
    }
}
