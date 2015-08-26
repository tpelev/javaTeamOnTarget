package teams.login_module;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import model.User;
import teams.user_module.ReviewAdManagedBean;


@ManagedBean(name = "LoginAsUser")
@SessionScoped
public class LoginAsUserMenagedBean {
	@EJB
	private RegisterUserEJB user;
	@ManagedProperty(value = "#{index}")
	private ReviewAdManagedBean indexBean;

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
				indexBean.setUserName(userName);
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

	public void validate(ComponentSystemEvent e) {
		UIForm form = (UIForm) e.getComponent();
		UIInput nameInput = (UIInput) form.findComponent("username");
		UIInput pwdInput = (UIInput) form.findComponent("pass1");

		String userName = nameInput.getValue().toString();
		String pass1 = pwdInput.getValue().toString();

		try {
			User test = user.getAdminUserName(userName);
			String hashedPass = user.hashing(userName, pass1);
			if (!hashedPass.equals(test.getLoginPassword())) {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage(form.getClientId(), new FacesMessage("Invalid username or password!"));
				fc.renderResponse();
			}
		} catch (Exception ex) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(form.getClientId(), new FacesMessage("Invalid username or password!"));
			fc.renderResponse();
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

	public ReviewAdManagedBean getIndexBean() {
		return indexBean;
	}

	public void setIndexBean(ReviewAdManagedBean indexBean) {
		this.indexBean = indexBean;
	}

	
}
