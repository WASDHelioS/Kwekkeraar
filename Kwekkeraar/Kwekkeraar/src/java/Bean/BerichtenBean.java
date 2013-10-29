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

    }
    
    @PostConstruct
    public void init() {
        berichten = Controller.Instance().getAllBerichtenVanGebruiker(naam);
        if(!berichten.isEmpty()) {
            for(int i = 0; i < berichten.size(); i++) {
                if(berichten.get(i).getInhoud().length() > 120) {
                    berichten.get(i).setInhoud(new StringBuilder(berichten.get(i).getInhoud()).insert(120, "\n").toString());
                }
            }
        }
    }
    
    public boolean checkIfBerichtenIsEmpty() {
        return berichten.isEmpty();
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
