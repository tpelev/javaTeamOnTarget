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

//	public List<Admin> findAdmins() {
//
//		Query query = entityManager.createQuery("Select a from Admin a");
//		@SuppressWarnings("unchecked")
//		List<Admin> admin = query.getResultList();
//		return admin;
//	}

	public List<CompanyProfile> showAllCompanies()
	{
		Query query = entityManager.createQuery("Select c from CompanyProfile c");
		
		@SuppressWarnings("unchecked")
		List<CompanyProfile> list = query.getResultList();
		
		return list;
	}
	
	public void updateCompany(CompanyProfile company)
	{
		entityManager.persist(company);
	}
}
