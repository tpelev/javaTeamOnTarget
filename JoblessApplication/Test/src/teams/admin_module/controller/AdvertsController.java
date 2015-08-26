package teams.admin_module.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import model.Advertisement;
import model.Category;
import model.Place;

import pagination.PaginationHelper;

@ManagedBean(name = "adverts")
@SessionScoped
public class AdvertsController {

	

	// instance
	@EJB
	private AdvertsDaoBean advertsDaoBean;
	private PaginationHelper pagination;

	// fields
	private Advertisement advertisement;
	private String category;
	private String title;
	private String content;
	private String price;
	private String place;
	private boolean isVip;

	// Pagination
	@SuppressWarnings("rawtypes")
	private DataModel dtmdl = null;

	// getDtml generates dynamic table with info from dataBase .
	@SuppressWarnings("rawtypes")
	public DataModel getDtmdl() {
		if (dtmdl == null) {
			dtmdl = getPagination().createPageDataModel();
		}
		return dtmdl;
	}

	public PaginationHelper getPagination() {

		if (pagination == null) {

			pagination = new PaginationHelper(2) {
				@Override
				public int getItemsCount() {
					return advertsDaoBean.count();
				}

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public DataModel createPageDataModel() {

					return new ListDataModel(advertsDaoBean
							.findRange(new int[] { getPageFirstItem(), getPageFirstItem() + getPageSize() }));
				}
			};
		}
		return pagination;
	}

	private void recreateModel() {
		dtmdl = null;
	}

	public String next() {
		getPagination().nextPage();
		recreateModel();
		return "home";
	}

	public String previous() {
		getPagination().previousPage();
		recreateModel();
		return "home";
	}

	// END of Pagination

	// Redirects to new .xhtml with form for update and setting Object from
	// class Advertisement
	public String sendToUpdateAdv(Advertisement advertisement) {
		setAdvertisement(advertisement);
		return "AdminUpdateAdverts.xhtml";
	}

	// Passing parameters to query for update Advertisement and refreshing the
	// dataTable
	public String updateAv() {
		advertsDaoBean.updateeAdvs(getAdvertisement().getId(), getCategory(), getTitle(), getContent(),
				Double.valueOf(getPrice()), getPlace(), isVip());
		dtmdl = null;
		getDtmdl();
		return "AdminAdvertsPanel.xhtml";
	}

	// Delete Advertisement
	public void deleteAdvertisement(Advertisement adv) {
		advertsDaoBean.deleteAdvertisement(adv.getId());
		dtmdl = null;
		getDtmdl();
	}

	// Getting the object from dataTable and setting field "isVip" in dataBase
	// to true and refreshing the dataTable
	public void makeVip(Advertisement adv) {
		if (adv != null) {
			if (adv.getIsPaid()) {
				advertsDaoBean.makeVip(adv.getId());
				dtmdl = null;
				getDtmdl();
			} else {
				System.err.println("It is NOT PAID SORRY!");
			}
		}
	}

	// Approving Advertisement for publish.
	public void publishAdv(Advertisement adv) {
		if (adv != null) {
			if (adv.getIsApproved()) {
				return;
			} else {
				advertsDaoBean.publishAdd(adv.getId());
				dtmdl = null;
				getDtmdl();
			}
		}
	}

	// Checking if Advertisement is expired
	public void chekForExpire() {
		advertsDaoBean.chekForExpiredDate();
	}

	// Dynamically filling "selectOneMenu" with info from dataBase
	public List<SelectItem> getShowAllPlaces() {

		List<SelectItem> selectedItem = new ArrayList<SelectItem>();
		List<Place> listOfAllPlaces = advertsDaoBean.showAllPlaces();
		for (Place tempPlace : listOfAllPlaces) {
			selectedItem.add(new SelectItem(tempPlace.getPlacesName()));
		}
		return selectedItem;
	}

	// Dynamically filling "selectOneMenu" with info from dataBase
	public List<SelectItem> getShowAllCategories() {

		List<SelectItem> selectedItem = new ArrayList<SelectItem>();
		List<Category> listOfAllCategories = advertsDaoBean.showAllCategories();
		for (Category tempCategoria : listOfAllCategories) {
			selectedItem.add(new SelectItem(tempCategoria.getCategorieName()));
		}
		return selectedItem;
	}

	// Set and Get methods

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
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
	// End of Set and Get methods
}
