package teams.login_module;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.User;
import teams.user_module.indexBean;

@ManagedBean(name = "LoginAsUser")
@SessionScoped
public class LoginAsUserMenagedBean {
	@EJB
	private RegisterUserEJB user;
	@ManagedProperty(value = "#{index}")
	private indexBean indexBean;

	public String userUserName;
	private String passUser;

	public String loginUser() {

		String userName = this.getUserUserName();

		String pass1 = this.getPassUser();
		System.out.println("entered pass" + pass1);

		try {
			User test = user.getAdminUserName(userUserName);
			String hashedPass = user.hashing(userName, pass1);
			if (hashedPass.equals(test.getLoginPassword())) {
				HttpSession session = SessionBean.getSession();
				session.setAttribute("user", userName);
				indexBean.setLogged(true);
				return "index.xhtml";
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Incorect User name or password!", "Please enter correct user name and password"));
				return "LoginAsUser";
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Incorect User name or password!", "Please enter correct user name and password"));
			return "LoginAsUser";
		}
	}

	public String getUserUserName() {
		return userUserName;
	}

	public void setUserUserName(String userUserName) {
		this.userUserName = userUserName;
	}

	public String getPassUser() {
		return passUser;
	}

	public void setPassUser(String passUser) {
		this.passUser = passUser;
	}

	public indexBean getIndexBean() {
		return indexBean;
	}

	public void setIndexBean(indexBean indexBean) {
		this.indexBean = indexBean;
	}

	
}
