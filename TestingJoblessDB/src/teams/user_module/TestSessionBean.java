package teams.user_module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Advertisement;
import model.Category;
import model.CompanyProfile;
import model.Place;

/**
 * Session Bean implementation class TestSessionBean
 */
@Stateless
@LocalBean
public class TestSessionBean {
	@PersistenceContext
	private EntityManager manager;

	public TestSessionBean() {

	}

	@SuppressWarnings("unchecked")
	public List<Advertisement> showAllAd() {
		List<Advertisement> list = manager.createNamedQuery("Advertisement.findAll").getResultList();
		return list;
	}

	public List<Advertisement> showAllAdAproved() {

		List<Advertisement> list = new ArrayList<>();
		Query quer = manager.createQuery("Select c from Advertisement c where c.isApproved = true and c.isExpired = false");
		list = quer.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Place> allPlaces() {
		Query quer = manager.createQuery("Select c from Place c");
		List<Place> list = quer.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Category> allCategories() {
		Query quer = manager.createQuery("Select c from Category c");
		List<Category> list = quer.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<CompanyProfile> allCompanies() {
		Query quer = manager.createQuery("Select c from CompanyProfile c");
		List<CompanyProfile> list = quer.getResultList();
		return list;
	}


	public List<Advertisement> searchAds (String word, String idPlace, String idCategory, String idCompany, String isVip){
	
		StringBuilder sb = new StringBuilder();	
		sb.append("Select a From Advertisement a Where ");
		
		if(!word.equalsIgnoreCase("")){
			sb.append(" a.title Like '%"+word+"%' and ");
		}
		if(!idPlace.equalsIgnoreCase("all")){
			sb.append(" a.place.placesName = '"+idPlace+"' and ");
		}
		if(!idCategory.equalsIgnoreCase("all")){
			sb.append(" a.category.categorieName = '"+idCategory+"' and ");	
			}
		if(!idCompany.equalsIgnoreCase("all")){
			sb.append(" a.companyProfile.companyName = '"+idCompany+"' and ");
		}
		if(isVip.equals("true")){
		sb.append(" a.isVip = "+isVip+" and ");	
		}
		sb.append(" a.isApproved = true and a.isExpired = false");
		
		
	Query q = manager.createQuery(sb.toString());
		List<Advertisement> list = q.getResultList();
		
		if(!isVip.equals("true")){
			Comparator<Advertisement> comparator = new Comparator<Advertisement>() {
				
				@Override
				public int compare(Advertisement o1, Advertisement o2) {
					
					int o1Toint = 0;
					int o2Toint = 0;
					
					if(o1.getIsVip()==true ){
						o1Toint =1;
					}
					if(o2.getIsVip()==true ){
						o2Toint =1;
					}
					
					return o1Toint - o2Toint;
				}
			};
			
			Collections.sort(list, comparator);
		}
		return list;
	}

	public Advertisement createAdvertisement(String userName,String title, String content, String test, boolean is_vip,
			String placeName, String categoryName) {
		
		Query query = manager.createQuery("SELECT p FROM CompanyProfile p JOIN p.company c WHERE c.loginName='"+userName+"'");
		CompanyProfile profile = (CompanyProfile) query.getSingleResult();

		Query queryPlace = manager.createQuery("select p from Place p where p.placesName='" + placeName + "'");
		Query queryCategory = manager.createQuery("select p from Category p where p.categorieName='" + categoryName + "'");
		Category category = (Category) queryCategory.getSingleResult();
		Place place = (Place) queryPlace.getSingleResult();
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
}
