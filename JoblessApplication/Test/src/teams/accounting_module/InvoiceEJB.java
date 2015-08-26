package teams.accounting_module;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.CompanyProfile;
import model.Invoice;




@Stateless
@SessionScoped
public class InvoiceEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	//get invoice by ID
	public Invoice showInvoiceByID(int id){
		Invoice invoiceObj = entityManager.find(Invoice.class, id);		
		return invoiceObj;
	}
	
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

		Date date = formatStringToDate(invDate);

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
		
		Date firDate = formatStringToDate(firstDate);
		Date lastDate = formatStringToDate(endDate);

		Query query = entityManager.createNamedQuery("Invoice.findAllByPeriod");
		query.setParameter("invoiceDate", firDate);
		query.setParameter("invoiceDate2", lastDate);
		
		invList = query.getResultList();
		
		return invList;
	}
	
	//get invoices by CompanyProfile id
	@SuppressWarnings("unchecked")
	public List<Invoice> showAllInvoicesByCompani(int id){
		List<Invoice> invList = new ArrayList<Invoice>();

		Query query = entityManager.createNamedQuery("Invoice.findByCompanyId");
		query.setParameter("id", id);
		invList = query.getResultList();	
		
		
		return invList;
	}
	
	//get CompanyProfile by id
	public CompanyProfile allCompaniesByName(int compid){
		CompanyProfile company = entityManager.find(CompanyProfile.class, compid);	
		return company;
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
		query.setParameter("isPayed", false);
		invList = query.getResultList();		
		return invList;
	}
	
	//get invoices by period
	public List<Invoice> showAllInvoicesByPeriod(String startDate, String endDate){
		List<Invoice> invList = null;

		Date firstDate = formatStringToDate(startDate);
		Date lastDate = formatStringToDate(endDate);
		Query query = entityManager.createNamedQuery("Invoice.findAllByPeriod");
		query.setParameter("invoiceDate", firstDate);
		query.setParameter("invoiceDate2", lastDate);
		
		invList = query.getResultList();
		
		return invList;
	}
	
	//add new Invoice
	public void addInvoice(Invoice invoiceObj){
		String currentDateStr = getCurrentDate();
		Date currDate = formatStringToDate(currentDateStr);
		
		invoiceObj.setInvoiceDate(currDate);
		invoiceObj.setQuantity(1);
		double price = 100;
		invoiceObj.setPrice(price);
		double tax = 0.2;
		invoiceObj.setTax(tax);
		double taxAmmount = price * tax;
		invoiceObj.setTaxAmmount(taxAmmount);
		invoiceObj.setLatePayment(5);
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
		invList = showAllExpectingPayments(); //get non payd invoices
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateStr = getCurrentDate();
		Date currDate = null;
		Date endDate = null;

		for (int i = 0; i < invList.size(); i++) {
			Date off = invList.get(i).getInvoiceDate();

			String reportDate = dateFormat.format(off);
			String lastDay = calculateEndDay(reportDate, 5);

			currDate = formatStringToDate(currentDateStr);
			endDate = formatStringToDate(lastDay);
			
			int result = endDate.compareTo(currDate);
			if(result<0){
				invList.get(i).setDuePayment(true);
				entityManager.flush(); // updating the information
			}
		}
		
	}
	
	//update Invoice By is_Payed
	public void updateInvoiceByIsPayed(int id){
		Invoice test = entityManager.find(Invoice.class, id);
		test.setIsPayed(true);
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
//		Date date2 = null;
//		try {
//			date2 = dateFormat.parse(date);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		
		Date date2 = formatStringToDate(date);
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
	
	//convert from String to Date
	public Date formatStringToDate(String date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = null;
		try {
			date2 = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date2;
	}
}
