package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the company_profiles database table.
 * 
 */
@Entity
@Table(name="company_profiles")
@NamedQueries({
    @NamedQuery(name = "CompanyProfile.findAll", query = "SELECT c FROM CompanyProfile c"),
    @NamedQuery(name = "CompanyProfile.findById", query = "SELECT c FROM CompanyProfile c WHERE c.id = :id"),
    @NamedQuery(name = "CompanyProfile.findByCompanyName", query = "SELECT c FROM CompanyProfile c WHERE c.companyName = :companyName"),
    @NamedQuery(name = "CompanyProfile.findByCompanyType", query = "SELECT c FROM CompanyProfile c WHERE c.companyType = :companyType"),
    @NamedQuery(name = "CompanyProfile.findByStatus", query = "SELECT c FROM CompanyProfile c WHERE c.status = :status")
})
public class CompanyProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String adress;

	@Column(name="COMPANY_NAME")
	private String companyName;

	@Column(name="COMPANY_TYPE")
	private String companyType;

	private String eik;

	private String email;

	private String mol;

	private int status;

	//bi-directional many-to-one association to Advertisement
	@OneToMany(mappedBy="companyProfile", fetch=FetchType.EAGER)
	private List<Advertisement> advertisements;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company;

	//bi-directional many-to-one association to Invoice
	@OneToMany(mappedBy="companyProfile", fetch=FetchType.EAGER)
	private List<Invoice> invoices;

	public CompanyProfile() {
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

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
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

	public String getMol() {
		return this.mol;
	}

	public void setMol(String mol) {
		this.mol = mol;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Advertisement> getAdvertisements() {
		return this.advertisements;
	}

	public void setAdvertisements(List<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}

	public Advertisement addAdvertisement(Advertisement advertisement) {
		getAdvertisements().add(advertisement);
		advertisement.setCompanyProfile(this);

		return advertisement;
	}

	public Advertisement removeAdvertisement(Advertisement advertisement) {
		getAdvertisements().remove(advertisement);
		advertisement.setCompanyProfile(null);

		return advertisement;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Invoice> getInvoices() {
		return this.invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Invoice addInvoice(Invoice invoice) {
		getInvoices().add(invoice);
		invoice.setCompanyProfile(this);

		return invoice;
	}

	public Invoice removeInvoice(Invoice invoice) {
		getInvoices().remove(invoice);
		invoice.setCompanyProfile(null);

		return invoice;
	}

	@Override
	public String toString() {
		return   id + " "+ companyName;
	}
	
}