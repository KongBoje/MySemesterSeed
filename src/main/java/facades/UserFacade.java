package facades;

import security.IUserFacade;
import entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import security.IUser;
import security.PasswordStorage;

public class UserFacade implements IUserFacade {

    /*When implementing your own database for this seed, you should NOT touch any of the classes in the security folder
    Make sure your new facade implements IUserFacade and keeps the name UserFacade, and that your Entity User class implements 
    IUser interface, then security should work "out of the box" with users and roles stored in your database */

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("lam_seedMaven_war_1.0-SNAPSHOTPU");

    public UserFacade() {
               
    }
    
    public void addUser(entity.User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public IUser getUserByUserId(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            return (IUser) em.find(User.class, id);
        } finally {
            em.close();
        }
    }
    /*
     Return the Roles if users could be authenticated, otherwise null
     */

    @Override
    public List<String> authenticateUser(String userName, String password) {
        EntityManager em = emf.createEntityManager();
        User user = null;
        boolean authenticated = false;
        try {
            user = em.find(User.class, userName);
            if (user == null) {
                return null;
            }

            authenticated = PasswordStorage.verifyPassword(password, user.getPassword());
            
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            ex.printStackTrace();
        } catch (PasswordStorage.InvalidHashException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return authenticated ? user.getRolesAsStrings() : null;
    }
}
