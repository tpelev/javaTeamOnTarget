package teams.accounting_module;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

//this class export information from one JSF to another JSF(see.xhtml) using nested Parameters in Command Button
@ManagedBean(name = "seeBean")
public class MangerBeanForSee {
	//class fields
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
	
	//methods needed to set and get parameters from command button
	public String getOutcomeId() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.id = getIdParam(fc);
	}

	public String getIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("id");

	}

	public String getOutcomeCompanyName() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.cName = getcNameParam(fc);
	}

	public String getcNameParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("cName");
	}

	public String getOutcomeAdress() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.address = getAddressParam(fc);
	}

	public String getAddressParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("address");

	}
	
	public String getOutcomeMol() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.mol = getMolParam(fc);
	}

	public String getMolParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("mol");

	}
	
	public String getOutcomeEik() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.eik = getEikParam(fc);
	}

	public String getEikParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("eik");

	}
	
	public String getOutcomeEmail() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.email = getEmailParam(fc);
	}

	public String getEmailParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("email");

	}

	public String getOutcomeDiscount() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.discount = getDiscountParam(fc);
	}

	public String getDiscountParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("discount");

	}

	public String getOutcomeTotalPrice() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.totalPrice = getTotalPriceParam(fc);
	}

	public String getTotalPriceParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("totalPrice");

	}
	
	public String getOutcomeIsCash() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.isCash = getIsCashParam(fc);
	}

	public String getIsCashParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("isCash");

	}
	
	public String getOutcomeIsPaid() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.isPaid = getIsPaidParam(fc);
	}

	public String getIsPaidParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("isPaid");

	}
	
	public String getOutcomeInvoiceDate() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.invoiceDate = getInvoiceDateParam(fc);
	}

	public String getInvoiceDateParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("invoiceDate");

	}
	
	public String getOutcomeEventDate() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return this.eventDate = getEventDateParam(fc);
	}

	public String getEventDateParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("eventDate");

	}
	
	//Getters and Setters
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
