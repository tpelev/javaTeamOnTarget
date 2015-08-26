package teams.admin_module.controller;

import java.util.List;

import javax.ejb.Stateful;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Advertisement;
import model.CompanyProfile;

@Stateful
public class CompanyDaoBean {
	@PersistenceContext
	private EntityManager entityManager;

	public List<CompanyProfile> showAllCompanies() {
		Query query = entityManager.createQuery("Select a from CompanyProfile a");

		@SuppressWarnings("unchecked")
		List<CompanyProfile> list = query.getResultList();

		return list;
	}

	public void updateCompany(int id, String companyName, String addres, String mol, String EIK, String email,
			String companyType) {

		CompanyProfile tempCompanyProfile = entityManager.find(CompanyProfile.class, id);
		if (tempCompanyProfile != null) {
			tempCompanyProfile.setCompanyName(companyName);

			tempCompanyProfile.setAdress(addres);
			tempCompanyProfile.setMol(mol);
			tempCompanyProfile.setEik(EIK);
			tempCompanyProfile.setEmail(email);
			tempCompanyProfile.setCompanyType(companyType);

			entityManager.flush();
		}

	}

	public void updateStatusAproval(int id) {
		CompanyProfile tempCompanyProfile = entityManager.find(CompanyProfile.class, id);
		if (tempCompanyProfile != null) {
			tempCompanyProfile.setStatus(1);
			entityManager.flush();
		}
	}

	public void updateStatusNotAproval(int id) {
		CompanyProfile tempCompanyProfile = entityManager.find(CompanyProfile.class, id);
		if (tempCompanyProfile != null) {
			tempCompanyProfile.setStatus(2);
			entityManager.flush();
		}
	}
	 public List<CompanyProfile> pageAllAdvertisements() {
	    	
	    	List<CompanyProfile> allItems = null;
	        TypedQuery<CompanyProfile> query = entityManager.createQuery("CompanyProfile.findAll", CompanyProfile.class);
	        allItems  = query.getResultList();
	        return allItems;
	    }
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public int count() {
	       
			CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
	        Root<CompanyProfile> rt = cq.from(CompanyProfile.class);
	        cq.select(entityManager.getCriteriaBuilder().count(rt));
	        Query q = entityManager.createQuery(cq);
	        return ((Long) q.getSingleResult()).intValue();
	    }
	  
	  
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CompanyProfile> findRange(int[] range) {
	        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
	        cq.select(cq.from(CompanyProfile.class));
	        Query q = entityManager.createQuery(cq);
	        q.setMaxResults(range[1] - range[0]);
	        q.setFirstResult(range[0]);
	        return q.getResultList();
	    }
}

