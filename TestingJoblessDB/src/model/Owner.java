package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the owner database table.
 * 
 */
@Entity
@NamedQuery(name="Owner.findAll", query="SELECT o FROM Owner o")
public class Owner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String adress;

	private String bic;

	@Column(name="COMPANY_NAME")
	private String companyName;

	private String eik;

	private String email;

	private String iban;

	private String mol;

	@Column(name="PHONE_NUMBER")
	private String phoneNumber;

	//bi-directional many-to-one association to Invoice
	@OneToMany(mappedBy="owner", fetch=FetchType.EAGER)
	private List<Invoice> invoices;

	public Owner() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdress() {
		return this.adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getBic() {
		return this.bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEik() {
		return this.eik;
	}

	public void setEik(String eik) {
		this.eik = eik;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getMol() {
		return this.mol;
	}

	public void setMol(String mol) {
		this.mol = mol;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Invoice> getInvoices() {
		return this.invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Invoice addInvoice(Invoice invoice) {
		getInvoices().add(invoice);
		invoice.setOwner(this);

		return invoice;
	}

	public Invoice removeInvoice(Invoice invoice) {
		getInvoices().remove(invoice);
		invoice.setOwner(null);

		return invoice;
	}

}