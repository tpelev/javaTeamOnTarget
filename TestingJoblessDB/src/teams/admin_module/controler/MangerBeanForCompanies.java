package teams.admin_module.controler;

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

@ManagedBean(name = "test")
public class MangerBeanForCompanies implements ActionListener {

	@EJB
	private EJBCompanyRequest ejbCompanies;

	private List<CompanyProfile> companyProfile = new ArrayList<>();

	public List<CompanyProfile> getCompanyist() {
		companyProfile = ejbCompanies.showAllCompanies();
		return companyProfile;
	}
	public String actionApprove(){
		
			
		return null;
		
	}
	
	public String showAllCompanies() {
		companyProfile = ejbCompanies.showAllCompanies();
		return "Companies.xhtml";
	}

	public List<CompanyProfile> getProfile() {
		return companyProfile;
	}

	public void setProfile(List<CompanyProfile> profile) {
		this.companyProfile = profile;
	}

	public EJBCompanyRequest getEmployeeEJB() {
		return ejbCompanies;
	}

	public void setEmployeeEJB(EJBCompanyRequest employeeEJB) {
		this.ejbCompanies = employeeEJB;
	}
	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		// TODO Auto-generated method stub
		System.out.println("test");
		
	}

}
