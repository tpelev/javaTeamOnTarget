package teams.user_module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import model.Category;
import model.Place;

@ManagedBean(name = "reg")
@SessionScoped
public class ResitrationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1086060163623490748L;

	
	@EJB
	private TestSessionBean query;
	private String companyUserName;
	private String title;
	private String cathegory;
	private String publisher;
	private String content;
	private String country;
	private int days;
	private boolean vip;
	
	private List<String> errors;
	private String outcome;

	public List<SelectItem> getShowAllPlaces() {

		List<SelectItem> items = new ArrayList<SelectItem>();
		List<Place> places = query.allPlaces();
		for (Place place : places) {
			items.add(new SelectItem(place.getPlacesName()));
		}
		return items;
	}

	public List<SelectItem> getShowAllCategories() {

		List<SelectItem> items = new ArrayList<SelectItem>();
		List<Category> categorys = query.allCategories();
		for (Category category : categorys) {
			items.add(new SelectItem(category.getCategorieName()));
		}
		return items;
	}

	
	
	
	
	public String getCompanyUserName() {
		return companyUserName;
	}

	public void setCompanyUserName(String companyUserName) {
		this.companyUserName = companyUserName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getCathegory() {
		return cathegory;
	}

	public void setCathegory(String cathegory) {
		this.cathegory = cathegory;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String text) {
		this.content = text;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}



	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public String publishNewAd() {
		if (isValid()) {
			query.createAdvertisement(getCompanyUserName(),getTitle(), getContent(), "", false, getCountry(), getCathegory());
			
			outcome = content + cathegory + isVip() + country;
			return "index.xhtml";
		}
		return "index.xhtml";
	}

	private boolean isValid() {
		boolean errorFound = false;
		errors = new ArrayList<String>();

		if (cathegory == null || cathegory.isEmpty()) {
			errors.add("You should select a category \n");
			errorFound = true;
		}
		if (country == null || country.isEmpty()) {
			errors.add("You should select a city \n");
			errorFound = true;
		}
		if (country == null || country.isEmpty()) {
			errors.add("You should select a city \n");
			errorFound = true;
		}
		if (days < 5 || days > 30) {
			errors.add("Invalid days input \n");
			errorFound = true;
		}
		if (content == null || content.isEmpty()) {
			errors.add("Ad content cannot be empty \n");
		}
		return !errorFound;
	}

	public String setVIPStatuswTest(){
		return "choose_num_of_quest.xhtml";
	}

}
