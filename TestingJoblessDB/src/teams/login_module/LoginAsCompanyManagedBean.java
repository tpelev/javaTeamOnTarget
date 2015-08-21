package teams.login_module;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.Accounter;
import model.Company;
import model.User;
import teams.user_module.AddAdManagedBean;
import teams.user_module.ResitrationBean;

@ManagedBean(name = "LoginAsCompany")
@SessionScoped
public class LoginAsCompanyManagedBean {
	@EJB
	private RegisterCompanyEJB company;
	@ManagedProperty(value = "#{reg}")
	private ResitrationBean registrationBean;
	
	
	@ManagedProperty(value = "#{test}")
	private AddAdManagedBean addMangerBean;
	
	public String companyUserName;
	private String passCompany;

	public String loginCompany() {

		String userName = this.getCompanyUserName();

		String pass1 = this.getPassCompany();
		System.out.println("entered pass" + pass1);

		try {
			Company test = company.getCompanyUserName(companyUserName);
			String hashedPass = company.hashing(userName, pass1);
			if (hashedPass.equals(test.getLoginPassword())) {
				HttpSession session = SessionBean.getSession();
				session.setAttribute("company", userName);
				registrationBean.setCompanyUserName(userName);
				addMangerBean.setCompanyUserName(userName);
				return "vip";
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Incorect Company name or password!", "Please enter correct user name and password"));
				return "LoginAsCompany";
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Incorect Company name or password!", "Please enter correct user name and password"));
			return "LoginAsCompany";
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

	public ResitrationBean getRegistrationBean() {
		return registrationBean;
	}

	public void setRegistrationBean(ResitrationBean registrationBean) {
		this.registrationBean = registrationBean;
	}

	public AddAdManagedBean getAddMangerBean() {
		return addMangerBean;
	}

	public void setAddMangerBean(AddAdManagedBean addMangerBean) {
		this.addMangerBean = addMangerBean;
	}
	
}
