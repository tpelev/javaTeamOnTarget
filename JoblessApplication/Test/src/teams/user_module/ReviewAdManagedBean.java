package teams.user_module;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.Advertisement;
import model.Category;
import model.CompanyProfile;
import model.Place;
import model.Test;
import model.UserProfile;

import org.json.JSONArray;
import org.json.JSONObject;

@ManagedBean(name = "index")
@SessionScoped
public class ReviewAdManagedBean {

	@EJB
	private QueriesEJB queries;
	@Resource(name = "mailSession")
	private Session mailSession;
	// logIn
	public boolean isLogged;
	private String userName;

	// selectOneMenu lists
	private List<Place> places;
	private List<CompanyProfile> companies;
	private List<Category> categories;

	// pages
	private List<Advertisement> currentAds; // ads on page
	private static final int RECORDS = 10; // number of ads on page
	private int pageIndex; // current page
	private int recordsTotal; // all ads
	private int pages; // all pages
	private boolean isNextDisable;
	private boolean isPrevDisable;

	// select Ad
	private Advertisement selected;
	private String keyword = "";
	private String selectedPlace = "All";
	private String selectedCompany = "All";
	private String selectedCategory = "All";
	private boolean isVip;

	// email data
	private List<Test> questions;
	private String cv;
	private String cl;

	// end - select ad

	public boolean isNextDisable() {
		return isNextDisable;
	}

	public void setNextDisable(boolean isNextDisable) {
		this.isNextDisable = isNextDisable;
	}

	public boolean isPrevDisable() {
		return isPrevDisable;
	}

	public void setPrevDisable(boolean isPrevDisable) {
		this.isPrevDisable = isPrevDisable;
	}

	public List<Advertisement> getCurrentAds() {
		return currentAds;
	}

