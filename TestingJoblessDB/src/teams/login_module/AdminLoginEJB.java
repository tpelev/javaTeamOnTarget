package teams.login_module;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Admin;

@Stateless
public class AdminLoginEJB {
	@PersistenceContext
	private EntityManager em;
	
	public Admin getAdminUserName(String userName){
		Query query = em.createQuery("SELECT a FROM Admin a WHERE a.loginName = '"+userName+"'");
		Admin admin = (Admin) query.getSingleResult();
		return admin;
	}
}
