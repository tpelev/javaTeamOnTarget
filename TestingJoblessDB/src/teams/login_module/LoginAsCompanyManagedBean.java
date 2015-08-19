package teams.login_module;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Accounter;
import model.Company;

@ManagedBean(name = "LoginAsCompany")
@SessionScoped
public class LoginAsCompanyManagedBean {
	@EJB
	private CompanyEJB company;

	public String companyUserName;
	private String passCompany;

	public void loginCompany() {

		String userName = this.getCompanyUserName();

		String pass1 = this.getPassCompany();
		System.out.println("entered pass" + pass1);

		try {
			Company test = company.getCompanyUserName(companyUserName);
			if (pass1.equals(test.getLoginPassword())) {
				System.out.println("Success!! Welcome company!");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getCompanyUserName() {
		return companyUserName;
	}

	public void setCompanyUserName(String companyUserName) {
		this.companyUserName = companyUserName;
	}

	public String getPassCompany() {
		return passCompany;
	}

	public void setPassCompany(String passCompany) {
		this.passCompany = passCompany;
	}
	
}
