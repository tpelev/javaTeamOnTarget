package teams.login_module;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.User;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless

public class UserEJB {
	@PersistenceContext
	private EntityManager em;
    
	public User getAdminUserName(String userName){
		Query query = em.createQuery("SELECT u FROM User u WHERE u.loginName = '"+userName+"'");
		User user = (User) query.getSingleResult();
		return user;
	}

}
