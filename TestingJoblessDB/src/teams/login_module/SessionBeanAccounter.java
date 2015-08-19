package teams.login_module;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionBeanAccounter {

	public static HttpSession getSession(){
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}
	
	public static HttpServletRequest getRequest(){
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public static String getUserName(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return session.getAttribute("accounter").toString();
	}
	
	public static String getUserId(){
		HttpSession session = getSession();
		if (session != null) {
			return (String) session.getAttribute("accounterid");
		}
		else {
			return null;
		}
	}
}
