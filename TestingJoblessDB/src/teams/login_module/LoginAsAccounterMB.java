package teams.login_module;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import teams.login_module.SessionBean;

@ManagedBean(name = "LoginAsAccounter")
@SessionScoped
public class LoginAsAccounterMB {

	@EJB
	private AccounterLoginEJB accounter;

	public String accounterUserName;
	private String pass;
	private String msg;

	public String loginAccounter() {

		String userName = this.getAccounterUserName();

		String pass1 = this.getPass();
		System.out.println("entered pass" + pass1);
		boolean isValid = accounter.validateUserNameAndPassword(getAccounterUserName(), getPass());
		if (isValid) {
			HttpSession session = SessionBean.getSession();
			session.setAttribute("accounter", userName);
			return "accounter";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorect accounter name or password!", "Please enter correct user name and password"));
			return "LoginAsAccount";
		}
	}
	
	public String logout(){
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		return "MainPage";
	}

	public String getAccounterUserName() {
		return accounterUserName;

	}

	public void setAccounterUserName(String name) {
		this.accounterUserName = name;

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
}
