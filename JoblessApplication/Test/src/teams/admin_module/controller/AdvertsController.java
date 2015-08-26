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
	@EJB
	private AdvertsDaoBean advertsDaoBean;

	private List<Advertisement> list = new ArrayList<Advertisement>();

	private Advertisement advertisement;		

	private String category;
	private String title;
	private String content;

	private String price;
	private String place;
	private boolean flag = true;
	private boolean isVip;

	private PaginationHelper pagination;
	private int selectedItemIndex;
	@SuppressWarnings("rawtypes")
	private DataModel dtmdl = null;
	
	
	
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

                    return new ListDataModel(advertsDaoBean.findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    
    private void recreateModel() {
        dtmdl = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    private void updateCurrentItem() {
        int count = advertsDaoBean.count();
        if (selectedItemIndex >= count) {

            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;

            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {

            	pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
        	advertisement = advertsDaoBean.findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
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

	
	
	public List<Advertisement> getList() {
		
		if(flag){
			list = advertsDaoBean.showAllAdverts();
			return list;
			}
			else{
				list=advertsDaoBean.showForAprovalAds();
			return list;
			}
	}

	public void setList(List<Advertisement> list) {
		this.list = list;
	}

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

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public String sendToUpdateAdv(Advertisement advertisement) {
		setAdvertisement(advertisement);
		return "updateAdverts.xhtml";
	}

	public String updateAv() {
		advertsDaoBean.updateeAdvs(getAdvertisement().getId(), getCategory(), getTitle(), getContent(),
				Double.valueOf(getPrice()), getPlace(), isVip());
		dtmdl=null;
		getDtmdl();
		return "Adverts.xhtml";
	}

	public void makeVip(Advertisement adv) {
		if (adv != null) {
			if (adv.getIsPaid()) {
				advertsDaoBean.makeVip(adv.getId());
				dtmdl=null;
				getDtmdl();
			} else {
				System.err.println("It is NOT PAID SORRY!");
			}
		}
	}

	public void publishAdv(Advertisement adv) {
		if (adv != null) {
			if (adv.getIsApproved()) {
				return;
			} else {
				advertsDaoBean.publishAdd(adv.getId());
				dtmdl=null;
				getDtmdl();
			}
		}
	}

	public void chekForExpire() {
		advertsDaoBean.chekForExpiDate();
	}

	public List<SelectItem> getShowAllPlaces() {

		List<SelectItem> selectedItem = new ArrayList<SelectItem>();
		List<Place> listOfAllPlaces = advertsDaoBean.showAllPlaces();
		for (Place tempPlace : listOfAllPlaces) {
			selectedItem.add(new SelectItem(tempPlace.getPlacesName()));
		}
		return selectedItem;
	}

	public List<SelectItem> getShowAllCategories() {

		List<SelectItem> selectedItem = new ArrayList<SelectItem>();
		List<Category> listOfAllCategories = advertsDaoBean.showAllCategories();
		for (Category tempCategoria : listOfAllCategories) {
			selectedItem.add(new SelectItem(tempCategoria.getCategorieName()));
		}
		return selectedItem;
	}

}
