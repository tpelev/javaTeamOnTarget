package teams.login_module;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Accounter;


/**
 * Session Bean implementation class AccountEJB
 */
@Stateless

public class AccountEJB {

	@PersistenceContext
	private EntityManager em;
    
	// Getting the accounter object from the database 
	public Accounter getAccountUserName(String userName){
		Query query = em.createQuery("SELECT a FROM Accounter a WHERE a.loginName = '"+userName+"'");
		Accounter accounter = (Accounter)query.getSingleResult();
		return accounter;
	}
	
	// Validating user name and password for the current object
	public boolean validateUserNameAndPassword(String userName, String password){
		try {	
			Accounter account = getAccountUserName(userName);
			//hashing
			if (account.getLoginPassword().equals(password)) {
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
