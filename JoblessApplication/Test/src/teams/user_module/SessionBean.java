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

	public List<Advertisement> showAllAdAproved() {
		TypedQuery<Advertisement> quer = manager
				.createQuery(
						"Select c from Advertisement c where c.isApproved = true and c.isExpired = false ORDER BY c.isVip DESC",
						Advertisement.class);
		return quer.getResultList();
	}

	public List<Place> allPlaces() {
		return manager.createQuery("Select c from Place c", Place.class)
				.getResultList();
	}

	public List<Category> allCategories() {
		return manager.createQuery("Select c from Category c", Category.class)
				.getResultList();
	}

	public List<CompanyProfile> allCompanies() {
		return manager.createQuery("Select c from CompanyProfile c",
				CompanyProfile.class).getResultList();
	}

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

	public UserProfile getUserProfileByUsername(String userName) {
		TypedQuery<UserProfile> query = manager.createQuery(
				"SELECT p FROM UserProfile p JOIN p.user c WHERE c.loginName='"
						+ userName + "'", UserProfile.class);
		return query.getResultList().get(0);
	}
}
