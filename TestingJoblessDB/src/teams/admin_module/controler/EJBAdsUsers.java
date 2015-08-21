package teams.admin_module.controler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Advertisement;
import model.Category;
import model.CompanyProfile;
import model.Place;
import model.UserProfile;

@Stateless
@SessionScoped
public class EJBAdsUsers {

	@PersistenceContext
	private EntityManager entityManager;

	
	public List<Place> showAllPlaces(){
		Query selectAllPlace = entityManager.createQuery("select p from Place p");
		@SuppressWarnings("unchecked")
		List<Place> placesList = selectAllPlace.getResultList();
		return  placesList;
	}
	public List<Category> showAllCategories() {
		Query selectAllCategories = entityManager.createQuery("select c from Category c");
		@SuppressWarnings("unchecked")
		List<Category> categoriesList = selectAllCategories.getResultList();
		return categoriesList;
		
	}
	
	public void makeVip(int id) {
		Advertisement advertisment = entityManager.find(Advertisement.class, id);
		advertisment.setIsVip(true);
		advertisment.setPrice(100);
		entityManager.flush();
		
	}

	public List<UserProfile> showAllUsers()
	{
		Query selectAllUser = entityManager.createQuery("Select a from UserProfile a");
		
		@SuppressWarnings("unchecked")
		List<UserProfile> listUsers = selectAllUser.getResultList();
		
		return listUsers;
	}
	
	public List<Advertisement> showForAprovalAds(){
		Query selectUnApprovedAds = entityManager.createQuery("Select a from Advertisement a where a.isApproved='false'");
		@SuppressWarnings("unchecked")
		List<Advertisement> listUnApprovedAds = selectUnApprovedAds.getResultList();
		return listUnApprovedAds;
	}
	
	public List<Advertisement> showAllAprovalAds(){
		Query selectApprovedAds = entityManager.createQuery("Select a from Advertisement a where a.isApproved='true'");
		@SuppressWarnings("unchecked")
		List<Advertisement> listApprovedAds = selectApprovedAds.getResultList();
		return listApprovedAds;
	}

	public List<Advertisement> showAllAdvs()
	{
		Query selectAllAdvs = entityManager.createQuery("Select c from Advertisement c");
		
		@SuppressWarnings("unchecked")
		List<Advertisement> listAdvs = selectAllAdvs.getResultList();
		
		return listAdvs;
	}
	
	public void deleteAdvs(int id)
	{
	Advertisement advertisment = entityManager.find(Advertisement.class, id);
	if(advertisment!=null){
		entityManager.remove(advertisment);
	}
	
	}
	

	public void updateeAdvs(int id ,String category,String title, String content,double price,String place,boolean isVip)
	{

		Advertisement tempAdvs = entityManager.find(Advertisement.class, id);
		if(tempAdvs!=null){
			tempAdvs.setContent(content);
			tempAdvs.setTitle(title);
			tempAdvs.setPrice(price);
			tempAdvs.setIsVip(isVip);
			entityManager.flush();
		}
	}
//	
	
	
	public void updateCompany(CompanyProfile company)
	{
		CompanyProfile tempCompany = entityManager.find(CompanyProfile.class, company.getId());
		if(tempCompany!=null){
			tempCompany.setAdress(company.getAdress());
			tempCompany.setCompanyName(company.getCompanyName());
			tempCompany.setCompanyType(company.getCompanyType());
			tempCompany.setEik(company.getEik());
			tempCompany.setEmail(company.getEmail());
			tempCompany.setMol(company.getMol());
			tempCompany.setStatus(company.getStatus());
			entityManager.flush();
		}
	}
	

	
	public void publishAdd(int id){
		Advertisement tempAdvertisement = entityManager.find(Advertisement.class,id);
		if(tempAdvertisement!=null){
		int durationsDays = tempAdvertisement.getActivityDays();
		String curentDate=getCurrentDate();
		Date expireDay = setExpiredDate(curentDate, durationsDays);
		tempAdvertisement.setExpirationDate(expireDay);
		tempAdvertisement.setIsApproved(true);
		entityManager.flush();
		}
	}
	
	public void chekForExpiDate(){
		try {
		List<Advertisement> listOfApprovalsAdvs=showAllAprovalAds();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < listOfApprovalsAdvs.size(); i++) {
			Date expiredDate = listOfApprovalsAdvs.get(i).getExpirationDate();
			Date currentDate = dateFormat.parse(getCurrentDate());
			
		if(expiredDate.compareTo(currentDate)>0){
			listOfApprovalsAdvs.get(i).setIsExpired(false);
		}
		else {
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
}
