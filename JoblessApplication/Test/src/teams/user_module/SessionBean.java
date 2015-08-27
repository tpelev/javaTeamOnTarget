package teams.user_module;

import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
public class SessionBean {
	@PersistenceContext
	private EntityManager manager;

	public SessionBean() {

	}

	/**
	 * Creates a query extracting all not expired and approved by admin ads.
	 * 
	 * @return List with all valid ads
	 * 
	 * @author Sneja, Metodi, Martin
	 */
	public List<Advertisement> showAllAdAproved() {
		TypedQuery<Advertisement> quer = manager
				.createQuery(
						"Select c from Advertisement c where c.isApproved = true and c.isExpired = false ORDER BY c.isVip DESC",
						Advertisement.class);
		return quer.getResultList();
	}

	/**
	 * Creates a query that retrieves all available places
	 * 
	 * @return list of Places
	 * 
	 * @author Sneja, Metodi, Martin
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
	 * @author Sneja, Metodi, Martin
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
	 * @author Sneja, Metodi, Martin
	 */
	public List<CompanyProfile> allCompanies() {
		return manager.createQuery("Select c from CompanyProfile c",
				CompanyProfile.class).getResultList();
	}

	/**
	 * Method creating a StringBuilder depending on the search parameters
	 * submitted by user. StringBuilder is converted to a query String
	 * 
	 * @return List from Advertisement answering to search parameters provided
	 *         by user
	 *         
	 *  @author tina
	 */
	public List<Advertisement> searchAds(String word, String idPlace,
			String idCategory, String idCompany, String isVip) {

		StringBuilder sb = new StringBuilder();
		sb.append("Select a From Advertisement a Where ");

		if (!word.equalsIgnoreCase("")) {
			sb.append(" a.title Like '%" + word + "%' and ");
		}
		if (!idPlace.equalsIgnoreCase("all")) {
			sb.append(" a.place.placesName = '" + idPlace + "' and ");
		}
		if (!idCategory.equalsIgnoreCase("all")) {
			sb.append(" a.category.categorieName = '" + idCategory + "' and ");
		}
		if (!idCompany.equalsIgnoreCase("all")) {
			sb.append(" a.companyProfile.companyName = '" + idCompany
					+ "' and ");
		}
		if (isVip.equals("true")) {
			sb.append(" a.isVip = " + isVip + " and ");
		}
		sb.append(" a.isApproved = true and a.isExpired = false ");

		if (!isVip.equals("true")) {
			sb.append(" ORDER BY a.isVip DESC");
		}

		TypedQuery<Advertisement> q = manager.createQuery(sb.toString(),
				Advertisement.class);

		return q.getResultList();
	}

	/**
	 * Method that creates an ad. Information about the current logged user is
	 * being requested from database, so the ad can be created without prompting
	 * the user for such information. Persists the gathered information.
	 *
	 * @return an object from Advertisement, containing all required ads fields.
	 * 
	 * @author Sneja, Metodi, Martin
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
	 * @author tina
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
