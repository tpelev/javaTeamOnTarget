package teams.login_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import model.Advertisement;
import model.Company;
import model.CompanyProfile;
import model.Invoice;
import model.User;

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
	public String hashing(String loginName, String loginPassword){
		HashFunction hash = Hashing.sha256();
		String salt = hash.newHasher().putString(loginName, Charsets.UTF_8).hash().toString();
		String pass = hash.newHasher().putString(loginPassword, Charsets.UTF_8).hash().toString();
		HashCode hs = hash.newHasher().putString(pass, Charsets.UTF_8).putString(salt, Charsets.UTF_8).hash();
		return hs.toString();
	}
	
	public Company getCompanyUserName(String userName){
		Query query = em.createQuery("SELECT c FROM Company c WHERE c.loginName = '"+userName+"'");
		Company company = (Company) query.getSingleResult();
		return company;
	}
//	Goes to new EJB for company make same for user
	
//	public void updatePassword(String loginName, String loginPassword){
//		Query query = em.createQuery("SELECT c FROM Company c WHERE c.loginName = '"+loginName+"'");
//		Company company = (Company) query.getSingleResult();
//		Random rnd = null;
//		company.setLoginPassword(loginPassword);
//	}
//	
//	public String generateNewPassword(Random rnd, String characters, int length)
//	{
//	    char[] text = new char[length];
//	    for (int i = 0; i < length; i++)
//	    {
//	        text[i] = characters.charAt(rnd.nextInt(characters.length()));
//	    }
//	    return new String(text);
//	}
}
