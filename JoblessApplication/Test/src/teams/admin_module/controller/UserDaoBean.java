package teams.admin_module.controller;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.CompanyProfile;
import model.UserProfile;

@Stateful
public class UserDaoBean {
	@PersistenceContext
	private EntityManager entityManager;

	public List<UserProfile> showAllUsersProfiles() {
		Query query = entityManager.createQuery("Select a from UserProfile a");

		@SuppressWarnings("unchecked")
		List<UserProfile> list = query.getResultList();

		return list;
	}

	public void updateUserProfile(int id, String firstName, String lastName, String email) {

		UserProfile userProfile = entityManager.find(UserProfile.class, id);
		if (userProfile != null) {
			userProfile.setFirstName(firstName);
			userProfile.setLastName(lastName);
			userProfile.setEmail(email);
			entityManager.flush();
		}
	}
	
	 public List<UserProfile> pageAllAdvertisements() {
	    	
	    	List<UserProfile> allItems = null;
	        TypedQuery<UserProfile> query = entityManager.createQuery("UserProfile.findAll", UserProfile.class);
	        allItems  = query.getResultList();
	        return allItems;
	    }
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public int count() {
	       
			CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
	        Root<UserProfile> rt = cq.from(UserProfile.class);
	        cq.select(entityManager.getCriteriaBuilder().count(rt));
	        Query q = entityManager.createQuery(cq);
	        return ((Long) q.getSingleResult()).intValue();
	    }
	  
	  
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<UserProfile> findRange(int[] range) {
	        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
	        cq.select(cq.from(UserProfile.class));
	        Query q = entityManager.createQuery(cq);
	        q.setMaxResults(range[1] - range[0]);
	        q.setFirstResult(range[0]);
	        return q.getResultList();
	    }

}
