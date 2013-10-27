/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Enums.Accountsoort;
import Model.Enums.Rol;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Nick
 */
public class Gebruiker {
    
    private String naam;
    private String wachtwoord;
    private Rol rol;
    private Accountsoort accountsoort;
    private String salt;
    private Set berichten = new HashSet();
    private Set berichten_liked = new HashSet();
    private Set persoon_favoriet = new HashSet();
    
    
    public Gebruiker() {
    }
    
    public Gebruiker(String naam, String wachtwoord, String rol, String accountsoort) {
        this.naam = naam;
        this.wachtwoord = wachtwoord;
        if(rol.equals("gebruiker"))
        {
            this.rol = Rol.gebruiker;
        }
        else
        {
            this.rol = Rol.moderator;
        }
        if(accountsoort.equals("normaal"))
        {
            this.accountsoort = Accountsoort.normaal;
        }
        else
        {
            this.accountsoort = Accountsoort.platinum;
        }
    }
    
    public Set getPersoon_favoriet() {
        return persoon_favoriet;
    }
    
    public void setPersoon_favoriet(Set persoon_favoriet) {
        this.persoon_favoriet = persoon_favoriet;
    }
    
    public void addPersoon_favoriet(Gebruiker gebr) {
        persoon_favoriet.add(gebr);
    }
    
    public Set getBerichten_liked() {
        return berichten_liked;
    }
    
    public void setBerichten_liked(Set berichten_liked) {
        this.berichten_liked = berichten_liked;
    }
    
    public void addBerichten_liked(Bericht bericht) {
        berichten_liked.add(bericht);
    }
    
    public Set getBerichten() {
        return berichten;
    }
    
    public void setBerichten(Set berichten) {
        this.berichten = berichten;
    }
    
    public Accountsoort getAccountsoort() {
        return accountsoort;
    }
    
    public void setAccountsoort(Accountsoort accountsoort) {
        this.accountsoort = accountsoort;
    }
    
    public Rol getRol() {
        return rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public void addBerichten(Bericht bericht) {
        berichten.add(bericht);
    }
    
    public String getNaam() {
        return naam;
    }
    
    public void setNaam(String naam) {
        this.naam = naam;
    }
    
    public String getWachtwoord() {
        return wachtwoord;
    }
    
    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
    
    public void setPassword(String password, boolean setHash) {

        if (setHash) {
            if (salt == null) {
                this.salt = getRandomSalt();
            }
            this.wachtwoord = this.hashSHA512(password, salt);

        } else {
            this.wachtwoord = password;
        }
    }
    
    public void setPassword(String password, String salt) {
        this.wachtwoord = this.hashSHA512(password, salt);
    }
    
    /**
     * Creates a new hash with salt. RECOMMENDED.
     *
     * @param password Password typed by user
     * @param salt Generated salt.
     * @return SHA-512 salt encryption.
     */
    public String hashSHA512(String password, String salt) {
        String genPw = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            genPw = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return genPw;
    }

    /**
     * Generates a new salt (random set of characters to add to the password before encrypting.).
     * @return Salt.
     */
    private String getRandomSalt() {
        SecureRandom sr = new SecureRandom();
        sr.setSeed(sr.generateSeed(20));
        byte[] salt = new byte[8];
        sr.nextBytes(salt);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < salt.length; i++) {
            sb.append(Integer.toString((salt[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
    
    
    public String getSalt() {
        return salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    @Override
    public String toString() {
        return "gebruiker{" + "naam=" + naam + ", wachtwoord=" + wachtwoord + '}';
    }
}
