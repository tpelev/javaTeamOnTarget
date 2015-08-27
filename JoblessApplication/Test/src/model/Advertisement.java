package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the advertisements database table.
 * 
 */
@Entity
@Table(name="advertisements")
@NamedQueries({
	@NamedQuery(name = "Advertisement.findAllVip", query = "SELECT a FROM Advertisement a WHERE a.isVip = :isVip"),
	@NamedQuery(name = "Advertisement.findAllnVipAndPayedByCompany", query = "SELECT a FROM Advertisement a WHERE a.isVip = :isVip AND a.isPaid = :isPayed AND a.hasInvoice = :hasInvoice AND a.companyProfile.id = :id"),
    @NamedQuery(name = "Advertisement.findAll", query = "SELECT a FROM Advertisement a"),
    @NamedQuery(name = "Advertisement.findById", query = "SELECT a FROM Advertisement a WHERE a.id = :id"),
    @NamedQuery(name = "Advertisement.findByTitle", query = "SELECT a FROM Advertisement a WHERE a.title = :title"),
    @NamedQuery(name = "Advertisement.findByTest", query = "SELECT a FROM Advertisement a WHERE a.test = :test"),
    @NamedQuery(name = "Advertisement.findByPrice", query = "SELECT a FROM Advertisement a WHERE a.price = :price"),
    @NamedQuery(name = "Advertisement.findByActivityDays", query = "SELECT a FROM Advertisement a WHERE a.activityDays = :activityDays"),
    @NamedQuery(name = "Advertisement.findByExpirationDate", query = "SELECT a FROM Advertisement a WHERE a.expirationDate = :expirationDate"),
    @NamedQuery(name = "Advertisement.findByIsApproved", query = "SELECT a FROM Advertisement a WHERE a.isApproved = :isApproved"),
    @NamedQuery(name = "Advertisement.findByIsPaid", query = "SELECT a FROM Advertisement a WHERE a.isPaid = :isPaid"),
    @NamedQuery(name = "Advertisement.findByIsExpired", query = "SELECT a FROM Advertisement a WHERE a.isExpired = :isExpired")
})
public class Advertisement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="ACTIVITY_DAYS")
	private int activityDays;

	private String content;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPIRATION_DATE")
	private Date expirationDate;

	@Column(name="HAS_INVOICE")
	private boolean hasInvoice;

	@Column(name="IS_APPROVED")
	private boolean isApproved;

	@Column(name="IS_EXPIRED")
	private boolean isExpired;

	@Column(name="IS_PAID")
	private boolean isPaid;

	@Column(name="IS_VIP")
	private boolean isVip;

	private double price;

	private String test;

	private String title;

	//bi-directional many-to-one association to Category
	@ManyToOne
	private Category category;

	//bi-directional many-to-one association to CompanyProfile
	@ManyToOne
	@JoinColumn(name="COMPANY_ID")
	private CompanyProfile companyProfile;

	//bi-directional many-to-one association to Place
	@ManyToOne
	private Place place;

	public Advertisement() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActivityDays() {
		return this.activityDays;
	}

	public void setActivityDays(int activityDays) {
		this.activityDays = activityDays;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean getHasInvoice() {
		return this.hasInvoice;
	}

	public void setHasInvoice(boolean hasInvoice) {
		this.hasInvoice = hasInvoice;
	}

	public boolean getIsApproved() {
		return this.isApproved;
	}

	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean getIsExpired() {
		return this.isExpired;
	}

	public void setIsExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public boolean getIsPaid() {
		return this.isPaid;
	}

	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public boolean getIsVip() {
		return this.isVip;
	}

	public void setIsVip(boolean isVip) {
		this.isVip = isVip;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getTest() {
		return this.test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public CompanyProfile getCompanyProfile() {
		return this.companyProfile;
	}

	public void setCompanyProfile(CompanyProfile companyProfile) {
		this.companyProfile = companyProfile;
	}

	public Place getPlace() {
		return this.place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return id + " " + title;
	}
}