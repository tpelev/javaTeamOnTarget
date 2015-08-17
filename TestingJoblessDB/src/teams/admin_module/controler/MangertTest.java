package teams.admin_module.controler;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import model.CompanyProfile;

@ManagedBean(name="bean")
public class MangertTest {
	
	@EJB
	private EJBCompanyRequest request;
	
	private String address;
	private String companyName;
	private String MOL;
	private String EIK;
	private String email;
	private String companyType;

	private List<CompanyProfile> company;

	public List<CompanyProfile> getCompany() {
		company=request.showAllCompanies();
		return company;
	}



	public void setCompany(List<CompanyProfile> company) {
		this.company = company;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public String getMOL() {
		return MOL;
	}



	public void setMOL(String mOL) {
		MOL = mOL;
	}



	public String getEIK() {
		return EIK;
	}



	public void setEIK(String eIK) {
		EIK = eIK;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getCompanyType() {
		return companyType;
	}



	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public void updateAproval(CompanyProfile company){
		request.updateStatusAproval(company.getId());
	}
	
	public void notAproval(CompanyProfile company){
		request.updateStatusNotAproval(company.getId());
	}
	
	public void update(CompanyProfile company){
		int id = company.getId();
		request.updateCompany(id, getCompanyName(), getAddress(),getMOL(), getEIK(), getEmail(),getCompanyType());
		
	}
	
	
}
