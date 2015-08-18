package teams.accounting_module;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import model.entity.CompanyProfile;
import model.entity.Invoice;

@ManagedBean(name = "invoiceBean")
public class MangerBeanForInvoices {

	@EJB
	private InvoiceEJB ejbInvoice;
	private CompanyProfile companyProfile;
	private List<Invoice> invoiceList = new ArrayList<>();
	private List<CompanyProfile> compList = new ArrayList<CompanyProfile>();
	private String day;
	private String month;
	private String year;
	private String dayEnd;
	private String monthEnd;
	private String yearEnd;
	private Invoice invoiceObj;
	private String selectedItem;

	public String getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}

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
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		//takeCompaniesProfile();
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + companyProfile.getId());
		int id = companyProfile.getId();
//		int id = Integer.valueOf(selectedItem.indexOf(0));
		System.out.println(id + "JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
		invoiceList = ejbInvoice.showAllInvoicesByCompani(id);
		System.out.println("============================================================================================" + invoiceList.size());
		for (CompanyProfile companyProfile : compList) {
			System.out.println(companyProfile + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		
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
		// invoiceList = ejbInvoice.showAllLatePayments();
		// invoiceList = ejbInvoice.showAllExpectingPayments();
		return "Companies.xhtml";
	}

	// get All Companies Profiles
//	private void takeCompaniesProfile() {
//		
//		compList = ejbInvoice.showAllCompaniesByName(getSelectedItem());
//		setCompanyProfile(compList.get(0));
//	}

	// method for dynamic Select One Menu
	public List<SelectItem> getShowAllCompaniesSelectOneMenu() {

		List<SelectItem> items = new ArrayList<SelectItem>();
		List<CompanyProfile> companies = ejbInvoice.showAllCompanies();
		for (CompanyProfile comp : companies) {
			items.add(new SelectItem(comp));
		}
		return items;
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

}
