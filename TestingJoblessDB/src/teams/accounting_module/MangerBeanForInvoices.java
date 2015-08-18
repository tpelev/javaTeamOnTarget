package teams.accounting_module;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import model.entity.Advertisement;
import model.entity.CompanyProfile;
import model.entity.Invoice;

@ManagedBean(name = "invoiceBean")
public class MangerBeanForInvoices {

	@EJB
	private InvoiceEJB ejbInvoice;
	@EJB
	private AdvertisementEJB ejbAdvertisements;
	private CompanyProfile companyProfile;
	private List<Invoice> invoiceList = new ArrayList<>();
	private List<CompanyProfile> compList = new ArrayList<CompanyProfile>();
	private List<Advertisement> advertisementList = new ArrayList<>();
	private String day;
	private String month;
	private String year;
	private String dayEnd;
	private String monthEnd;
	private String yearEnd;
	private Invoice invoiceObj;
	private String selectedItem;
	private String selectedItemAdv;
	private String totalPrice;
	private String discount;


	// add All invoices to List
	public List<Invoice> getAllInvoices() {
		invoiceList = ejbInvoice.showAllInvoices();
		return invoiceList;
	}

	// add All invoices by day to List
	public List<Invoice> allInvoicesByDay() {
		invoiceList = ejbInvoice.showAllInvoicesByDay(getDayString(getDay(),
				getMonth(), getYear()));
		return invoiceList;
	}

	// add All invoices by Month to List
	public List<Invoice> allInvoicesByMonth() {
		invoiceList = ejbInvoice.showAllInvoicesByMonth(Integer.valueOf(month),
				Integer.valueOf(year));
		return invoiceList;
	}

	// add All invoices by Period to List
	public List<Invoice> allInvoicesByPeriod() {
		String startDay = getDayString(day, month, year);
		String endDay = getDayString(dayEnd, monthEnd, yearEnd);
		invoiceList = ejbInvoice.showAllInvoicesByPeriod(startDay, endDay);
		return invoiceList;
	}

	// add All invoices by company to List
	public List<Invoice> allInvoicesByCompany() {
		String[] temp = selectedItem.split(" ");
		int id = Integer.valueOf(temp[0]);

		invoiceList = ejbInvoice.showAllInvoicesByCompani(id);

		return invoiceList;
	}

	// add All invoices by Expecting payment
	public List<Invoice> getAllInvoicesByExpectingPayments() {
		invoiceList = ejbInvoice.showAllExpectingPayments();
		return invoiceList;
	}

	// add All invoices by Late payment
	public List<Invoice> getAllInvoicesByLatePayments() {
		invoiceList = ejbInvoice.showAllLatePayments();
		return invoiceList;
	}

	// add invoice
	public void addInvoice() {
		ejbInvoice.addInvoice(invoiceObj.getAdvId(), invoiceObj.getOwner(),
				invoiceObj.getCompanyProfile(), invoiceObj.getDiscount(),
				invoiceObj.getIsPayed(), invoiceObj.getIsCash());
	}

	// add invoice
	public void updateInvoice() {
		ejbInvoice.updateInvoiceByIsPayed(invoiceObj, invoiceObj.getIsPayed());
	}

	// to refresh the xhtml page
	public String showAllInvoices() {
		invoiceList = ejbInvoice.showAllInvoices();
		return "Companies.xhtml";
	}

	// method for dynamic Select One Menu for CompanyProfiles
	public List<SelectItem> getShowAllCompaniesSelectOneMenu() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		List<CompanyProfile> companies = ejbInvoice.showAllCompanies();
		for (CompanyProfile comp : companies) {
			items.add(new SelectItem(comp));
		}
		return items;
	}
	
	// method for dynamic Select One Menu for Advertisements
	public List<SelectItem> getShowAllAdvertisementsSelectOneMenu() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		CompanyProfile compObj = toCompanyByID();
		List<Advertisement> adverts = ejbAdvertisements.showAllVipNonPayedAdvertisement(compObj.getId());
		for (Advertisement adv : adverts) {
			items.add(new SelectItem(adv));
		}
		return items;
	}

	// get CompanyProfile by id after Select One menu
	public CompanyProfile toCompanyByID() {
		String[] temp = selectedItem.split(" ");
		int id = Integer.valueOf(temp[0]);

		companyProfile = ejbInvoice.allCompaniesByName(id);
		return companyProfile;
	}

	public String calculateTotalPrice() {
		double disc = Double.valueOf(discount);
		
		double discAmount = (100 + 20)*disc;
		totalPrice = String.valueOf((100 + 20) - discAmount);
		return totalPrice;
	}

	private String getDayString(String dayT, String monthT, String yearT) {
		if (monthT.length() == 1) {
			monthT = "0" + monthT;
		}
		if (dayT.length() == 1) {
			dayT = "0" + dayT;
		}
		String finalDayString = yearT + "-" + monthT + "-" + dayT;
		return finalDayString;
	}

	//Getters And Setters for fields...
	public String goTo() {
		return getSelectedItem();
	}

	public InvoiceEJB getEjbInvoice() {
		return ejbInvoice;
	}

	public void setEjbInvoice(InvoiceEJB ejbInvoice) {
		this.ejbInvoice = ejbInvoice;
	}

	public CompanyProfile getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(CompanyProfile companyProfile) {
		this.companyProfile = companyProfile;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDayEnd() {
		return dayEnd;
	}

	public void setDayEnd(String dayEnd) {
		this.dayEnd = dayEnd;
	}

	public String getMonthEnd() {
		return monthEnd;
	}

	public void setMonthEnd(String monthEnd) {
		this.monthEnd = monthEnd;
	}

	public String getYearEnd() {
		return yearEnd;
	}

	public void setYearEnd(String yearEnd) {
		this.yearEnd = yearEnd;
	}

	public Invoice getInvoiceObj() {
		return invoiceObj;
	}

	public void setInvoiceObj(Invoice invoiceObj) {
		this.invoiceObj = invoiceObj;
	}

	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public List<CompanyProfile> getCompList() {
		return compList;
	}

	public void setCompList(List<CompanyProfile> compList) {
		this.compList = compList;
	}
	
	public String getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}

	
	public AdvertisementEJB getEjbAdvertisements() {
		return ejbAdvertisements;
	}

	
	public void setEjbAdvertisements(AdvertisementEJB ejbAdvertisements) {
		this.ejbAdvertisements = ejbAdvertisements;
	}

	
	public List<Advertisement> getAdvertisementList() {
		return advertisementList;
	}

	
	public void setAdvertisementList(List<Advertisement> advertisementList) {
		this.advertisementList = advertisementList;
	}

	
	public String getSelectedItemAdv() {
		return selectedItemAdv;
	}

	
	public void setSelectedItemAdv(String selectedItemAdv) {
		this.selectedItemAdv = selectedItemAdv;
	}
		
}
