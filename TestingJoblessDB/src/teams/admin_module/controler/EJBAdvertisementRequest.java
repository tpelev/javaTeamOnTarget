package teams.admin_module.controler;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Advertisement;

@Stateless
public class EJBAdvertisementRequest {

	@PersistenceContext
	private EntityManager entityManager;

	public void deleteAdvertisement(int id) {
		Advertisement ad = entityManager.find(Advertisement.class, id);
		entityManager.remove(ad);
	}

	public void updateAdvertisementApproved(Advertisement ad) {
		ad.setIsApproved(true);
		entityManager.persist(ad);
	}
}
