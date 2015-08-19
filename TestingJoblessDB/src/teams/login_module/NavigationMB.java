package teams.login_module;

import java.io.Serializable;

//import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="navigationMB")
@SessionScoped
public class NavigationMB implements Serializable{

	
	private static final long serialVersionUID = 1L;

	
	  public String toLoginAsAdmin() {
	        return "LoginAsAdmin.xhtml";
	    }
	     
}
