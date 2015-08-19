package teams.login_module;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import model.Company;

@Stateless
public class CompanyLoginEJB {


	@PersistenceContext
	private EntityManager em;
	
	public Company getCompanyUserName(String userName){
		Company company;
		try{
		Query query = em.createQuery("SELECT a FROM Company a WHERE a.loginName = :loginName", Company.class).setParameter("loginName", userName);
		company = (Company) query.getSingleResult();
		}catch(Exception e){
			return null;
		}
		return company;
	}
	public boolean validateUserNameAndPassword(String userName, String password){
		try {
			Company  company = getCompanyUserName(userName);
			//hashing
			if (company.getLoginPassword().equals(password)) {
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
