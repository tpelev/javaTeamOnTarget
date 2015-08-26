package teams.admin_module.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Advertisement;
import model.Category;
import model.Place;

@Stateful
public class AdvertsDaoBean {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Advertisement> showAllAdverts() {
		Query query = entityManager.createQuery("Select a from Advertisement a");

		@SuppressWarnings("unchecked")
		List<Advertisement> list = query.getResultList();
		return list;
	}

	public void deleteAdvertisement(int id) {
		Advertisement tempAdvertisement = entityManager.find(Advertisement.class, id);
		if (tempAdvertisement != null)
			entityManager.remove(tempAdvertisement);
	}

	public void updateAdvertisementApproved(Advertisement advertisement) {
		advertisement.setIsApproved(true);
		entityManager.flush();
	}

	public List<Place> showAllPlaces() {
		Query selectAllPlace = entityManager.createQuery("select p from Place p");
		@SuppressWarnings("unchecked")
		List<Place> listOfPlaces = selectAllPlace.getResultList();
		return listOfPlaces;
	}

	public List<Category> showAllCategories() {
		Query selectAllCategories = entityManager.createQuery("select c from Category c");
		@SuppressWarnings("unchecked")
		List<Category> categoriesList = selectAllCategories.getResultList();
		return categoriesList;

	}

	public void updateeAdvs(int id, String category, String title, String content, double price, String place,
			boolean isVip) {

		Advertisement tempAdvs = entityManager.find(Advertisement.class, id);
		if (tempAdvs != null) {
			Query queryCategory = entityManager
					.createQuery("SELECT c FROM Category c where c.categorieName='" + category + "'");
			Category cata = (Category) queryCategory.getSingleResult();
			Query queryPlace = entityManager.createQuery("SELECT p FROM Place p where p.placesName='" + place + "'");
			Place placeObj = (Place) queryPlace.getSingleResult();
			tempAdvs.setCategory(cata);
			tempAdvs.setContent(content);
			tempAdvs.setTitle(title);
			tempAdvs.setPrice(price);
			tempAdvs.setIsVip(isVip);
			tempAdvs.setPlace(placeObj);
			entityManager.flush();
		}
	}

	public void publishAdd(int id) {
		Advertisement tempAdvertisement = entityManager.find(Advertisement.class, id);
		if (tempAdvertisement != null) {
			int durationsDays = tempAdvertisement.getActivityDays();
			String curentDate = getCurrentDate();
			Date expireDay = setExpiredDate(curentDate, durationsDays);
			tempAdvertisement.setExpirationDate(expireDay);
			tempAdvertisement.setIsApproved(true);
			entityManager.flush();
		}
	}

	public void chekForExpiDate() {
		try {
			List<Advertisement> listOfApprovalsAdvs = showAllAdverts();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < listOfApprovalsAdvs.size(); i++) {
				Date expiredDate = listOfApprovalsAdvs.get(i).getExpirationDate();
				Date currentDate = dateFormat.parse(getCurrentDate());

				if (expiredDate.compareTo(currentDate) > 0) {
					listOfApprovalsAdvs.get(i).setIsExpired(false);
				} else {
					listOfApprovalsAdvs.get(i).setIsExpired(true);
				}

			}
			entityManager.flush();
		} catch (ParseException e) {
			System.err.println("Error parsing Date");
		}
	}

	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		return dateFormat.format(currentDate);
	}

	public static Date setExpiredDate(String date, int days) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = null;

		try {
			currentDate = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	public void makeVip(int id) {
		Advertisement advertisment = entityManager.find(Advertisement.class, id);
		advertisment.setIsVip(true);
		advertisment.setPrice(100);
		entityManager.flush();

	}

	public List<Advertisement> showForAprovalAds() {
		Query selectUnApprovedAds = entityManager
				.createQuery("Select a from Advertisement a where a.isApproved='false'");
		@SuppressWarnings("unchecked")
		List<Advertisement> listUnApprovedAds = selectUnApprovedAds.getResultList();
		return listUnApprovedAds;
	}
	  public List<Advertisement> pageAllAdvertisements() {
	    	
	    	List<Advertisement> allItems = null;
	        TypedQuery<Advertisement> query = entityManager.createQuery("Advertisement.findAll", Advertisement.class);
	        allItems  = query.getResultList();
	        return allItems;
	    }
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public int count() {
	       
			CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
	        Root<Advertisement> rt = cq.from(Advertisement.class);
	        cq.select(entityManager.getCriteriaBuilder().count(rt));
	        Query q = entityManager.createQuery(cq);
	        return ((Long) q.getSingleResult()).intValue();
	    }
	  
	  
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Advertisement> findRange(int[] range) {
	        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
	        cq.select(cq.from(Advertisement.class));
	        Query q = entityManager.createQuery(cq);
	        q.setMaxResults(range[1] - range[0]);
	        q.setFirstResult(range[0]);
	        return q.getResultList();
	    }
}
