package teams.accounting_module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import model.Advertisement;
import model.CompanyProfile;
import model.Invoice;
import model.Owner;
import pagination.EjbInvoiceItem;
import pagination.PaginationHelper;


@ManagedBean(name = "invoiceBean")
public class MangerBeanForInvoices {

	@EJB
	private InvoiceEJB ejbInvoice;
	@EJB
	private AdvertisementEJB ejbAdvertisements;
	@EJB
	private OwnerEJB ejbOwner;
	@EJB
	private EjbInvoiceItem itembean;
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
	private int idComp;
	private String isCash;
	private boolean isCashPaid;
	private String isPaid;
	private boolean isPaidBoolean;
	private String fileName;
	private String mssg;
	private PaginationHelper pagination;
	private int selectedItemIndex;
	@SuppressWarnings("rawtypes")
	private DataModel dtmdl = null;
	private Invoice invoice;

	// constructor
	public MangerBeanForInvoices() {
		this.ejbInvoice = new InvoiceEJB();
		this.ejbAdvertisements = new AdvertisementEJB();
		this.companyProfile = new CompanyProfile();
		this.invoiceList = new ArrayList<Invoice>();
		this.compList = new ArrayList<CompanyProfile>();
		this.advertisementList = new ArrayList<Advertisement>();
		this.invoiceObj = new Invoice();
		this.ejbOwner = new OwnerEJB();
	}

	// add All invoices to List
	public List<Invoice> getAllInvoices() {
		setInvoiceList(ejbInvoice.showAllInvoices());
		return getInvoiceList();
	}

	// add All invoices by day to List
	public List<Invoice> allInvoicesByDay() {
		setInvoiceList(ejbInvoice.showAllInvoicesByDay(getDayString(getDay(), getMonth(), getYear())));
		return getInvoiceList();
	}

	// add All invoices by Month to List
	public List<Invoice> allInvoicesByMonth() {
		setInvoiceList(ejbInvoice.showAllInvoicesByMonth(Integer.valueOf(month), Integer.valueOf(year)));
		return getInvoiceList();
	}

	// add All invoices by Period to List
	public List<Invoice> allInvoicesByPeriod() {
		String startDay = getDayString(day, month, year);
		String endDay = getDayString(dayEnd, monthEnd, yearEnd);
		setInvoiceList(ejbInvoice.showAllInvoicesByPeriod(startDay, endDay));
		return getInvoiceList();
	}

	// add All invoices by company to List
	public List<Invoice> allInvoicesByCompany() {
		String[] temp = selectedItem.split(" ");
		int id = Integer.valueOf(temp[0]);

		setInvoiceList(ejbInvoice.showAllInvoicesByCompani(id));
		setIdComp(id);
		return getInvoiceList();
	}

	// get All invoices by Expecting payment
	public List<Invoice> getAllInvoicesByExpectingPayments() {
		setInvoiceList(ejbInvoice.showAllExpectingPayments());
		return getInvoiceList();
	}

	// get All invoices by Late payment
	public List<Invoice> getAllInvoicesByLatePayments() {
		setInvoiceList(ejbInvoice.showAllLatePayments());
		return getInvoiceList();
	}

	// add new Invoice
	public void addInvoice() {
		calculateTotalPrice();
		choiceCash();
		choicePaid();

		String[] temp = selectedItem.split(" ");
		int id = Integer.valueOf(temp[0]);

		CompanyProfile compTemp = ejbInvoice.allCompaniesByName(id);
		Date evDate = ejbInvoice.formatStringToDate(getDayString(day, month, year));
		double totPrice = Double.valueOf(totalPrice);
		double disc = Double.valueOf(discount);
		Owner owner = ejbOwner.getOwnerInformation(1);

		Invoice invoiceTemp = new Invoice();
		invoiceTemp.setIsCash(isCashPaid);
		invoiceTemp.setIsPayed(isPaidBoolean);
		invoiceTemp.setTotalPrice(totPrice);
		invoiceTemp.setEventDate(evDate);
		invoiceTemp.setDiscount(disc);
		invoiceTemp.setCompanyProfile(compTemp);
		invoiceTemp.setOwner(owner);

		String[] tempAdv = selectedItemAdv.split(" ");
		int idAdv = Integer.valueOf(tempAdv[0]);
		invoiceTemp.setAdvId(idAdv);
		ejbInvoice.addInvoice(invoiceTemp);
		ejbAdvertisements.updateAdvertToHasInvoice(idAdv);
	}

	// update invoice
	public void updateInvoice(Invoice invoice) {
		if (invoice.getIsPayed()) {
			return;
		} else {
			int temp = invoice.getId();
			int idAdv = invoice.getAdvId();
			ejbInvoice.updateInvoiceByIsPayed(temp);
			ejbAdvertisements.updateAdvertToIsPayed(idAdv);
		}

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
		List<Advertisement> adverts = ejbAdvertisements.showAllVipNonPayedAdvertisement(getIdComp());
		for (Advertisement adv : adverts) {
			items.add(new SelectItem(adv));
		}
		return items;
	}

