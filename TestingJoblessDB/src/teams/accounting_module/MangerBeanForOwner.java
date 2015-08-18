package teams.accounting_module;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import model.entity.Owner;

@ManagedBean(name = "ownerBean")
public class MangerBeanForOwner {

	@EJB
	private OwnerEJB ejbOwner;
	private Owner owner;
	
	public Owner getOwnerInformation(){
		owner = ejbOwner.getOwnerInformation(1);
		return owner;
	}
	
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
