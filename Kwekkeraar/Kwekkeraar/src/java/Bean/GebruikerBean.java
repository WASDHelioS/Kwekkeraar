/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Model.Gebruiker;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Nick
 */
@ManagedBean
@SessionScoped
public class GebruikerBean {

    private final ArrayList<Gebruiker> gebruikers;
    
    public GebruikerBean() {
        gebruikers = Controller.Controller.Instance().getAllGebruikers();
    }

    public List<Gebruiker> getGebruikers() {
        return gebruikers;
    }
    
    
}
