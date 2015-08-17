package teams.admin_module.controler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale.Category;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import model.Advertisement;
import model.Place;
@ManagedBean(name = "BeenAdv")
public class ManagerBeanForADS {
	@EJB
	private EJBAdsUsers ejbCompanies;

	private List<Advertisement> userList = new ArrayList<>();
	private Advertisement advertisement;

	private String category;
	private String title;
	private String content;
	private String test;
	private String price;
	private String place;
	
	private boolean isVip;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	
	

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public List<SelectItem> getShowAllPlaces() {

		List<SelectItem> items = new ArrayList<SelectItem>();
		List<Place> places = ejbCompanies.showAllPlaces();
		for (Place place : places) {
			items.add(new SelectItem(place.getPlacesName()));
		}
		return items;
	}
	public List<SelectItem> getShowAllCategories() {

		List<SelectItem> items = new ArrayList<SelectItem>();
		List<model.Category> categorys = ejbCompanies.showAllCategories();
		for (model.Category category : categorys) {
			items.add(new SelectItem(category.getCategorieName()));
		}
		return items;
	}

	public List<Advertisement> getAdvsList() {
		userList = ejbCompanies.showAllAdvs();
		return userList;
	}

	public void deleteAdvs(Advertisement adv) {
		ejbCompanies.deleteAdvs(adv.getId());

	}

	public void updateAdvs(Advertisement adv) {
		ejbCompanies.updateeAdvs(adv.getId(), getCategory(), getTitle(), getContent(),
				getTest(), Double.valueOf(getPrice()), getPlace(), Boolean.valueOf(isVip()));
	}
	// public String update(Advertisement adv){
	// int id = adv.getId();
	// return "ADS.xhtml";
	// }

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

}
