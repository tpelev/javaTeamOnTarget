package teams.user_module;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "login")
@SessionScoped
public class User {

	private boolean logged;
	private boolean company;

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public boolean isCompany() {
		return company;
	}

	public void setCompany(boolean company) {
		this.company = company;
	}

}
