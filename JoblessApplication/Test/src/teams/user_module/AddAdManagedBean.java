package teams.user_module;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import model.Category;
import model.Place;
import model.Test;
import org.json.JSONArray;
import org.json.JSONObject;

@ManagedBean(name = "createAd")
@SessionScoped
public class AddAdManagedBean {

	@EJB
	private SessionBean query;
	public String companyUserName;
	private String selectedNumOfQuestions;
	private String[] nums = { "1", "2", "3", "4", "5", "6", "7" };
	private List<Test> questions;
	private String result;
	private String title;
	private String cathegory;
	private String content;
	private String country;
	private int days;
	private boolean isVip;
	private boolean vipWithTest;
	private List<String> errors;
	private String outcome;

	
	public AddAdManagedBean() {
		days = 30;
	}
	
	
	// Set Ad priority
	public String setVipWithTest() {
		setVip(true);
		setVipWithTest(true);
		return "choose_num_of_quest.xhtml?faces-redirect=true";
	}

	public String setVipWithoutTest() {
		setVip(true);
		setVipWithTest(false);
		return "create_new_ad.xhtml?faces-redirect=true";
	}

	public String setRegularAd() {
		setVip(false);
		setVipWithTest(false);
		return "create_new_ad.xhtml?faces-redirect=true";
	}

	
	// Ad Publishing Methods
	public String saveNewAd() {
		if (isValid()) {
			if (isVip() && isVipWithTest()) {
				query.createAdvertisement(getCompanyUserName(), getTitle(),
						getContent(), result, true, getCountry(),
						getCathegory());
			} else if (isVip() && !isVipWithTest()) {
				query.createAdvertisement(getCompanyUserName(), getTitle(),
						getContent(), "", true, getCountry(), getCathegory());
			} else {
				query.createAdvertisement(getCompanyUserName(), getTitle(),
						getContent(), "", false, getCountry(), getCathegory());
			}
			return "success_add_ad.xhtml?faces-redirect=true";
		}
		return "";
	}

	@SuppressWarnings("boxing")
	public String setQuestions() {
		questions = new ArrayList<Test>();
		for (int i = 1; i <= Integer.valueOf(selectedNumOfQuestions); i++) {
			Test test = new Test();
			test.setQuestionNum(String.valueOf(i) + ".");
			questions.add(test);
		}
		return "enter_questions.xhtml?faces-redirect=true";
	}

	public String saveInDB() {
		JSONArray jArray = new JSONArray();
		for (Test test : questions) {
			JSONObject j = new JSONObject();
			j.put("questionNum", test.getQuestionNum());
			j.put("questionDescription", test.getQuestionDescription());
			jArray.put(j);
		}
		result = jArray.toString();
		return "create_new_ad.xhtml?faces-redirect=true";
	}

	
	//Validation
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
		if (title == null || title.isEmpty() || title.length()>99) {
			errors.add("You should write a title with less than 99 symbols \n");
			errorFound = true;
		}
		if (days < 5 || days > 30) {
			errors.add("Invalid days input. Should be between 5 and 30 days \n");
			errorFound = true;
		}
		if (content == null || content.isEmpty()) {
			errors.add("Ad content cannot be empty \n");
		}
		return !errorFound;
	}

	
	// Setters and Getters
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
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public boolean isVipWithTest() {
		return vipWithTest;
	}

	public void setVipWithTest(boolean vipWithTest) {
		this.vipWithTest = vipWithTest;
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

}
