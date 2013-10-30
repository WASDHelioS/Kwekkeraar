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
import java.util.*;
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
            if (o[2].toString().equals("g")) {
                usr.setRol(Rol.gebruiker);
            } else {
                usr.setRol(Rol.moderator);
            }

            if (o[3].toString().equals("n")) {
                usr.setAccountsoort(Accountsoort.normaal);
            } else {
                usr.setAccountsoort(Accountsoort.platinum);
            }

            if (o[4].toString().equals("j")) {
                usr.setRecht(Recht.ja);
            } else {
                usr.setRecht(Recht.nee);
            }

            usr.setSalt(o[5].toString());

            Set<Gebruiker> favorieten;
            favorieten = getAllFavorietenVanGebruiker(usr);
            usr.setPersoon_favoriet(favorieten);
            usr.setBerichten_liked(getAllFavorietenVanGebruiker(usr));
            
            this.loggedIn = usr;
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    

    

   

    

    private void initSession() {
        if (session == null || HibernateUtil.getSessionFactory().isClosed()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
    }

    

    

    

    

    //<editor-fold defaultstate="collapsed" desc="User">
    public void editUser(Gebruiker gebruiker) {
        initSession();
        session.beginTransaction();
        session.update(gebruiker);
        session.getTransaction().commit();
        session.evict(gebruiker);
    }

    public boolean afterPostCheckIfGebruikerHas200Posts(Gebruiker gebruiker) {
        ArrayList<Bericht> berichten = getAllBerichtenVanGebruiker(gebruiker.getNaam());

        if (berichten.size() > 5) {
            return true;
        }
        return false;
    }

    public void addUser(String username, String password) {
        initSession();

        Gebruiker gebr = new Gebruiker(username, password, "gebruiker", "normaal", "ja");

        gebr.setPassword(gebr.getWachtwoord(), true);

        session.beginTransaction();

        session.save(gebr);
        session.getTransaction().commit();
        session.evict(gebr);
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
    
    public Gebruiker getSpecificGebruiker(String name) {
        if (doesNameExistInDB(name)) {
            List result = session.createSQLQuery("select wachtwoord, rol, accountsoort, salt, recht from gebruiker"
                    + " where naam='" + name + "'").list();

            if (!result.isEmpty()) {
                Gebruiker usr = new Gebruiker();
                Object[] o = (Object[]) result.get(0);
                usr.setNaam(name);
                usr.setWachtwoord(o[0].toString());
                if (o[1].toString().equals("g")) {
                    usr.setRol(Rol.gebruiker);
                } else {
                    usr.setRol(Rol.moderator);
                }

                if (o[2].toString().equals("n")) {
                    usr.setAccountsoort(Accountsoort.normaal);
                } else {
                    usr.setAccountsoort(Accountsoort.platinum);
                }

                if (o[4].toString().equals("j")) {
                    usr.setRecht(Recht.ja);
                } else {
                    usr.setRecht(Recht.nee);
                }

                usr.setSalt(o[3].toString());

                Set<Gebruiker> favorieten;
                favorieten = getAllFavorietenVanGebruiker(usr);
                usr.setPersoon_favoriet(favorieten);
                return usr;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
    public Gebruiker toggleRecht(String gebruiker) {

        initSession();
        List result = session.createSQLQuery("select wachtwoord,rol, accountsoort, salt, recht "
                + "from gebruiker where naam='" + gebruiker + "'").list();
        Object[] o = (Object[]) result.get(0);

        Gebruiker g = new Gebruiker();
        g.setNaam(gebruiker);
        g.setWachtwoord(o[0].toString());
        if (o[1].toString().equals("m")) {
            g.setRol(Rol.moderator);
        } else {
            g.setRol(Rol.gebruiker);
        }
        if (o[2].toString().equals("p")) {
            g.setAccountsoort(Accountsoort.platinum);
        } else {
            g.setAccountsoort(Accountsoort.normaal);
        }
        g.setSalt(o[3].toString());
        if (o[4].toString().equals("n")) {
            g.setRecht(Recht.nee);
        } else {
            g.setRecht(Recht.ja);
        }

        if (g.getRecht() == Recht.ja) {
            g.setRecht(Recht.nee);
        } else {
            g.setRecht(Recht.ja);
        }


        session.beginTransaction();
        session.update(g);
        session.getTransaction().commit();
        session.evict(g);
        return g;
    }

    public void removeUser(Gebruiker gebruiker) {
        Logout();
        initSession();
        session.beginTransaction();
        session.delete(gebruiker);
        session.getTransaction().commit();
        session.evict(gebruiker);
    }
    
    public ArrayList<Gebruiker> getAllGebruikers() {
        initSession();
        List result = session.createSQLQuery("select naam, recht from `gebruiker`").list();
        ArrayList<Gebruiker> gebr = new ArrayList<Gebruiker>();

        for (int i = 0; i < result.size(); i++) {
            Object[] o = (Object[]) result.get(i);
            Gebruiker gebru = new Gebruiker();
            gebru.setNaam(o[0].toString());
            if (o[1].toString().equals("j")) {
                gebru.setRecht(Recht.ja);
            } else {
                gebru.setRecht(Recht.nee);
            }
            gebr.add(gebru);
        }
        return gebr;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Bericht">
    
    public void addBericht(String bericht, Gebruiker user) {
        initSession();

        Bericht b = new Bericht();
        b.setInhoud(bericht);

        b.setPoster(user);

        session.beginTransaction();
        session.save(b);
        session.getTransaction().commit();
        session.evict(b);
    }
    
    public Bericht getSpecificBericht(int id) {
        initSession();
        List result = session.createSQLQuery("select inhoud, poster from bericht where id='" + id + "'").list();
        Bericht b = new Bericht();
        Object[] o = (Object[]) result.get(0);
        b.setId(id);
        b.setInhoud(o[0].toString());
        b.setPoster(getSpecificGebruiker(o[1].toString()));
        return b;
    }
    
    public ArrayList<Bericht> getAllBerichtenVanGebruiker(String gebruiker) {
        initSession();
        List result = session.createSQLQuery("select id, inhoud, poster from bericht where poster ='" + gebruiker + "'").list();
        ArrayList<Bericht> ber = new ArrayList<Bericht>();
        for (int i = 0; i < result.size(); i++) {
            Bericht b = new Bericht();
            Object[] o = (Object[]) result.get(i);
            b.setId((Integer) o[0]);
            b.setInhoud(o[1].toString());
            b.setPoster(getSpecificGebruiker(gebruiker));
            ber.add(b);
        }
        return ber;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Like">
    public void likeBericht(int id) {
        initSession();
        session.beginTransaction();
        Bericht b = getSpecificBericht(id);
        loggedIn.addBerichten_liked(b);
        session.update(loggedIn);
        session.getTransaction().commit();
        session.evict(loggedIn);
    }

    public List<Gebruiker> getAllLikesVanBericht(Bericht b) {
        initSession();
        ArrayList<Gebruiker> gebrs = new ArrayList<>();
        List result = session.createSQLQuery("select naam from bericht_liked where id='" +Integer.toString(b.getId())+ "'").list();
        for(int i = 0; i< result.size(); i++) {
            Gebruiker g = new Gebruiker();
            g.setNaam(result.get(i).toString());
            gebrs.add(g);
        }
        return gebrs;
    }
    
    public Set<Bericht> getAllLikesVanGebruiker(Gebruiker gebruiker) {
        initSession();
        List result = session.createSQLQuery("select id from bericht_liked where naam='"+gebruiker.getNaam()+"'").list();
        Set<Bericht> likes = new HashSet<Bericht>();
        for(int i = 0; i < result.size(); i++) {
            Bericht b = new Bericht();
            b.setId((Integer) result.get(i));
            
            likes.add(b);
        }
        return likes;
    }
    
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Favoriet">
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
            session.evict(loggedIn);
            return true;
        } else {
            return false;
        }
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
    //</editor-fold>
}
