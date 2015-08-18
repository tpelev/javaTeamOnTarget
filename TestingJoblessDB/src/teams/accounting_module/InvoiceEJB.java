package teams.accounting_module;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.entity.CompanyProfile;
import model.entity.Invoice;
import model.entity.Owner;

@Singleton
public class InvoiceEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	//show all invoices
	public List<Invoice> showAllInvoices(){
		List<Invoice> invList = null;
		
		Query query = entityManager.createNamedQuery("Invoice.findAll");
		invList = query.getResultList();		
		return invList;
	}
	
	
	
	//get invoices by day
	public List<Invoice> showAllInvoicesByDay(String invDate){
		List<Invoice> invList = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(invDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Query query = entityManager.createNamedQuery("Invoice.findByInvoiceDate");
		query.setParameter("invoiceDate", date);
		
		invList = query.getResultList();
		
		return invList;
	}
	
	//get invoices by month
	public List<Invoice> showAllInvoicesByMonth(int month, int year){
		List<Invoice> invList = null;
		String endDate = getLastDayFromMonth(month, year);
		String firstDate = getFirstDayOfMonth(month, year);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date firDate = null;
		Date lastDate = null;
		try {
			firDate = dateFormat.parse(firstDate);
			lastDate = dateFormat.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Query query = entityManager.createNamedQuery("Invoice.findAllByPeriod");
		query.setParameter("invoiceDate", firDate);
		query.setParameter("invoiceDate2", lastDate);
		
		invList = query.getResultList();
		
		return invList;
	}
	
	//get invoices by CompanyProfile id
	public List<Invoice> showAllInvoicesByCompani(int compProfil){
		List<Invoice> invList = null;

		Query query = entityManager.createNamedQuery("Invoice.findByCompanyId");
		query.setParameter("companyId", compProfil);
		invList = query.getResultList();	
		
		
		return invList;
	}
	
	//get CompanyProfile by name
	public List<CompanyProfile> AllCompaniesByName(String compName){
		List<CompanyProfile> invList = null;

		Query query = entityManager.createNamedQuery("CompanyProfile.findByCompanyName");
		query.setParameter("companyName", compName);
		invList = query.getResultList();		
		return invList;
	}

	//get invoices by expecting payments (by isPayed)
	public List<Invoice> showAllExpectingPayments(){
		List<Invoice> invList = null;
		
		Query query = entityManager.createNamedQuery("Invoice.findAllWaitingPayments");
		query.setParameter("isPayed", false);
		invList = query.getResultList();		
		return invList;
	}
	
	//get invoices by late payments (by duePayment)
	public List<Invoice> showAllLatePayments(){
		updateInvoiceByLatePayment();
		
		List<Invoice> invList = null;
		Query query = entityManager.createNamedQuery("Invoice.findAllLatePayment");
		query.setParameter("duePayment", true);
		invList = query.getResultList();		
		return invList;
	}
	
	//get invoices by period
	public List<Invoice> showAllInvoicesByPeriod(String startDate, String endDate){
		List<Invoice> invList = null;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDate = null;
		Date lastDate = null;
		try {
			firstDate = dateFormat.parse(startDate);
			lastDate = dateFormat.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Query query = entityManager.createNamedQuery("Invoice.findAllByPeriod");
		query.setParameter("invoiceDate", firstDate);
		query.setParameter("invoiceDate2", lastDate);
		
		invList = query.getResultList();
		
		return invList;
	}
	
	//add new Invoice
	public void addInvoice(int advID, Owner ownerObj, CompanyProfile company,double discount, boolean isPayed, boolean isCash ){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currDate = null;
		String currentDateStr = getCurrentDate();
		
		try {
			currDate = dateFormat.parse(currentDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Invoice invoiceObj = new Invoice();
		invoiceObj.setInvoiceDate(currDate);
		invoiceObj.setOwner(ownerObj);
		invoiceObj.setCompanyProfile(company);
		invoiceObj.setAdvId(advID);
		invoiceObj.setQuantity(1);
		invoiceObj.setDiscount(discount);
		double price = 100;
		invoiceObj.setPrice(price);
		double tax = 0.2;
		invoiceObj.setTax(tax);
		double taxAmmount = price * tax;
		invoiceObj.setTaxAmmount(taxAmmount);
		double totalPrice = calculateTotalPrice(price, taxAmmount, discount);
		invoiceObj.setTotalPrice(totalPrice);
		invoiceObj.setEventDate(currDate);
		invoiceObj.setLatePayment(5);
		invoiceObj.setIsCash(isCash);
		invoiceObj.setIsPayed(isPayed);
		invoiceObj.setDuePayment(false);
		
		entityManager.persist(invoiceObj);
	}
	
	//get all Companies Profiles
	public List<CompanyProfile> showAllCompanies(){
		List<CompanyProfile> compList = null;
		Query query = entityManager.createNamedQuery("CompanyProfile.findAll");
		compList = query.getResultList();		
		return compList;
	}
		
	//set Due Payment to payed or Not Payed
	private void updateInvoiceByLatePayment(){
		List<Invoice> invList = null;
		invList = showAllExpectingPayments();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateStr = getCurrentDate();
		Date currDate = null;
		Date endDate = null;
		
		for (int i = 0; i < invList.size(); i++) {
			Date off = invList.get(i).getInvoiceDate();
			String reportDate = dateFormat.format(off);
			String lastDay = calculateEndDay(reportDate, 5);

			try {
				currDate = dateFormat.parse(currentDateStr);
				endDate = dateFormat.parse(lastDay);
			} catch (ParseException e) {
				e.printStackTrace();
			}
						
			int result = endDate.compareTo(currDate);
			if(result<0){
				invList.get(i).setDuePayment(true);
				entityManager.flush(); //? tuk ili w karq na for-a
			}
		}
		
	}
	
	//update Invoice By is_Payed
	public void updateInvoiceByIsPayed(Invoice invObj, boolean isPayed){
		invObj.setIsPayed(isPayed);
		entityManager.flush();
	}
	
	//return current date
	private String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		return dateFormat.format(date);
	}

	
	//calculate end day
	private String calculateEndDay(String date, int days) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = null;
		try {
			date2 = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date2);
		cal.add(Calendar.DATE, days);
		return dateFormat.format(cal.getTime());
	}

	//get last day of Month
	private String getLastDayFromMonth(int month, int year) {
		boolean isLeap = false;
		String temp = String.valueOf(month);
		if (month < 10) {
			temp = "0" + temp;
		}
		String date = String.valueOf(year) + "-" + temp + "-"; 
		
		if (year % 400 == 0 || year % 4 == 0 && year % 100 != 0) {
			isLeap = true;
		}
		
		if (isLeap) {
			 int[] months = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
			 date = date + months[month];
		}else {
			 int[] months = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
			 date = date + months[month];
		}

		return date;
	}
	
	//get first day of month
	private String getFirstDayOfMonth(int month, int year){
		String temp = String.valueOf(month);
		if (month < 10) {
			temp = "0" + temp;
		}
		String date = String.valueOf(year) + "-" + temp + "-" + "01";
		return date;
	}
	
	private double calculateTotalPrice(double price, double taxAmount, double discount){
		double discAmount = (price+taxAmount)*discount;
		return (price+taxAmount)-discAmount;
	}
}
