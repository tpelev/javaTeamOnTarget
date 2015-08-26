package teams.admin_module.controller;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.UserProfile;

@Stateful
public class UserDaoBean {
	@PersistenceContext
	private EntityManager entityManager;

	// Query for updating UserProfile
	public void updateUserProfile(int id, String firstName, String lastName, String email) {

		UserProfile userProfile = entityManager.find(UserProfile.class, id);
		if (userProfile != null) {
			userProfile.setFirstName(firstName);
			userProfile.setLastName(lastName);
			userProfile.setEmail(email);
			entityManager.flush();
		}
	}

	// Query for Pagination
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
	// End of query for Pagination
}
