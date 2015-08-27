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


/**
 * EJB Class manipulating Invoice JPA Class
 * Class is using EntityManager
 * @author Kaloyan Tsvetkov
 */
@Stateless
@SessionScoped
public class InvoiceEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Getting invoice by ID
	 * @param Integer
	 * @return Invoice Object
	 * @author Kaloyan Tsvetkov
	 */
	public Invoice showInvoiceByID(int id){
		Invoice invoiceObj = entityManager.find(Invoice.class, id);		
		return invoiceObj; 
	}
	
	/**
	 * Getting all invoices from DataBase
	 * using NamedQuery "Invoice.findAll"
	 * {@link Invoice}
	 * @return List<Invoice>
	 * @author Kaloyan Tsvetkov
	 */
	public List<Invoice> showAllInvoices(){
		List<Invoice> invList = null;
		
		Query query = entityManager.createNamedQuery("Invoice.findAll"); //creating query
		invList = query.getResultList(); //executing the query
		return invList;
	}
	

	/**
	 * Getting all invoices by Day
	 * using NamedQuery "Invoice.findByInvoiceDate"
	 * {@link Invoice}
	 * @return List<Invoice>
	 * @author Kaloyan Tsvetkov
	 */
	public List<Invoice> showAllInvoicesByDay(String invDate){
		List<Invoice> invList = null;

		Date date = formatStringToDate(invDate); //call method converting from String to Date Object

		Query query = entityManager.createNamedQuery("Invoice.findByInvoiceDate"); //creating NamedQuery
		query.setParameter("invoiceDate", date); //set NamedQuery parameter to current date
		
		invList = query.getResultList();  //executing the query
		
		return invList;
	}
	
	/**
	 * Getting all invoices by Month
	 * using NamedQuery "Invoice.findAllByPeriod"
	 * Using private Methods getLastDayFromMonth() and getFirstDayOfMonth()
	 * {@link Invoice}
	 * @param month Integer
	 * @param year Integer
	 * @return List<Invoice>
	 * @author Kaloyan Tsvetkov
	 */
	public List<Invoice> showAllInvoicesByMonth(int month, int year){
		List<Invoice> invList = null;
		String endDate = getLastDayFromMonth(month, year); //call method returning String last Day of the Month
		String firstDate = getFirstDayOfMonth(month, year);//call method returning String first Day of the Month
		
		Date firDate = formatStringToDate(firstDate); //call method converting from String to Date Object
		Date lastDate = formatStringToDate(endDate); //call method converting from String to Date Object

		Query query = entityManager.createNamedQuery("Invoice.findAllByPeriod"); //creating NamedQuery
		query.setParameter("invoiceDate", firDate); //set NamedQuery parameter to month start date
		query.setParameter("invoiceDate2", lastDate); //set NamedQuery parameter to month end date
		
		invList = query.getResultList(); //executing the query
		
		return invList;
	}
	
	/**
	 * Getting all invoices by CompanyProfile
	 * using NamedQuery "Invoice.findByCompanyId"
	 * {@link Invoice}
	 * @param id Integer
	 * @return List<Invoice>
	 * @author Kaloyan Tsvetkov
	 */
	public List<Invoice> showAllInvoicesByCompani(int id){
		List<Invoice> invList = new ArrayList<Invoice>();

		Query query = entityManager.createNamedQuery("Invoice.findByCompanyId");
		query.setParameter("id", id);  //set NamedQuery parameter CompanyProfile id
		invList = query.getResultList();  //executing the query	
				
		return invList;
	}
	
	/**
	 * Geting CompanyProfile by id
	 * {@link CompanyProfile}
	 * @param id Integer
	 * @return CompanyProfile Object
	 * @author Kaloyan Tsvetkov
	 */
	public CompanyProfile allCompaniesByName(int compid){
		CompanyProfile company = entityManager.find(CompanyProfile.class, compid); //find CompanyProfile by primary key(int id)
		return company;
	}

	/**
	 * Geting all invoices by expecting payments (by isPayed Column)
	 * using NamedQuery "Invoice.findAllWaitingPayments"
	 * {@link Invoice}
	 * @return List<Invoice>
	 * @author Kaloyan Tsvetkov
	 */
	public List<Invoice> showAllExpectingPayments(){
		List<Invoice> invList = null;
		
		Query query = entityManager.createNamedQuery("Invoice.findAllWaitingPayments"); //creating NamedQuery
		query.setParameter("isPayed", false); //set NamedQuery parameter to false
		invList = query.getResultList(); //executing the query	
		return invList;
	}
	

	/**
	 * Geting all invoices by late payments (by duePayment Column)
	 * using NamedQuery "Invoice.findAllLatePayment"
	 * and private method updateInvoiceByLatePayment()
	 * {@link Invoice}
	 * @return List<Invoice>
	 * @author Kaloyan Tsvetkov
	 */
	public List<Invoice> showAllLatePayments(){
		updateInvoiceByLatePayment(); //call private method
		
		List<Invoice> invList = null;
		Query query = entityManager.createNamedQuery("Invoice.findAllLatePayment"); //creating NamedQuery
		query.setParameter("duePayment", true); //set NamedQuery parameter to true
		query.setParameter("isPayed", false); //set NamedQuery parameter to false
		invList = query.getResultList(); //executing the query	
		return invList;
	}
	
	/**
	 * Geting all invoices by Period
	 * using NamedQuery "Invoice.findAllByPeriod"
	 * {@link Invoice}
	 * @return List<Invoice>
	 * @author Kaloyan Tsvetkov
	 */
	public List<Invoice> showAllInvoicesByPeriod(String startDate, String endDate){
		List<Invoice> invList = null;

		Date firstDate = formatStringToDate(startDate); //call method converting from String to Date Object
		Date lastDate = formatStringToDate(endDate); //call method converting from String to Date Object
		Query query = entityManager.createNamedQuery("Invoice.findAllByPeriod"); //creating NamedQuery
		query.setParameter("invoiceDate", firstDate); //set NamedQuery parameter
		query.setParameter("invoiceDate2", lastDate); //set NamedQuery parameter
		
		invList = query.getResultList(); //executing the query			
		return invList;
	}
	
	/**
	 * Write a new Invoice to DataBase
	 * {@link Invoice}
	 * @param invoiceObj Invoice 
	 * @return void
	 * @author Kaloyan Tsvetkov
	 */
	public void addInvoice(Invoice invoiceObj){
		String currentDateStr = getCurrentDate(); //call method getting current Date
		Date currDate = formatStringToDate(currentDateStr); //call method converting from String to Date Object
		
		invoiceObj.setInvoiceDate(currDate); //set Invoice Date
		invoiceObj.setQuantity(1); //set Quantyti
		double price = 100;
		invoiceObj.setPrice(price); //set Price
		double tax = 0.2;
		invoiceObj.setTax(tax); //set Tax
		double taxAmmount = price * tax;
		invoiceObj.setTaxAmmount(taxAmmount); //set TaxAmmount
		invoiceObj.setLatePayment(5); //set LatePayment
		invoiceObj.setDuePayment(false); //set DuePayment
		
		entityManager.persist(invoiceObj); //write the Invoice Object to DataBase
	}
	
	/**
	 * Geting all Companies Profiles
	 * using NamedQuery "CompanyProfile.findAll"
	 * and private method updateInvoiceByLatePayment()
	 * {@link CompanyProfile}
	 * @return List<CompanyProfile>
	 * @author Kaloyan Tsvetkov
	 */
	public List<CompanyProfile> showAllCompanies(){
		List<CompanyProfile> compList = null;
		Query query = entityManager.createNamedQuery("CompanyProfile.findAll"); //creating NamedQuery
		compList = query.getResultList(); //executing the query	
		return compList;
	}
		
	//set Due Payment to payed or Not Payed
	/**
	 * Geting all Companies Profiles
	 * using NamedQuery "CompanyProfile.findAll"
	 * and private method updateInvoiceByLatePayment()
	 * {@link CompanyProfile}
	 * @return List<CompanyProfile>
	 * @author Teodora Miteva
	 * @author Kaloyan Tsvetkov
	 */
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
			String lastDay = calculateEndDay(reportDate, 5); //call method calculating end Date to pay invoice

			currDate = formatStringToDate(currentDateStr); //call method converting from String to Date Object
			endDate = formatStringToDate(lastDay); //call method converting from String to Date Object
			
			int result = endDate.compareTo(currDate); //Comparing first and last date to pay
			//result < 0 Invoice payment is late, result >= 0 Invoice payment is OK
			if(result<0){
				invList.get(i).setDuePayment(true); //set Invoice DuePayment
				entityManager.flush(); // updating the information
			}
		}
		
	}
	
	/**
	 * Update Invoice to Paid using is_Payed Column
	 * {@link Invoice}
	 * @return List<CompanyProfile>
	 * @author Ilian Tegarkov
	 * @author Teodora Miteva
	 * @author Kaloyan Tsvetkov
	 */
	public void updateInvoiceByIsPayed(int id){
		Invoice test = entityManager.find(Invoice.class, id); //find Invoice by primary key(int id)
		test.setIsPayed(true); //set NamedQuery parameter
		entityManager.flush(); // updating the information
	}
	

	/**
	 * Private method returning current date
	 * @return String
	 * @author Ilian Tegarkov
	 */
	private String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		return dateFormat.format(date);
	}
	
	/**
	 * Private method calculate end day to paid Invoice
	 * @return String
	 * @author Ilian Tegarkov
	 */
	private String calculateEndDay(String date, int days) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date2 = formatStringToDate(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date2);
		cal.add(Calendar.DATE, days);
		return dateFormat.format(cal.getTime());
	}

	/**
	 * Private method calculate last day of Month
	 * @return String
	 * @author Ilian Tegarkov
	 */
	private String getLastDayFromMonth(int month, int year) {
		boolean isLeap = false;
		String temp = String.valueOf(month);
		if (month < 10) {
			temp = "0" + temp;
		}
		String date = String.valueOf(year) + "-" + temp + "-"; 
		//checking for leap year
		if (year % 400 == 0 || year % 4 == 0 && year % 100 != 0) {
			isLeap = true;
		}
		//geting last day of month
		if (isLeap) {
			 int[] months = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
			 date = date + months[month];
		}else {
			 int[] months = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
			 date = date + months[month];
		}

		return date;
	}
	
	/**
	 * Private method calculate first day of Month
	 * @param month Integer
	 * @param year Integer
	 * @return String
	 * @author Ilian Tegarkov
	 */
	private String getFirstDayOfMonth(int month, int year){
		String temp = String.valueOf(month);
		if (month < 10) {
			temp = "0" + temp;
		}
		String date = String.valueOf(year) + "-" + temp + "-" + "01";
		return date;
	}
	
	/**
	 * Private method converting from String to Date
	 * @return Date
	 * @param date String
	 * @throws ParseException
	 * @author Ilian Tegarkov
	 */
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
