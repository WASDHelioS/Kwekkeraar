/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Bericht;
import Model.Enums.Accountsoort;
import Model.Enums.Recht;
import Model.Enums.Rol;
import Model.Gebruiker;
import com.sun.mail.util.LogOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import org.hibernate.Session;

/**
 *
 * @author Nick
 */
public class Controller extends Observable {

    private Session session;
    private static Controller controller;
    private Gebruiker loggedIn = new Gebruiker();

    @Override
    public void notifyObservers(Object o) {
        this.setChanged();
        super.notifyObservers(o);
    }

    public static Controller Instance() {
        if (controller == null) {
            controller = new Controller();
        }

        return controller;
    }

    public void Logout() {
        session.evict(loggedIn);
        this.loggedIn = new Gebruiker();

    }

    public Gebruiker getLoggedIn() {
        return loggedIn;
    }

    public String getSaltFromDB(String username) {

        initSession();

        List result = session.createSQLQuery("select salt FROM `gebruiker` where naam='" + username + "'").list();

        if (!result.isEmpty()) {
            return result.get(0).toString();
        }
        return null;
    }

    /**
     * *
     * Login
     *
     * @param username
     * @param password
     * @return
     */
    public boolean Login(String username, String password) {
        boolean result = false;

        initSession();
        List results = session.createSQLQuery("SELECT naam, wachtwoord,rol,accountsoort,recht, salt FROM `gebruiker` WHERE naam='" + username + "' and wachtwoord='" + password + "'").list();

        if (!results.isEmpty()) {
            Gebruiker usr = new Gebruiker();
            Object[] o = (Object[]) results.get(0);
            usr.setNaam((o[0].toString()));
            usr.setWachtwoord(o[1].toString());
            if (o[2].toString().equals("gebruiker")) {
                usr.setRol(Rol.gebruiker);
            } else if (o[2].toString().equals("moderator")) {
                usr.setRol(Rol.moderator);
            } else {
                usr.setRol(Rol.gebruiker);
            }

            if (o[3].toString().equals("normaal")) {
                usr.setAccountsoort(Accountsoort.normaal);
            } else if (o[3].toString().equals("platinum")) {
                usr.setAccountsoort(Accountsoort.platinum);
            } else {
                usr.setAccountsoort(Accountsoort.normaal);
            }

            if (o[4].toString().equals("ja")) {
                usr.setRecht(Recht.ja);
            } else if (o[4].toString().equals("nee")) {
                usr.setRecht(Recht.nee);
            } else {
                usr.setRecht(Recht.ja);
            }

            usr.setSalt(o[5].toString());

            Set<Gebruiker> favorieten;
            favorieten = getAllFavorietenVanGebruiker(usr);
            usr.setPersoon_favoriet(favorieten);
            this.loggedIn = usr;
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public void addUser(String username, String password) {
        initSession();

        Gebruiker gebr = new Gebruiker(username, password, "gebruiker", "normaal", "ja");

        gebr.setPassword(gebr.getWachtwoord(), true);

        session.beginTransaction();

        session.save(gebr);
        session.getTransaction().commit();
    }

    public void addBericht(String bericht, Gebruiker user) {
        initSession();

        Bericht b = new Bericht();
        b.setInhoud(bericht);

        b.setPoster(user);

        session.beginTransaction();
        session.save(b);
        session.getTransaction().commit();
    }

    /**
     * Checks if the name exists in the database.
     *
     * @param name The name entered by the user.
     * @return true if there IS a similar name in the database; false if there
     * isn't such a name in the database.
     */
    public boolean doesNameExistInDB(String name) {

        initSession();
        List result = session.createSQLQuery("select naam from `gebruiker` where naam='" + name + "'").list();
        if (result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void initSession() {
        if (session == null || HibernateUtil.getSessionFactory().isClosed()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
    }

    public ArrayList<Gebruiker> getAllGebruikers() {
        initSession();
        List result = session.createSQLQuery("select naam from `gebruiker`").list();
        ArrayList<Gebruiker> gebr = new ArrayList<Gebruiker>();
        for (int i = 0; i < result.size(); i++) {
            Gebruiker gebru = new Gebruiker();
            gebru.setNaam(result.get(i).toString());
            gebr.add(gebru);
        }
        return gebr;
    }

    public ArrayList<Bericht> getAllBerichtenVanGebruiker(String gebruiker) {
        initSession();
        List result = session.createSQLQuery("select id, inhoud from bericht where poster ='" + gebruiker + "'").list();
        ArrayList<Bericht> ber = new ArrayList<Bericht>();
        for (int i = 0; i < result.size(); i++) {
            Bericht b = new Bericht();
            Object[] o = (Object[]) result.get(i);
            b.setId((Integer) o[0]);
            b.setInhoud(o[1].toString());
            ber.add(b);
        }
        return ber;
    }

    public void removeUser(Gebruiker gebruiker) {
        Logout();
        initSession();
        session.beginTransaction();
        session.delete(gebruiker);
        session.getTransaction().commit();
    }

    public Set<Gebruiker> getAllFavorietenVanGebruiker(Gebruiker gebruiker) {
        initSession();
        List result = session.createSQLQuery("select favoriet from gebruiker_favoriet where naam='" + gebruiker.getNaam() + "'").list();
        Set<Gebruiker> favs = new HashSet<Gebruiker>();
        for (int i = 0; i < result.size(); i++) {
            Gebruiker g = new Gebruiker();

            g.setNaam(result.get(i).toString());
            favs.add(g);
        }
        return favs;
    }

    public boolean voegToeAanFavorieten(Gebruiker favoriet, Set<Gebruiker> favorieten) {
        session.flush();
        initSession();
        session.beginTransaction();

        boolean contains = false;

        for (Gebruiker gebruiker : favorieten) {
            if (gebruiker.getNaam().equals(favoriet.getNaam())) {
                contains = true;
                break;
            }
        }

        if (!contains) {
            loggedIn.addPersoon_favoriet(favoriet);
            session.update(loggedIn);
            session.getTransaction().commit();
            return true;
        } else {
            return false;
        }
    }
}
