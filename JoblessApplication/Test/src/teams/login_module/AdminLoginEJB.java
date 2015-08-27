package teams.login_module;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Admin;

@Stateless
@NamedQuery(name = "Login.selectquery", query ="SELECT a FROM Admin a WHERE a.loginName = :loginName")
public class AdminLoginEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	
	/**
	 * Get Admin object from the database
	 * @param userName holds admin's username
	 * @return admin
	 * @author Tihomir Pelev
	 */
	public Admin getAdminUserName(String userName){
		Admin admin;
		try{
		Query query = em.createQuery("SELECT a FROM Admin a WHERE a.loginName = :loginName", Admin.class).setParameter("loginName", userName);
		 admin = (Admin) query.getSingleResult();
		}catch(Exception e){
			return null;
		}
		return admin;
	}
	
	/**
	 * Validating user name and password for the current object
	 * @param userName holds admin's username
	 * @param password holds amdin's password
	 * @return boolean
	 * @author Tihomir Pelev
	 */
	public boolean validateUserNameAndPassword(String userName, String password){
		try {
			Admin admin = getAdminUserName(userName);
			//hashing
			if (admin.getLoginPassword().equals(password)) {
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
