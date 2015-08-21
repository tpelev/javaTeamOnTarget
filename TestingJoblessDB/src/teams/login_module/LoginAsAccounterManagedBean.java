package teams.login_module;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.Accounter;
import model.Company;

@ManagedBean(name = "LoginAsAccounter")
@SessionScoped
public class LoginAsAccounterManagedBean {
	@EJB
	private AccountEJB accounter;

	public String accounterUserName;
	private String passAccounter;

	public String loginAccounter() {

		String userName = this.getAccounterUserName();

		String pass1 = this.getPassAccounter();
		System.out.println("entered pass" + pass1);

		try {
			Accounter test = accounter.getAccountUserName(accounterUserName);
			String hashedPass = accounter.hashing(userName, pass1);
			if (hashedPass.equals(test.getLoginPassword())) {
				HttpSession session = SessionBean.getSession();
				session.setAttribute("account", userName);
				return "account";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Incorect Accounter name or password!", "Please enter correct user name and password"));
				return "LoginAsAccount";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Incorect Accounter name or password!", "Please enter correct user name and password"));
			return "LoginAsAccount";
		}
	}

	public String getAccounterUserName() {
		return accounterUserName;
	}

	public void setAccounterUserName(String accounterUserName) {
		this.accounterUserName = accounterUserName;
	}

	public String getPassAccounter() {
		return passAccounter;
	}

	public void setPassAccounter(String passAccounter) {
		this.passAccounter = passAccounter;
	}

}
