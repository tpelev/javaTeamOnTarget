package teams.accounting_module;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import model.entity.Invoice;

/**
 * ManagedBean class export information from one JSF to another JSF(see.xhtml) 
 * using nested Parameters in Command Button
 * 
 * @author Ilian Tegarkov
 */
@ManagedBean(name = "seeBean")
public class ManagedBeanForSee {
	/* Class Fields */
	@EJB
	private InvoiceEJB ejbInvoice;
	private String id;
	private String cName;
	private String address;
	private String mol;
	private String eik;
	private String email;
	private String discount;
	private String totalPrice;
	private String isCash;
	private String isPaid;
	private String invoiceDate;
	private String eventDate;
	
	/* 
	 method needed to set and get parameters from command button 
	 */
	public String getOutcomeId() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.id = getIdParam(fc);
		if (id != null) {
			Invoice inv = ejbInvoice.showInvoiceByID(Integer.parseInt(this.id));
			cName = inv.getCompanyProfile().getCompanyName();
			address = inv.getCompanyProfile().getAdress();
			email = inv.getCompanyProfile().getEmail();
			mol = inv.getCompanyProfile().getMol();
			eik = inv.getCompanyProfile().getEik();
			discount = String.valueOf(inv.getDiscount());
			totalPrice = String.valueOf(inv.getTotalPrice());
			isCash = String.valueOf(inv.getIsCash());
			isPaid = String.valueOf(inv.getIsPayed());
			invoiceDate = String.valueOf(inv.getInvoiceDate());
			eventDate = String.valueOf(inv.getEventDate());
		}
		return id;
	}

	public String getIdParam(FacesContext fc) {
//		return fc.getExternalContext().getRequestParameterMap().get("id");
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("id");
	}

	/*
	 *  Getters and Setters 
	 */
	public InvoiceEJB getEjbInvoice() {
		return ejbInvoice;
	}

	public void setEjbInvoice(InvoiceEJB ejbInvoice) {
		this.ejbInvoice = ejbInvoice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMol() {
		return mol;
	}

	public void setMol(String mol) {
		this.mol = mol;
	}

	public String getEik() {
		return eik;
	}

	public void setEik(String eik) {
		this.eik = eik;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getIsCash() {
		return isCash;
	}

	public void setIsCash(String isCash) {
		this.isCash = isCash;
	}

	public String getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}


}
