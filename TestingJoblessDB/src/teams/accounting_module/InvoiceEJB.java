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

import model.Invoice;

@Singleton
public class InvoiceEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
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
	public List<Invoice> showAllInvoicesByCompani(int id){
		List<Invoice> invList = null;
		
		Query query = entityManager.createNamedQuery("Invoice.findByCompanyId");
		query.setParameter("companyId", id);
		invList = query.getResultList();		
		return invList;
	}

	//get invoices by expecting payments (by isPayed)
	public List<Invoice> showAllExpectingPayments(){
		List<Invoice> invList = null;
		
		Query query = entityManager.createNamedQuery("Invoice.findAllWaitingPayments");
		invList = query.getResultList();		
		return invList;
	}
	
	//get invoices by late payments (by duePayment)
	public List<Invoice> showAllLatePayments(){
		List<Invoice> invList = null;
		
		Query query = entityManager.createNamedQuery("Invoice.findAllLatePayment");
		invList = query.getResultList();		
		return invList;
	}
	
	//return curent date
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
	
	private String getFirstDayOfMonth(int month, int year){
		String temp = String.valueOf(month);
		if (month < 10) {
			temp = "0" + temp;
		}
		String date = String.valueOf(year) + "-" + temp + "-" + "01";
		return date;
	}
}
