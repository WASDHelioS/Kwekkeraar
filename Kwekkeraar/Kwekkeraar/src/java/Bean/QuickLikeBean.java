/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Model.Bericht;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Nick
 */
@ManagedBean
@RequestScoped
public class QuickLikeBean {

    @ManagedProperty(value = "#{param.id}")
    private int id;

    public QuickLikeBean() {
    }

    
    public void quickLikeRedirect() {
        Controller.Controller.Instance().likeBericht(id);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bericht is geliked!", null));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("favorieten.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(QuickLikeBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
