/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import Model.Bericht;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

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
        
        Controller.Controller.Instance().addBericht(bericht.getInhoud(), Controller.Controller.Instance().getLoggedIn());
        return "homepage.xhtml";
    }
}
