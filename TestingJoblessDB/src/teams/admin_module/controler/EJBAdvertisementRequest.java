package teams.admin_module.controler;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Advertisement;
import model.Place;

@Stateless
@SessionScoped
public class EJBAdvertisementRequest {

	@PersistenceContext
	private EntityManager entityManager;

	public void deleteAdvertisement(int id) {
		Advertisement tempAdvertisement = entityManager.find(Advertisement.class, id);
		if(tempAdvertisement!=null)
		entityManager.remove(tempAdvertisement);
	}

	public void updateAdvertisementApproved(Advertisement advertisement) {
		advertisement.setIsApproved(true);
		entityManager.flush();
	}
	
	public List<Place> showAllPlaces(){
		Query selectAllPlace = entityManager.createQuery("select p from Place p");
		@SuppressWarnings("unchecked")
		List<Place> listOfPlaces = selectAllPlace.getResultList();
		return  listOfPlaces;
	}
}
