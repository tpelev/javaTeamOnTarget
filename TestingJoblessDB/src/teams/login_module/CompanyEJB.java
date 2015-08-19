package teams.login_module;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Company;

/**
 * Session Bean implementation class CompanyEJB
 */
@Stateless
@LocalBean
public class CompanyEJB {
	@PersistenceContext
	private EntityManager em;
    
	public Company getCompanyUserName(String userName){
		Query query = em.createQuery("SELECT c FROM Company c WHERE c.loginName = '"+userName+"'");
		Company company = (Company) query.getSingleResult();
		return company;
	}
   

}
