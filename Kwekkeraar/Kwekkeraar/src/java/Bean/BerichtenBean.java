/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.Controller;
import Model.Bericht;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Nick
 */
@ManagedBean
@RequestScoped
public class BerichtenBean {

    @ManagedProperty(value = "#{param.naam}")
    private String naam;
    private ArrayList<Bericht> berichten;

    public BerichtenBean() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String value = request.getParameter("naam");
        berichten = Controller.Instance().getAllBerichtenVanGebruiker(value);
    }

    public ArrayList<Bericht> getBerichten() {
        return berichten;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
