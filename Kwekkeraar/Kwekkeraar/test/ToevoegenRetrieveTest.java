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
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Nick
 */
public class ToevoegenRetrieveTest {
    
    public ToevoegenRetrieveTest() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @BeforeClass
    public static void setUpClass() throws Exception {
    }
    
    @Test
    public void saveGebruiker()
    {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Gebruiker Nick = new Gebruiker("NickHiert", "abcdefg", "gebruiker", "normaal");
        Bericht b1 = new Bericht(1,"abcdefg", Nick);
        Bericht b2 = new Bericht(2,"hijklmnop", Nick);
        
        Nick.addBerichten(b1);
        Nick.addBerichten(b2);
        
       List<Gebruiker> result;
       List<Bericht> result2;
       boolean geslaagd = true;
       

       try{
           session.save(Nick);
           result = session.createQuery("from Gebruiker").list();
           result2 = session.createQuery("from Bericht").list();
           session.getTransaction().commit();
           Gebruiker gebr;
           Bericht b1check;
           Bericht b2check;
          
           if(result!=null & result.size()!=1){
               geslaagd = false;
               
           }else{
               gebr = result.get(0);
               b1check = result2.get(0);
               b2check = result2.get(1);
               
               if(!gebr.getNaam().equals("NickHiert") && b1check.getId() != 1 && b2check.getId() != 2){
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
