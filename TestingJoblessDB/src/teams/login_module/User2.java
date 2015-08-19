package teams.login_module;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class User2 {

	@EJB
	private RegisterCompanyEJB company;
	private String name;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.test();
	}
	
	public void test(){
		String username = this.getName();
		if (company.companyExists(this.name)) {
			this.setMessage("Username already exists");
		}
		else{
			this.setMessage("");
		}
	}
	
}
