package teams.login_module;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import model.User;

@Stateless
public class UserLoginEJB {

	@PersistenceContext
	private EntityManager em;
	
	public User getUserUserName(String userName){
		User user;
		try{
		Query query = em.createQuery("SELECT a FROM User a WHERE a.loginName = :loginName", User.class).setParameter("loginName", userName);
		user = (User) query.getSingleResult();
		}catch(Exception e){
			return null;
		}
		return user;
	}
	public boolean validateUserNameAndPassword(String userName, String password){
		try {
			User  user = getUserUserName(userName);
			//hashing
			if (user.getLoginPassword().equals(password)) {
				return true;
			}
			else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}	
	}
}
