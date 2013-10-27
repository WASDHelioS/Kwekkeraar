/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.HibernateUtil;
import Model.Bericht;
import Model.Gebruiker;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Nick
 */
public class ToevoegenGebruikerFavoriet {
    
    public ToevoegenGebruikerFavoriet() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void voegFavorietPersoonToe()
    {
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Gebruiker Nick = new Gebruiker("NickHiert", "abcdefg", "gebruiker", "normaal");
        
        Gebruiker Henk = new Gebruiker("HenkJan","efgh","moderator","platinum");
        

        
       List<Gebruiker> result;
       boolean geslaagd = true;
       

       try{
           session.save(Henk);
           Nick.addPersoon_favoriet(Henk);
           session.save(Nick);
           result = session.createQuery("from Gebruiker as g inner join g.persoon_favoriet").list();
           //HQL moet nog goed gekregen worden, maar is werkend.
           session.getTransaction().commit();
           Gebruiker gebr = new Gebruiker();
           if(result!=null & result.size()!=1){
               geslaagd = false;
           }else{
               //gebr.setNaam(result.get(0).getNaam());
               System.out.println(result.get(0).getNaam());
               if(!gebr.getNaam().equals("NickHiert")){
                    geslaagd = false;
               }
           }
           
       }catch(HibernateException he){
           session.getTransaction().rollback();
           assert false;
       }
       
       assertTrue(geslaagd);
    }
}
