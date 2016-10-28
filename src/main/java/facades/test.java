/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Role;
import entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import security.PasswordStorage;

/**
 *
 * @author Michael
 */
public class test {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SemesterSeedMaven_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        UserFacade uf = new UserFacade();
        try {
          createUser(uf);
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            ex.printStackTrace();
        }

    }
    private static void createUser(UserFacade uf) throws PasswordStorage.CannotPerformOperationException {
        User user = new User();
        User admin = new User();
        User member = new User();
        user.setUserName("user");
        admin.setUserName("admin");
        member.setUserName("member");
        
        String hashedPwd = PasswordStorage.createHash("test");
        user.setPassword(hashedPwd);
        admin.setPassword(hashedPwd);
        member.setPassword(hashedPwd);
        Role role1 = new Role();
        Role role2 = new Role();
        Role role3 = new Role();
        role1.setRoleName("User");
        role2.setRoleName("Admin");
        role3.setRoleName("Member");
        user.addRole(role1);
        admin.addRole(role2);
        member.addRole(role3);
        uf.addUser(user);
        uf.addUser(admin);
        uf.addUser(member);
    }
}
