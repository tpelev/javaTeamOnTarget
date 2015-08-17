package teams.admin_module.controler;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.User;

import model.Admin;
import model.Advertisement;
import model.Category;
import model.CompanyProfile;
import model.Place;
import model.UserProfile;

@Stateless
public class EJBAdsUsers {

	@PersistenceContext
	private EntityManager entityManager;

	
	public List<Place> showAllPlaces(){
		Query query = entityManager.createQuery("select p from Place p");
		List<Place> temp = query.getResultList();
		return  temp;
	}
	public List<Category> showAllCategories() {
		Query query = entityManager.createQuery("select c from Category c");
		List<Category> temp = query.getResultList();
		return temp;
		
	}

	public List<UserProfile> showAllUsers()
	{
		Query query = entityManager.createQuery("Select a from UserProfile a");
		
		@SuppressWarnings("unchecked")
		List<UserProfile> list = query.getResultList();
		
		return list;
	}
	
	

	public List<Advertisement> showAllAdvs()
	{
		Query query = entityManager.createQuery("Select c from Advertisement c");
		
		@SuppressWarnings("unchecked")
		List<Advertisement> list = query.getResultList();
		
		return list;
	}
	
	public void deleteAdvs(int id)
	{
		
	Advertisement adv = entityManager.find(Advertisement.class, id);
		entityManager.remove(adv);
	}
	

//	private int activityDays;

	public void updateeAdvs(int id ,String category,String title, String content,String test,double price,String place,boolean isVip)
	{
		Query query = entityManager.createQuery("select p from Category p where p.categorieName='"+category+"'");
		Query query2 = entityManager.createQuery("select p from Place p where p.placesName='"+place+"'");
		Category cata = (Category) query.getSingleResult();
		Place pla = (Place) query2.getSingleResult();
		Advertisement temp = entityManager.find(Advertisement.class, id);
		temp.setCategory(cata);
		temp.setPlace(pla);
		temp.setContent(content);
		temp.setTest(test);
		temp.setTitle(title);
		temp.setPrice(price);
		temp.setIsVip(isVip);
		entityManager.flush();
	}
//	
	
	
	public void updateCompany(CompanyProfile company)
	{
		CompanyProfile update = entityManager.find(CompanyProfile.class, company.getId());
		
		update.setAdress(company.getAdress());
		update.setCompanyName(company.getCompanyName());
		update.setCompanyType(company.getCompanyType());
		update.setEik(company.getEik());
		update.setEmail(company.getEmail());
		update.setMol(company.getMol());
		update.setStatus(company.getStatus());
	//	update.setAdvertisements(company.getAdvertisements());
	//	update.setInvoices(company.getInvoices());
		
		entityManager.flush();
	}
	public String getPage(){
		return "UpdateCompanies.xhtml";
	}
}
