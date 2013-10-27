/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Nick
 */
public class Bericht {
    private int id;
    private String inhoud;
    private Gebruiker poster;

    
    public Bericht() {
    }

    public Bericht(int id, String inhoud, Gebruiker poster) {
        this.id = id;
        this.inhoud = inhoud;
        this.poster = poster;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInhoud() {
        return inhoud;
    }

    public void setInhoud(String inhoud) {
        this.inhoud = inhoud;
    }

    public Gebruiker getPoster() {
        return poster;
    }

    public void setPoster(Gebruiker poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Bericht{" + "id=" + id + ", inhoud=" + inhoud + ", poster=" + poster + '}';
    }
    
    
}