	// get CompanyProfile by id after Select One menu
	public void toCompanyByID() {
		String[] temp = selectedItem.split(" ");

		int id = Integer.valueOf(temp[0]);
		setCompanyProfile(ejbInvoice.allCompaniesByName(id));
		setIdComp(companyProfile.getId());
	}

	// calculating total price
	public void calculateTotalPrice() {
		double disc = Double.valueOf(discount);
		double discAmount = (100 + 20) * disc;
		setTotalPrice(String.valueOf((100 + 20) - discAmount));
	}

	// create a date(String format) from all fields
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

	// from string to boolean Cash
	private void choiceCash() {
		if (isCash.equalsIgnoreCase("true")) {
			setCashPaid(true);
		} else {
			setCashPaid(false);
		}
	}

	// from string to boolean Paid
	private void choicePaid() {
		if (isPaid.equalsIgnoreCase("true")) {
			setPaidBoolean(true);
		} else {
			setPaidBoolean(false);
		}
	}

	
	// Methods needed for export filters result to excel
	public void exportToExcelShowAll() {
		Export export = new Export(getFileName());
		generateMssgForFile(export.generateSimpleExcelReport(getAllInvoices()));
	}

	public void exportToExcelByCompany() {
		Export export = new Export(getFileName());
		generateMssgForFile(export.generateSimpleExcelReport(allInvoicesByCompany()));
	}

	public void exportToExcelByDay() {
		Export export = new Export(getFileName());
		generateMssgForFile(export.generateSimpleExcelReport(allInvoicesByDay()));
	}

	public void exportToExcelByMonth() {
		Export export = new Export(getFileName());
		generateMssgForFile(export.generateSimpleExcelReport(allInvoicesByMonth()));
	}

	public void exportToExcelByPeriod() {
		Export export = new Export(getFileName());
		generateMssgForFile(export.generateSimpleExcelReport(allInvoicesByPeriod()));
	}

	public void exportToExcelByDuePay() {
		Export export = new Export(getFileName());
		generateMssgForFile(export.generateSimpleExcelReport(getAllInvoicesByLatePayments()));
	}

	public void exportToExcelByExpectingPayments() {
		Export export = new Export(getFileName());
		generateMssgForFile(export.generateSimpleExcelReport(getAllInvoicesByExpectingPayments()));
	}

	// method to generate a message for result of Export
	private void generateMssgForFile(boolean successExport) {
		if (successExport) {
			setMssg(fileName + ".xls has been successfully written to Desktop");
		} else {
			setMssg(fileName + ".xls has NOT been successfully written to Desktop");
		}
	}

	
	//method needed for pagination
	public PaginationHelper getPagination() {

		if (pagination == null) {

			pagination = new PaginationHelper(10) {
				@Override
				public int getItemsCount() {
					return itembean.count();
				}

				@SuppressWarnings({ "rawtypes", "unchecked" })
				@Override
				public DataModel createPageDataModel() {

					return new ListDataModel(
							itembean.findRange(new int[] { getPageFirstItem(), getPageFirstItem() + getPageSize() }));
				}
			};
		}
		return pagination;
	}

	@SuppressWarnings("rawtypes")
	public DataModel getdtmdl() {
		if (dtmdl == null) {
			dtmdl = getPagination().createPageDataModel();
		}
		return dtmdl;
	}

	private void recreateModel() {
		dtmdl = null;
	}

	@SuppressWarnings("unused")
	private void recreatePagination() {
		pagination = null;
	}

	@SuppressWarnings("unused")
	private void updateCurrentItem() {
		int count = itembean.count();
		if (selectedItemIndex >= count) {

			// selected index cannot be bigger than number of items:
			selectedItemIndex = count - 1;

			// go to previous page if last page disappeared:
			if (pagination.getPageFirstItem() >= count) {

				pagination.previousPage();
			}
		}
		if (selectedItemIndex >= 0) {
			invoice = itembean.findRange(new int[] { selectedItemIndex, selectedItemIndex + 1 }).get(0);
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
	

	// Getters And Setters for fields...
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

	public int getIdComp() {
		return idComp;
	}

	public void setIdComp(int idComp) {
		this.idComp = idComp;
	}

	public String getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}

	public String getIsCash() {
		return isCash;
	}

	public void setIsCash(String isCash) {
		this.isCash = isCash;
	}

	public boolean isCashPaid() {
		return isCashPaid;
	}

	public void setCashPaid(boolean isCashPaid) {
		this.isCashPaid = isCashPaid;
	}

	public boolean isPaidBoolean() {
		return isPaidBoolean;
	}

	public void setPaidBoolean(boolean isPaidBoolean) {
		this.isPaidBoolean = isPaidBoolean;
	}

	public OwnerEJB getEjbOwner() {
		return ejbOwner;
	}

	public void setEjbOwner(OwnerEJB ejbOwner) {
		this.ejbOwner = ejbOwner;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMssg() {
		return mssg;
	}

	public void setMssg(String mssg) {
		this.mssg = mssg;
	}

	
	public Invoice getInvoice() {
		return invoice;
	}

	
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	
}
