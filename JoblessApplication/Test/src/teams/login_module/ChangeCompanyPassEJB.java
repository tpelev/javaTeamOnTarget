package teams.login_module;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Company;
import model.CompanyProfile;

/**
 * Session Bean implementation class ChangeUserPassEJB
 */
@Stateless
public class ChangeCompanyPassEJB {
	@PersistenceContext
	private EntityManager em;
	private String loginName;
	private String email;
	private int id;

	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean findCompanyEmail(String loginName, String email) {

		boolean isExist = false;
		CompanyProfile cp = findCompanyObject(loginName);

		if (cp.getCompany().getLoginName().equals(loginName) && cp.getEmail().equals(email)) {
			setLoginName(cp.getCompany().getLoginName());
			setEmail(cp.getEmail());
			setId(cp.getCompany().getId());
			isExist = true;
		}
		return isExist;
	}

	
	private CompanyProfile findCompanyObject(String loginName) {
		CompanyProfile cp = new CompanyProfile();
		Query query = em.createQuery("SELECT cp FROM CompanyProfile cp where cp.company.loginName='" + loginName + "'");
		cp = (CompanyProfile) query.getSingleResult();
		return cp;
	}

	public void updatePass(String password) {
		Company company = em.find(Company.class, this.id);
		company.setLoginPassword(password);	
		em.persist(company);
	
	}
}
