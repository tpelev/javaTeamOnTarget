package teams.admin_module.controler;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.CompanyProfile;

@Singleton
public class EJBCompanyRequest {

	@PersistenceContext
	private EntityManager entityManager;

	// public List<Admin> findAdmins() {
	//
	// Query query = entityManager.createQuery("Select a from Admin a");
	// @SuppressWarnings("unchecked")
	// List<Admin> admin = query.getResultList();
	// return admin;
	// }

	public List<CompanyProfile> showAllCompanies() {
		Query query = entityManager.createQuery("Select c from CompanyProfile c");

		@SuppressWarnings("unchecked")
		List<CompanyProfile> list = query.getResultList();

		return list;
	}

	//
	// private String companyName;
	// private String addres;
	// private String mol;
	// private String EIK;
	// private String email;
	// private String companyType;
	// private int status;
	public void updateCompany(int id, String companyName, String addres, String mol, String EIK, String email,
			String companyType) {

		CompanyProfile company = entityManager.find(CompanyProfile.class, id);
		company.setCompanyName(companyName);

		company.setAdress(addres);
		company.setMol(mol);
		company.setEik(EIK);
		company.setEmail(email);
		company.setCompanyType(companyType);
		

		entityManager.flush();
		// entityManager.remove(update);
	}

	public void updateStatusAproval(int id){
		CompanyProfile com = entityManager.find(CompanyProfile.class, id);
		com.setStatus(1);
		entityManager.flush();
	}
	public void updateStatusNotAproval(int id){
		CompanyProfile com = entityManager.find(CompanyProfile.class, id);
		com.setStatus(2);
		entityManager.flush();
	}

}
