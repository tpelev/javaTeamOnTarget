package teams.user_module;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

//import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

import org.json.JSONObject;

import model.Advertisement;
import model.Category;
import model.CompanyProfile;
import model.Place;
import model.Test;


@ManagedBean(name = "index")
@SessionScoped
public class indexBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1854159341673746964L;

//	@ManagedProperty("#{login}")
//	private User user;

	@EJB
	private TestSessionBean t;
	
	private List<Advertisement> ads= new ArrayList<>();
	
//	@Resource(name ="mailSession")
//    private Session mailSession;
//	
	private List<Test> questions;
	
	private Advertisement selected;
	private String keyword;
	private String countryId;
	private String companyId;
	private String categoryId;
	private boolean isVip;
	public List<String> string;
	public String[] test;
	public boolean isLogged;
	
	
	
	
	
	public void setTest(String[] test) {
		this.test = test;
	}

	public List<SelectItem> getShowAllCompany() {

		List<SelectItem> items = new ArrayList<SelectItem>();
		List<CompanyProfile> company = t.allCompanies();
		for (CompanyProfile comp : company) {
			items.add(new SelectItem(comp.getCompanyName()));
		}
		return items;
	}

	public List<SelectItem> getShowAllPlaces() {

		List<SelectItem> items = new ArrayList<SelectItem>();
		List<Place> places = t.allPlaces();
		for (Place place : places) {
			items.add(new SelectItem(place.getPlacesName()));
		}
		return items;
	}

	public List<SelectItem> getShowAllCategories() {

		List<SelectItem> items = new ArrayList<SelectItem>();
		List<Category> categorys = t.allCategories();
		for (Category category : categorys) {
			items.add(new SelectItem(category.getCategorieName()));
		}
		return items;
	}

	public void search(){
		setAds(t.searchAds(getKeyword(), getCountryId(), getCategoryId(), getCompanyId(), String.valueOf(isVip)));
	}
	public void showAllAdds(){
		setAds(t.showAllAdAproved());
	}

	public List<Advertisement> getAds() {
		return ads;
	}

	public void setAds(List<Advertisement> ads) {
		this.ads = ads;
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

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

//	public String logIn() {
//		user.setLogged(true);
//		return "cool";
//	}
//
//	public String view() {
//		if (selected.getIsVip() && !user.isLogged()) {
//			return "index.xhtml";
//		}
//		return "view.xhtml";
//	}
	
	
	
	public String selectAd(){
		if(selected.getIsVip() && selected.getTest()!=null && isLogged){
			String test = selected.getTest();
			
			
//			JSONArray jArray = new JSONArray(test);
			questions = new ArrayList<Test>();
			JSONObject json= new JSONObject(test);
			for (int i = 0; i < json.length(); i++) {
				Test t = new Test();
				t.setQuestionNum(String.valueOf(i+1));
				t.setQuestionDescription(json.getString(String.valueOf(i+1)+"."));
				questions.add(t);
			}
//		    for (int i = 0; i < jArray.length(); i++) {
//		        JSONObject explrObject = (JSONObject) jArray.getJSONObject(i);
//		        Test t = new Test();
//		        t.setQuestionNum(explrObject.getString("questionNum"));
//		        t.setQuestionDescription(explrObject.getString("questionDescription").toString());
//		        questions.add(t);
//		}    
			return "enter_anwers.xhtml";
		}
		else if (isLogged) {
			return "view.xhtml";
		}
		else{
		return "user.xhtml";
		}
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

	public List<Test> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Test> questions) {
		this.questions = questions;
	}
//	public String sendTest(){
//		
//		String companyEmail = selected.getCompanyProfile().getEmail();
//		
//		StringBuilder sb = new StringBuilder();
//		for (Test test : questions) {
//			sb.append(test.getQuestionNum()+test.getQuestionDescription()+"\n"+test.getAnswer()+"\n");
//		}
//		  String to = companyEmail;//change accordingly  
//	      String from = "cvetomir.andrev@gmail.com";//change accordingly  
//	     //String host = "localhost";//or IP address
//	      try{  
//	    	  	
//	    		 Message message = new MimeMessage(mailSession);
//		         message.setFrom(new InternetAddress(from));  
//		         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
//		         message.setSubject("Test");  
//		         message.setText("Uaer Name\n"+sb.toString());  	      
//		         // Send message  
//		         Transport.send(message);
//		         System.out.println("message sent successfully....");  
//		         
//		      }catch (MessagingException mex) {
//		    	  mex.printStackTrace();}  
//		return "index.xhtml";
//	}
	

}
