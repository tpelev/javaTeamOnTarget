package teams.admin_module.controler;

import java.util.List;

import javax.ejb.Singleton;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.CompanyProfile;

@Singleton
@SessionScoped
public class EJBCompanyRequest {

	@PersistenceContext
	private EntityManager entityManager;


	@SuppressWarnings("unchecked")
	public List<CompanyProfile> showAllCompanies() {
		Query selectAllCompaniesProfiles = entityManager.createQuery("Select c from CompanyProfile c");
		List<CompanyProfile> listOfProfiles = selectAllCompaniesProfiles.getResultList();
		return listOfProfiles;
	}

	public void updateCompany(int id, String companyName, String addres, String mol, String EIK, String email,String companyType) 
	{

		CompanyProfile tempCompanyProfile = entityManager.find(CompanyProfile.class, id);
		if(tempCompanyProfile!=null){
		tempCompanyProfile.setCompanyName(companyName);

		tempCompanyProfile.setAdress(addres);
		tempCompanyProfile.setMol(mol);
		tempCompanyProfile.setEik(EIK);
		tempCompanyProfile.setEmail(email);
		tempCompanyProfile.setCompanyType(companyType);
		

		entityManager.flush();
		}
	
	}

	public void updateStatusAproval(int id){
		CompanyProfile tempCompanyProfile = entityManager.find(CompanyProfile.class, id);
		if(tempCompanyProfile!=null){
		tempCompanyProfile.setStatus(1);
		entityManager.flush();
		}
	}
	public void updateStatusNotAproval(int id){
		CompanyProfile tempCompanyProfile = entityManager.find(CompanyProfile.class, id);
		if(tempCompanyProfile!=null){
		tempCompanyProfile.setStatus(2);
		entityManager.flush();
		}
	}

}