	public void setCurrentAds(List<Advertisement> currentAds) {
		this.currentAds = currentAds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Test> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Test> questions) {
		this.questions = questions;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	public List<CompanyProfile> getCompanies() {
		return companies;
	}

	public void setCompanies(List<CompanyProfile> companies) {
		this.companies = companies;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Advertisement getSelected() {
		return selected;
	}

	public void setSelected(Advertisement selected) {
		this.selected = selected;
	}

	public String doNothing() {
		return null;
	}

	public String getSelectedPlace() {
		return selectedPlace;
	}

	public void setSelectedPlace(String selectedPlace) {
		this.selectedPlace = selectedPlace;
	}

	public String getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(String selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public String getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public String getCl() {
		return cl;
	}

	public void setCl(String cl) {
		this.cl = cl;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

	// End of setters and getters

	/**
	 * A Method Called after the constructor and injections being done. Post
	 * Construct methods have void arguments and return void. Method may be
	 * public, protected, private, or package private. If the method throws an
	 * unchecked exception, the JSF implementation must not put the managed bean
	 * into service and no further methods on that managed bean instance will be
	 * called.
	 * 
	 * @author Damain
	 */

	@PostConstruct
	public void init() {
		Place p = new Place();
		p.setPlacesName("All");
		places = queries.allPlaces();
		places.add(p);
		CompanyProfile cp = new CompanyProfile();
		cp.setCompanyName("All");
		companies = queries.allCompanies();
		companies.add(cp);
		Category category = new Category();
		category.setCategorieName("All");
		categories = queries.allCategories();
		categories.add(category);

		initFirstPage();
	}

	// changing pages
	/**
	 * Method showing the initial ads.
	 * 
	 * @author Zlatina
	 */

	public void initFirstPage() {
		pageIndex = 1;
		recordsTotal = (int) queries.countAds(keyword, selectedPlace,
				selectedCategory, selectedCompany, isVip);
		pages = recordsTotal / RECORDS;

		if (recordsTotal % RECORDS > 0) {
			pages++;
		}
		if (pages == 0) {
			pages = 1;
		}

		if ((pageIndex == pages) && (pageIndex == 1)) {
			setPrevDisable(true);
			setNextDisable(true);
		} else {
			setPrevDisable(true);
			setNextDisable(false);
		}
		updateModel();
	}

	/**
	 * Updates the list, which is visualized on the main page.
	 */
	private void updateModel() {
		int fromIndex = getFirst();
		currentAds = queries.searchAdsOnCurrentPage(keyword, selectedPlace,
				selectedCategory, selectedCompany, isVip, fromIndex, RECORDS);
	}

	/**
	 * Moves to the next page and updates the list
	 */
	public void next() {
		if (pageIndex < pages) {
			pageIndex++;
			setPrevDisable(false);
			if (this.pageIndex == pages) {
				setNextDisable(true);
			}
		}
		updateModel();
	}

	/**
	 * Moves to the previous page and updates the list
	 */
	public void prev() {
		if (pageIndex > 1) {
			pageIndex--;
			setNextDisable(false);
			if (pageIndex == 1) {
				setPrevDisable(true);
			}
		}
		updateModel();
	}

	private int getFirst() {
		return (pageIndex * RECORDS) - RECORDS;
	}

	// end changing pages

	/**
	 * Fills the test of the ad by taking the questions from the database
	 * 
	 * @author Zlatina
	 */
	private void fillTest() {
		String test = selected.getTest();

		JSONArray jArray = new JSONArray(test);
		questions = new ArrayList<Test>();

		for (int i = 0; i < jArray.length(); i++) {
			JSONObject explrObject = jArray.getJSONObject(i);
			Test t = new Test();
			t.setQuestionNum(explrObject.getString("questionNum"));
			t.setQuestionDescription(explrObject
					.getString("questionDescription"));
			questions.add(t);
		}
	}

	/**
	 * Select the ad view based on user status and ad type.
	 * 
	 * @return The corresponding view. guest_view_ad.xhtml if the user is a
	 *         Guest advVipViewTest.xhtml.xhtml is returned if the user is
	 *         logged and the ad has a test. advLoggedView.xhtml is returned if
	 *         the user is logged and the ad doesn't have a test.
	 * 
	 * @author Martin
	 */
	public String selectAd() {
		if (!isLogged) {
			return "advGuestView.xhtml?faces-redirect=true";
		}
		if (selected.getIsVip() && !selected.getTest().equals("")) {
			fillTest();
			return "advVipViewTest.xhtml?faces-redirect=true";
		}
		return "advLoggedView.xhtml?faces-redirect=true";
	}

	/**
	 * Sends the answers written by the user to the company via email
	 * 
	 * @return The corresponding view. advEmailSentError.xhtml will be returned
	 *         if there was an error. advEmailSentSuccessfully.xhtml if
	 *         everything went successfully
	 * 
	 * @author Metodi
	 */
	public String sendTest() {

		String companyEmail = selected.getCompanyProfile().getEmail();
		UserProfile userProf = queries.getUserProfileByUsername(getUserName());

		String to = companyEmail;
		String from = "awwwwws@gmail.com";

		StringBuilder sb = new StringBuilder();
		sb.append("Name: " + userProf.getFirstName() + " "
				+ userProf.getLastName() + "\n");
		sb.append("Email: " + userProf.getEmail() + "\n");
		sb.append("TEST: \n");
		for (Test test : questions) {
			sb.append(test.getQuestionNum() + test.getQuestionDescription()
					+ "\n" + test.getAnswer() + "\n");
		}
		if (!cv.isEmpty()) {
			sb.append("CV: \n");
			sb.append(cv + "\n");
		}
		if (!cl.isEmpty()) {
			sb.append("CL: \n");
			sb.append(cl + "\n");
		}
		try {
			sendEmail("JOBLESS - Ad ID: " + selected.getId(), sb.toString(),
					from, to);
		} catch (MessagingException mex) {
			return "advEmailSentError.xhtml?faces-redirect=true";
		}
		return "advEmailSentSuccessfully.xhtml?faces-redirect=true";
	}

	/**
	 * Sends cover letter and CV to the company with the data added by the user
	 * 
	 * @return The corresponding view. advEmailSentError.xhtml will be returned
	 *         if there was an error. advEmailSentSuccessfully.xhtml if
	 *         everything went successfully
	 * 
	 * @author Sneja
	 */
	public String sendCVandCL() {
		String companyEmail = selected.getCompanyProfile().getEmail();
		UserProfile userProf = queries.getUserProfileByUsername(getUserName());

		String to = companyEmail;
		String from = "awwwwws@gmail.com";

		StringBuilder sb = new StringBuilder();
		sb.append("Name: " + userProf.getFirstName() + " "
				+ userProf.getLastName() + "\n");
		sb.append("Email: " + userProf.getEmail() + "\n");
		if (!cv.isEmpty()) {
			sb.append("CV: \n");
			sb.append(cv + "\n");
		}
		if (!cl.isEmpty()) {
			sb.append("CL: \n");
			sb.append(cl + "\n");
		}
		try {
			sendEmail("JOBLESS - Ad ID: " + selected.getId(), sb.toString(),
					from, to);
		} catch (MessagingException mex) {
			return "advEmailSentError.xhtml?faces-redirect=true";
		}
		return "advEmailSentSuccessfully.xhtml?faces-redirect=true";
	}

	/**
	 * Sends an email form the specified sender with the given subject and text
	 * to the specified receiver
	 * 
	 * @param subject
	 * @param text
	 * @param from
	 * @param to
	 * @throws MessagingException
	 * 
	 * @author Damian
	 */
	private void sendEmail(String subject, String text, String from, String to)
			throws MessagingException {
		Message message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setText(text);
		Transport.send(message);
		System.out.println("message sent successfully....");
	}

}
