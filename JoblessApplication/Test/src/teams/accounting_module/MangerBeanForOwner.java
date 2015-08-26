package teams.accounting_module;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import model.Owner;


@ManagedBean(name = "ownerBean")
public class MangerBeanForOwner {

	@EJB
	private OwnerEJB ejbOwner;
	private Owner owner;
	
	//return Owner information
	public Owner getOwnerInformation(){
		setOwner(ejbOwner.getOwnerInformation(1));
//		owner = ejbOwner.getOwnerInformation(1);
		return owner;
	}
	
	//Getters and Setters
	public OwnerEJB getEjbOwner() {
		return ejbOwner;
	}
	
	public void setEjbOwner(OwnerEJB ejbOwner) {
		this.ejbOwner = ejbOwner;
	}
	
	public Owner getOwner() {
		return owner;
	}
	
	public void setOwner(Owner owner) {
		this.owner = owner;
	}

}
