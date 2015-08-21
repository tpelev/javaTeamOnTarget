package teams.admin_module.controler;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import model.Advertisement;
import model.Place;
@ManagedBean(name = "BeenAdv")
@SessionScoped
public class ManagerBeanForADS {
	@EJB
	private EJBAdsUsers ejbCompanies;

	private List<Advertisement> AdvertisementsList = new ArrayList<Advertisement>();

	private Advertisement advertisement;
	private String category;
	private String title;
	private String content;

	private String price;
	private String place;
	private boolean flag = true;
	private boolean isVip;

	
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public void flagFalse(){
		setFlag(false);
	}
	public void flagTrue(){
		setFlag(true);
	}

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

		List<SelectItem> selectedItem = new ArrayList<SelectItem>();
		List<Place> listOfAllPlaces = ejbCompanies.showAllPlaces();
		for (Place tempPlace : listOfAllPlaces) {
			selectedItem.add(new SelectItem(tempPlace.getPlacesName()));
		}
		return selectedItem;
	}
	
	public List<SelectItem> getShowAllCategories() {

		List<SelectItem> selectedItem = new ArrayList<SelectItem>();
		List<model.Category> listOfAllCategories = ejbCompanies.showAllCategories();
		for (model.Category tempCategoria : listOfAllCategories) {
			selectedItem.add(new SelectItem(tempCategoria.getCategorieName()));
		}
		return selectedItem;
	}

	public void showAllAds(){
		AdvertisementsList = ejbCompanies.showAllAdvs();
	}
	
	public List<Advertisement> getAdvsList() {
		if(flag){
		AdvertisementsList = ejbCompanies.showAllAdvs();
		return AdvertisementsList;
		}
		else{
			AdvertisementsList=ejbCompanies.showForAprovalAds();
			return AdvertisementsList;
		}
		
	}

	public void deleteAdvs(Advertisement adv) {
		if(adv!=null)
		ejbCompanies.deleteAdvs(adv.getId());

	}
	public void publishAdv(Advertisement adv){
		if(adv!=null){
		if(adv.getIsApproved()){
			return;
		}
		else {
			ejbCompanies.publishAdd(adv.getId());
		}
		}
	}

	public void updateAdvs(Advertisement adv) {
		if(adv!=null)
		ejbCompanies.updateeAdvs(adv.getId(), getCategory(), getTitle(), getContent(), Double.valueOf(getPrice()), getPlace(), Boolean.valueOf(isVip()));
	}
	
	public void chekForExpire(){
		ejbCompanies.chekForExpiDate();
	}
	
	public void allAdFroApproval(){
	AdvertisementsList=ejbCompanies.showForAprovalAds();
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}
	
	public void makeVip(Advertisement adv){
		if(adv!=null){
		if(adv.getIsPaid()){
			ejbCompanies.makeVip(adv.getId());
		}
		else{
			System.err.println("It is NOT PAID SORRY!");
		}
		}
	}

}
