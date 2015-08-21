package teams.user_module;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.json.JSONObject;

import model.Category;
import model.Place;
import model.Test;

@ManagedBean(name = "test")
@SessionScoped
public class AddAdManagedBean {

	@EJB
	private TestSessionBean query;
	private String selectedNumOfQuestions;
	private String[] nums = { "1", "2", "3", "4", "5", "6", "7" };
	private List<Test> questions;
	private String result;
	private String title;
	private String cathegory;
	public String companyUserName;
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

	public String publishNewAdWhitTest() {
		if (isValid()) {
			query.createAdvertisement(getCompanyUserName(),getTitle(), getContent(), result, true, getCountry(), getCathegory());

			return "index.xhtml";
		}
		return "index.xhtml";
	}

	@SuppressWarnings("boxing")
	public String setQuestions() {
		questions = new ArrayList<Test>();
		for (int i = 1; i <= Integer.valueOf(selectedNumOfQuestions); i++) {
			Test test = new Test();
			test.setQuestionNum(String.valueOf(i) + ".");
			questions.add(test);
		}
		return "enter_questions.xhtml";
	}

	public String saveInDB() {
		JSONObject test = new JSONObject();
//		 JSONArray jArray = new JSONArray();
		for (Test test1 : questions) {
			test.put(test1.getQuestionNum(), test1.getQuestionDescription());
//			jArray.put(test1);
		}
//		 Iterator<Object> iterator = jArray.iterator();
//		 while (iterator.hasNext()) {
//		 Test test1 = (Test) iterator.next();
//		System.out.println(jArray.toString());
//		 }
		result = test.toString();
		return "registrationFormWhitTest.xhtml";
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

	public List<Test> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Test> questions) {
		this.questions = questions;
	}

	public String getSelectedNumOfQuestions() {
		return selectedNumOfQuestions;
	}

	public void setSelectedNumOfQuestions(String selectedNumOfQuestions) {
		this.selectedNumOfQuestions = selectedNumOfQuestions;
	}

	public String[] getNums() {
		return nums;
	}

	public void setNums(String[] nums) {
		this.nums = nums;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCathegory() {
		return cathegory;
	}

	public void setCathegory(String cathegory) {
		this.cathegory = cathegory;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getCompanyUserName() {
		return companyUserName;
	}

	public void setCompanyUserName(String companyUserName) {
		this.companyUserName = companyUserName;
	}
	

}
