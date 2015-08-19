package teams.login_module;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "LoginM")
@SessionScoped
public class Login {
	@EJB
	private AdminLoginEJB admin;
	@EJB
	private AccountEJB account;
	@EJB
	private UserEJB user;
	@EJB
	private CompanyEJB company;

	public String userName;
	private String pass;
	private String msg;

	public String login() {

		String userName = this.getUserName();
		String pass1 = this.getPass();
		System.out.println("entered pass" + pass1);
		if (account.validateUserNameAndPassword(getUserName(), getPass())) {
			HttpSession session = SessionBean.getSession();
			session.setAttribute("account", userName);
			return "account";
		} else if (admin.validateUserNameAndPassword(getUserName(), getPass())) {
			HttpSession session = SessionBean.getSession();
			session.setAttribute("admin", userName);
			return "admin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorect admin name or password!", "Please enter correct user name and password"));
			return "LoginAsAdmin";
		}
	}

	public String logout() {
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		return "LoginAsAdmin";
	}

	public String getUserName() {
		return userName;

	}

	public void setUserName(String name) {
		this.userName = name;

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
