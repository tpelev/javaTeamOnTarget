package teams.login_module;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import model.Accounter;

@ManagedBean(name = "LoginAsAccounter")
@SessionScoped
public class LoginAsAccounterManagedBean {
	@EJB
	private AccountEJB accounter;
	public String accounterUserName;
	private String passAccounter;

	
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
	
	public String loginAccounter() {

		String userName = this.getAccounterUserName();
		String password = this.getPassAccounter();		

		try {
			Accounter account = accounter.getAccountUserName(accounterUserName);
			String hashedPass = HashingAlgorithm.hashing(userName, password);
			if (hashedPass.equals(account.getLoginPassword())) {
				HttpSession session = SessionBean.getSession();
				session.setAttribute("account", userName);
				return "accountMainJSF?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Incorect Accounter name or password!", "Please enter correct user name and password"));
				return "loginAsAccount?faces-redirect=true";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Incorect Accounter name or password!", "Please enter correct user name and password"));
			return "loginAsAccount?faces-redirect=true";
		}
	}
	
	public void validate(ComponentSystemEvent e) {
		UIForm form = (UIForm) e.getComponent();
		UIInput nameInput = (UIInput) form.findComponent("username");
		UIInput pwdInput = (UIInput) form.findComponent("password");

		String userName = nameInput.getValue().toString();
		String password = pwdInput.getValue().toString();

		try {
			Accounter account = accounter.getAccountUserName(userName);
			String hashedPass = HashingAlgorithm.hashing(userName, password);
			if (!hashedPass.equals(account.getLoginPassword())) {
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
