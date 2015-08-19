package teams.login_module;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "LoginAsCompanyMB")
@SessionScoped
public class LoginAsCompanyMB {

	@EJB
	private CompanyLoginEJB company;

	public String companyUserName;
	private String pass;
	private String msg;

	private boolean loggedIn;
	
//	@ManagedProperty(value = "#{navigationMB}")
//
//	private NavigationMB navigationBean;

	public String loginCompany() {

		String userName = this.getCompanyUserName();

		String pass1 = this.getPass();
		System.out.println("entered pass" + pass1);
		boolean isValid = company.validateUserNameAndPassword(getCompanyUserName(), getPass());
		if (isValid) {
			HttpSession session = SessionBean.getSession();
			session.setAttribute("company", userName);
			loggedIn = true;
			return "company.xhtml";
			
		} else {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
//					"Incorect admin name or password!", "Please enter correct user name and password"));
//			return "/LoginAsAdmin";
			
			// Set login ERROR
			FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
	        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			
			// To to login page
			return "LoginAsCompany.xhtml";
		}
	}

	public String logout() {
		
		loggedIn = false;
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		
		//return "MainPage";
		return "LoginAsCompany.xhtml";
		//return navigationBean.toLoginAsAdmin();
	}

	public String getCompanyUserName() {
		return companyUserName;

	}

	public void setCompanyUserName(String name) {
		this.companyUserName = name;

	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	  public boolean isLoggedIn() {
	        return loggedIn;
	    }
	 
	    public void setLoggedIn(boolean loggedIn) {
	        this.loggedIn = loggedIn;
	    }
	 
//	    public void setNavigationBean(NavigationMB navigationBean) {
//	        this.navigationBean = navigationBean;
//	    }
}
