package teams.user_module;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import model.Advertisement;
import model.Category;
import model.CompanyProfile;
import model.Place;
import model.UserProfile;

/**
 * Session Bean implementation class TestSessionBean
 */
@Stateless
@LocalBean
public class QueriesEJB {
	@PersistenceContext
	private EntityManager manager;

	public QueriesEJB() {

	}

	/**
	 * Creates a query that retrieves all available places
	 * 
	 * @return list of Places
	 * 
	 * @author Sneja
	 */
	public List<Place> allPlaces() {
		return manager.createQuery("Select c from Place c", Place.class)
				.getResultList();
	}

	/**
	 * Creates a query that retrieves all available categories
	 * 
	 * @return list of Category
	 * 
	 * @author Metodi
	 */
	public List<Category> allCategories() {
		return manager.createQuery("Select c from Category c", Category.class)
				.getResultList();
	}

	/**
	 * Creates a query that retrieves all available companies
	 * 
	 * @return list of CompanyProfile
	 * 
	 * @author Martin
	 */
	public List<CompanyProfile> allCompanies() {
		return manager.createQuery("Select c from CompanyProfile c",
				CompanyProfile.class).getResultList();
	}

	/**
	 * Method creating a Criteria Query depending on the search parameters
	 * submitted by user. The returned list is grouped by ten entries for each call.
	 * 
	 * @return List of ten Advertisements answering to search parameters provided
	 *         by user
	 *         
	 *  @author Zlatina, Metodi, Martin, Sneja, Damian
	 */
	public List<Advertisement> searchAdsOnCurrentPage(String word, String placeName,
			String categoryName, String companyName, boolean isVip, int from, int elementsPerPage) {
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery <Advertisement> cq = cb.createQuery(Advertisement.class);
		Root <Advertisement> adv = cq.from(Advertisement.class);
		Join <Advertisement, Category> category = adv.join("category");
		Join <Advertisement, Place> place = adv.join("place");
		Join <Advertisement, CompanyProfile> compProf = adv.join("companyProfile");
		
		
		if (word.equalsIgnoreCase("")) {
			word = "%";
		}
		if (placeName.equalsIgnoreCase("all")) {
			placeName = "%";
		}
		if (categoryName.equalsIgnoreCase("all")) {
			categoryName = "%";
		}
		if (companyName.equalsIgnoreCase("all")) {
			companyName ="%";
		}
		
		if (isVip == true) {
			cq.where(cb.like(adv.get("title"), "%"+word+"%"),
					cb.like(category.get("categorieName"), categoryName), 
					cb.like(place.get("placesName"), placeName),
					cb.like(compProf.get("companyName"), companyName),
					cb.isTrue(adv.get("isApproved")),
					cb.isFalse(adv.get("isExpired")),
					cb.isTrue(adv.get("isVip")));
			
		}else{
			cq.where(cb.like(adv.get("title"), "%"+word+"%"),
					cb.like(category.get("categorieName"), categoryName), 
					cb.like(place.get("placesName"), placeName),
					cb.like(compProf.get("companyName"), companyName),
					cb.isTrue(adv.get("isApproved")),
					cb.isFalse(adv.get("isExpired")));
			cq.orderBy(cb.desc(adv.get("isVip")));
		}

		
		
		TypedQuery<Advertisement> quer = manager
				.createQuery(cq);
		quer.setFirstResult(from);
		quer.setMaxResults(elementsPerPage);
		
		return quer.getResultList();
	}
	
	/**
	 * Method creating a Criteria Query depending on the search parameters
	 * submitted by user. The number of entries is returned
	 * 
	 * 
	 * @param word
	 * @param placeName
	 * @param categoryName
	 * @param companyName
	 * @param isVip
	 * @return The number of entries by the given criteria
	 * 
	 * @author Damian, Zlatina, Metodi, Martin, Sneja
	 */
	
