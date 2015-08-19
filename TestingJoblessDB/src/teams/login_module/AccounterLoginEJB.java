package teams.login_module;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Accounter;
import model.Admin;

@Stateless
public class AccounterLoginEJB {

		@PersistenceContext
		private EntityManager em;
		
		public Accounter getAccounterUserName(String userName){
			Accounter accounter;
			try{
			Query query = em.createQuery("SELECT a FROM Accounter a WHERE a.loginName = :loginName", Admin.class)
					.setParameter("loginName", userName);
			 accounter = (Accounter) query.getSingleResult();
			}catch(Exception e){
				return null;
			}
			return accounter;
		}
		public boolean validateUserNameAndPassword(String userName, String password){
			try {
				Accounter accounter = getAccounterUserName(userName);
				//hashing
				if (accounter.getLoginPassword().equals(password)) {
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
