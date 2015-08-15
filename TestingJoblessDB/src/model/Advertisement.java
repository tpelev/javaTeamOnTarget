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
@NamedQuery(name="Advertisement.findAll", query="SELECT a FROM Advertisement a")
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

	@Column(name="IS_APPROVED")
	private byte isApproved;

	@Column(name="IS_EXPIRED")
	private byte isExpired;

	@Column(name="IS_PAID")
	private byte isPaid;

	@Column(name="IS_VIP")
	private byte isVip;

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

	public byte getIsApproved() {
		return this.isApproved;
	}

	public void setIsApproved(byte isApproved) {
		this.isApproved = isApproved;
	}

	public byte getIsExpired() {
		return this.isExpired;
	}

	public void setIsExpired(byte isExpired) {
		this.isExpired = isExpired;
	}

	public byte getIsPaid() {
		return this.isPaid;
	}

	public void setIsPaid(byte isPaid) {
		this.isPaid = isPaid;
	}

	public byte getIsVip() {
		return this.isVip;
	}

	public void setIsVip(byte isVip) {
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

}