	public long countAds(String word, String placeName,
			String categoryName, String companyName, boolean isVip) {
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root <Advertisement> adv = cq.from(Advertisement.class);
		Join <Advertisement, Category> category = adv.join("category");
		Join <Advertisement, Place> place = adv.join("place");
		Join <Advertisement, CompanyProfile> compProf = adv.join("companyProfile");
		cq.select(cb.count(adv));
		if (word.equalsIgnoreCase("")) {
			word = "%";
		}
		if (placeName.equalsIgnoreCase("all")) {
			placeName = "%";
		}
		if (categoryName.equalsIgnoreCase("all")) {
			categoryName = "%";
		}
		if (companyName.equalsIgnoreCase("all")) {
			companyName ="%";
		}
		
		if (isVip == true) {
			cq.where(cb.like(adv.get("title"), "%"+word+"%"),
					cb.like(category.get("categorieName"), categoryName), 
					cb.like(place.get("placesName"), placeName),
					cb.like(compProf.get("companyName"), companyName),
					cb.isTrue(adv.get("isApproved")),
					cb.isFalse(adv.get("isExpired")),
					cb.isTrue(adv.get("isVip")));
			
		}else{
			cq.where(cb.like(adv.get("title"), "%"+word+"%"),
					cb.like(category.get("categorieName"), categoryName), 
					cb.like(place.get("placesName"), placeName),
					cb.like(compProf.get("companyName"), companyName),
					cb.isTrue(adv.get("isApproved")),
					cb.isFalse(adv.get("isExpired")));
			cq.orderBy(cb.desc(adv.get("isVip")));
		}		
		TypedQuery<Long> quer = manager.createQuery(cq);		
		return quer.getSingleResult();
	}
	
	

	/**
	 * Method that creates an ad. Information about the current logged user is
	 * being requested from database, so the ad can be created without prompting
	 * the user for such information. Persists the gathered information.
	 *
	 * @return an object from Advertisement, containing all required ads fields.
	 * 
	 * @author Sneja, Metodi, Martin, Damian, Sneja
	 */
	public Advertisement createAdvertisement(String userName, String title,
			String content, String test, boolean is_vip, String placeName,
			String categoryName) {
		TypedQuery<CompanyProfile> query = manager.createQuery(
				"SELECT p FROM CompanyProfile p JOIN p.company c WHERE c.loginName='"
						+ userName + "'", CompanyProfile.class);
		CompanyProfile profile = query.getSingleResult();

		TypedQuery<Place> queryPlace = manager.createQuery(
				"select p from Place p where p.placesName='" + placeName + "'",
				Place.class);
		TypedQuery<Category> queryCategory = manager.createQuery(
				"select p from Category p where p.categorieName='"
						+ categoryName + "'", Category.class);
		Category category = queryCategory.getSingleResult();
		Place place = queryPlace.getSingleResult();
		Advertisement advertisement = new Advertisement();
		advertisement.setCategory(category);
		advertisement.setTitle(title);
		advertisement.setCompanyProfile(profile);
		advertisement.setContent(content);
		advertisement.setTest(test);
		if (is_vip) {
			advertisement.setPrice(30);
		} else {
			advertisement.setPrice(0);
		}
		Date date = null;
		advertisement.setActivityDays(30);
		advertisement.setExpirationDate(date);
		advertisement.setPlace(place);
		advertisement.setIsVip(is_vip);
		advertisement.setIsApproved(false);
		advertisement.setIsPaid(false);
		advertisement.setIsExpired(false);

		manager.persist(advertisement);
		return advertisement;
	}

	/**
	 * Creates a query that retrieves the UserProfile by given username.
	 * 
	 * @return The UserProfile of the corresponding user
	 * 
	 * @author Damian
	 */
	public UserProfile getUserProfileByUsername(String userName) {
		TypedQuery<UserProfile> query = manager.createQuery(
				"SELECT p FROM UserProfile p JOIN p.user c WHERE c.loginName='"
						+ userName + "'", UserProfile.class);
		return query.getResultList().get(0);
	}

	/**
	 * Creates a query that retrieves the Advertisement by given Company
	 * Username.
	 * 
	 * @return The Advertisement of the corresponding company username
	 * 
	 * @author Zlatina, Medoti, Martin, Sneja, Damian
	 */
	public List<Advertisement> getAdvertisementByCompanyName(String userName) {
		TypedQuery<CompanyProfile> queryCP = manager.createQuery(
				"SELECT p FROM CompanyProfile p JOIN p.company c WHERE c.loginName='"
						+ userName + "'", CompanyProfile.class);
		CompanyProfile profile = queryCP.getSingleResult();
		TypedQuery<Advertisement> queryA = manager.createQuery(
				"SELECT a FROM Advertisement a JOIN a.companyProfile c WHERE c.id= "
						+ profile.getId()
						+ " AND a.isApproved = true AND a.isExpired = false",
				Advertisement.class);

		return queryA.getResultList();
	}

}
