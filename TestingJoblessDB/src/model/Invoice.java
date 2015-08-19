package model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the invoices database table.
 * 
 */
@Entity
@Table(name="invoices")
@NamedQueries({
	@NamedQuery(name = "Invoice.findAllLatePayment", query = "SELECT i FROM Invoice i WHERE i.duePayment = :duePayment"),
	@NamedQuery(name = "Invoice.findAllByPeriod", query = "SELECT i FROM Invoice i WHERE i.invoiceDate >= :invoiceDate AND i.invoiceDate <= :invoiceDate2"),
	@NamedQuery(name = "Invoice.findAllWaitingPayments", query = "SELECT i FROM Invoice i WHERE i.isPayed = :isPayed"),
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findById", query = "SELECT i FROM Invoice i WHERE i.id = :id"),
    @NamedQuery(name = "Invoice.findByInvoiceDate", query = "SELECT i FROM Invoice i WHERE i.invoiceDate = :invoiceDate"),
    @NamedQuery(name = "Invoice.findByCompanyId", query = "SELECT i FROM Invoice i  WHERE i.companyProfile.id=:id")
})
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="ADV_ID")
	private int advId;

	private double discount;

	@Column(name="DUE_PAYMENT")
	private boolean duePayment;

	@Temporal(TemporalType.DATE)
	@Column(name="EVENT_DATE")
	private Date eventDate;

	@Temporal(TemporalType.DATE)
	@Column(name="INVOICE_DATE")
	private Date invoiceDate;

	@Column(name="IS_CASH")
	private boolean isCash;

	@Column(name="IS_PAYED")
	private boolean isPayed;

	@Column(name="LATE_PAYMENT")
	private int latePayment;

	private double price;

	private double quantity;

	private double tax;

	@Column(name="TAX_AMMOUNT")
	private double taxAmmount;

	@Column(name="TOTAL_PRICE")
	private double totalPrice;

	//bi-directional many-to-one association to Owner
	@ManyToOne
	private Owner owner;

	//bi-directional many-to-one association to CompanyProfile
	@ManyToOne
	@JoinColumn(name="COMPANY_ID")
	private CompanyProfile companyProfile;

	public Invoice() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAdvId() {
		return this.advId;
	}

	public void setAdvId(int advId) {
		this.advId = advId;
	}

	public double getDiscount() {
		return this.discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public boolean getDuePayment() {
		return this.duePayment;
	}

	public void setDuePayment(boolean duePayment) {
		this.duePayment = duePayment;
	}

	public Date getEventDate() {
		return this.eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Date getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public boolean getIsCash() {
		return this.isCash;
	}

	public void setIsCash(boolean isCash) {
		this.isCash = isCash;
	}

	public boolean getIsPayed() {
		return this.isPayed;
	}

	public void setIsPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}

	public int getLatePayment() {
		return this.latePayment;
	}

	public void setLatePayment(int latePayment) {
		this.latePayment = latePayment;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getTax() {
		return this.tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTaxAmmount() {
		return this.taxAmmount;
	}

	public void setTaxAmmount(double taxAmmount) {
		this.taxAmmount = taxAmmount;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public CompanyProfile getCompanyProfile() {
		return this.companyProfile;
	}

	public void setCompanyProfile(CompanyProfile companyProfile) {
		this.companyProfile = companyProfile;
	}

}