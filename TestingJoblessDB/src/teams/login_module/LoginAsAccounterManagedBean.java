package teams.login_module;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Accounter;

@ManagedBean(name = "LoginAsAccounter")
@SessionScoped
public class LoginAsAccounterManagedBean {
	@EJB
	private AccountEJB accounter;

	public String accounterUserName;
	private String passAccounter;

	public void loginAccounter() {

		String userName = this.getAccounterUserName();

		String pass1 = this.getPassAccounter();
		System.out.println("entered pass" + pass1);

		try {
			Accounter test = accounter.getAccountUserName(accounterUserName);
			if (pass1.equals(test.getLoginPassword())) {
				System.out.println("Success!! Welcome accounter!");
			}

		} catch (Exception e) {
			// TODO: handle exception
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
