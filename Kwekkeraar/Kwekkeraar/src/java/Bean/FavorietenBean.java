/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import Model.Gebruiker;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Nick
 */
@ManagedBean
@RequestScoped
public class FavorietenBean {
    
    private List<Gebruiker> favorieten = new ArrayList<Gebruiker>();

    public List<Gebruiker> getFavorieten() {
        return favorieten;
    }
    
    public FavorietenBean() {
        
    }
    
    @PostConstruct
    public void setAllFavorieten() {
        
        favorieten = getListAsSet(Controller.Controller.Instance().getAllFavorietenVanGebruiker(Controller.Controller.Instance().getLoggedIn()));
    }
    
    public List<Gebruiker> getListAsSet(Set<Gebruiker> set) {
        return new ArrayList<Gebruiker>(set);
    }
    
    public boolean checkIfFavorietenIsEmpty() {
        return favorieten.isEmpty();
    }
}
