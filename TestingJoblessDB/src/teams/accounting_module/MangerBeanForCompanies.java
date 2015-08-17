package teams.accounting_module;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import model.Admin;
import model.Company;
import model.CompanyProfile;
import model.Invoice;

@ManagedBean(name = "test")
public class MangerBeanForCompanies{

	@EJB
	private InvoiceEJB ejbInvoice;

	private List<Invoice> invoiceList = new ArrayList<>();
	
	//add invoices to List
	public List<Invoice> getAllInvoices(){
		invoiceList = ejbInvoice.showAllInvoices();		
		return invoiceList;
	}

	//to refresh the xhtml page
	public String showAllInvoices() {
		invoiceList = ejbInvoice.showAllInvoices();
//		invoiceList = ejbInvoice.showAllLatePayments();
//		invoiceList = ejbInvoice.showAllExpectingPayments();
		return "Companies.xhtml";
	}


	public InvoiceEJB getEjbInvoice() {
		return ejbInvoice;
	}


	public void setEjbInvoice(InvoiceEJB ejbInvoice) {
		this.ejbInvoice = ejbInvoice;
	}


	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}


	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}


}
