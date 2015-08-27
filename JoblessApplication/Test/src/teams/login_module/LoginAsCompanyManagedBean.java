package teams.login_module;


import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.mail.Session;
import javax.servlet.http.HttpSession;
import model.Company;

import teams.user_module.AddAdManagedBean;

@ManagedBean(name = "LoginAsCompany")
@SessionScoped
public class LoginAsCompanyManagedBean {
	@EJB
	private RegisterCompanyEJB company;

	@ManagedProperty(value = "#{createAd}")
	private AddAdManagedBean addManagerBean;

	public String companyUserName;
	private String passCompany;
	@Resource(name = "mailSession")
	private Session mailSession;

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

	public AddAdManagedBean getAddManagerBean() {
		return addManagerBean;
	}

	public void setAddManagerBean(AddAdManagedBean addManagerBean) {
		this.addManagerBean = addManagerBean;
	}

	public String loginCompany() {

		String userName = this.getCompanyUserName();

		String password = this.getPassCompany();
		System.out.println("entered pass" + password);

		try {
			Company companyLog = company.getCompanyUserName(companyUserName);
			String hashedPass = HashingAlgorithm.hashing(userName, password);
			if (hashedPass.equals(companyLog.getLoginPassword())) {
				HttpSession session = SessionBean.getSession();
				session.setAttribute("company", userName);

				addManagerBean.setCompanyUserName(userName);
				return "advTypeSelectionView?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Incorect Company name or password!", "Please enter correct user name and password"));
				return "loginAsCompany?faces-redirect=true";
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Incorect Company name or password!", "Please enter correct user name and password"));
			return "loginAsCompany?faces-redirect=true";
		}
	}

	public void validate(ComponentSystemEvent e) {
		UIForm form = (UIForm) e.getComponent();
		UIInput nameInput = (UIInput) form.findComponent("username");
		UIInput pwdInput = (UIInput) form.findComponent("password");

		String userName = nameInput.getValue().toString();
		String password = pwdInput.getValue().toString();

		try {
			Company test = company.getCompanyUserName(userName);
			String hashedPass = HashingAlgorithm.hashing(userName, password);
			if (!hashedPass.equals(test.getLoginPassword())) {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage(form.getClientId(), new FacesMessage("Invalid username or password."));
				fc.renderResponse();
			}
		} catch (Exception ex) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(form.getClientId(), new FacesMessage("Invalid username or password!"));
			fc.renderResponse();
		}

	}

}
