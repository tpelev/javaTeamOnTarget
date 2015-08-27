package teams.login_module;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Advertisement;
import model.Company;
import model.CompanyProfile;
import model.Invoice;


/**
 * Session Bean implementation class RegisterCompany
 */
@Stateless
@LocalBean
public class RegisterCompanyEJB {

	@PersistenceContext
	private EntityManager em;

	public void addCompany(String companyName, String companyType, String adress, String email, String eik, String mol,
			String loginName, String loginPassword, String sault, String token) {
		Company company = new Company();
		company.setLoginName(loginName);
		company.setLoginPassword(loginPassword);
		company.setSault("sol");
		company.setToken("");
		em.persist(company);
		CompanyProfile companyProfile = new CompanyProfile();
		List<Advertisement> advertisements = null;
		List<Invoice> invoices = null;
		companyProfile.setCompanyName(companyName);
		companyProfile.setCompanyType(companyType);
		companyProfile.setAdress(adress);
		companyProfile.setEmail(email);
		companyProfile.setEik(eik);
		companyProfile.setMol(mol);
		companyProfile.setCompany(company);
		companyProfile.setInvoices(invoices);
		companyProfile.setAdvertisements(advertisements);
		em.persist(companyProfile);
	}

	@SuppressWarnings("unchecked")
	public boolean companyExists(String userName) {
		boolean companyExists = false;
		Query query = em.createQuery("Select c from Company c where c.loginName= '" + userName + "'");	
		List<Company> company = new ArrayList<>();
		company = query.getResultList();
		if (!company.isEmpty()) {
			companyExists = true;
		}
		return companyExists;
	}
	
	public Company getCompanyUserName(String userName){
		Query query = em.createQuery("SELECT c FROM Company c WHERE c.loginName = '"+userName+"'");
		Company company = (Company) query.getSingleResult();
		return company;
	}

}
