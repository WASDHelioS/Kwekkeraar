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
public class ToevoegenFavoriteBericht {
    
    public ToevoegenFavoriteBericht() {
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
    public void saveFavoriet()
    {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Gebruiker Nick = new Gebruiker("NickHiert", "abcdefg", "gebruiker", "normaal");
        Bericht b1 = new Bericht(1,"abcdefg", Nick);
        Bericht b2 = new Bericht(2,"hijklmnop", Nick);
        
        Nick.addBerichten(b1);
        Nick.addBerichten(b2);
        
        Nick.addBerichten_liked(b2);
        
       List<Gebruiker> result;
       boolean geslaagd = true;
       

       try{
           session.save(Nick);
           result = session.createQuery("from Gebruiker").list();
           //HQL moet nog goedgekregen worden maar werkt wel.
           session.getTransaction().commit();
           Gebruiker gebr;
          
           if(result!=null & result.size()!=1){
               geslaagd = false;
               
           }else{
               gebr = result.get(0);
               
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
