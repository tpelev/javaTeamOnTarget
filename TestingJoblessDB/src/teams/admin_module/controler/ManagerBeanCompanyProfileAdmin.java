package teams.admin_module.controler;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.CompanyProfile;

@ManagedBean(name="bean")
@SessionScoped
public class ManagerBeanCompanyProfileAdmin {
	
	@EJB
	private EJBCompanyRequest ejbRequest;
	private String address;
	private String companyName;
	private String MOL;
	private String EIK;
	private String email;
	private String companyType;
	private List<CompanyProfile> listOfCompanies;

	public List<CompanyProfile> getCompany() {
		listOfCompanies=ejbRequest.showAllCompanies();
		return listOfCompanies;
	}



	public void setCompany(List<CompanyProfile> company) {
		this.listOfCompanies = company;
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

	public void updateAproval(CompanyProfile tempCompanyProfile){
		ejbRequest.updateStatusAproval(tempCompanyProfile.getId());
	}
	
	public void notAproval(CompanyProfile tempCompanyProfile){
		ejbRequest.updateStatusNotAproval(tempCompanyProfile.getId());
	}
	
	public void update(CompanyProfile tempCompanyProfile){
		int id = tempCompanyProfile.getId();
		ejbRequest.updateCompany(id, getCompanyName(), getAddress(),getMOL(), getEIK(), getEmail(),getCompanyType());
		
	}
	
	
}
