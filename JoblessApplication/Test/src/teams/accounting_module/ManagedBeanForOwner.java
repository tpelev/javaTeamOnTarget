package teams.accounting_module;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import model.Owner;




/**
 * ManagedBean class using {@link OwnerEJB} methods
 * needet to set Owner information in JSFs (HTML pages)
 * 
 * @author Kaloyan Tsvetkov
 */
@ManagedBean(name = "ownerBean")
public class ManagedBeanForOwner {
	/* Class Fields */
	@EJB
	private OwnerEJB ejbOwner;
	private Owner owner;
	
	/**
	 * Method to get Owner information fom DataBase
	 * calling  OwnerEJB getOwnerInformation(int) method
	 * {@link OwnerEJB}
	 * @return Owner Object
	 * @author Kaloyan Tsvetkov
	 */
	public Owner getOwnerInformation(){
		setOwner(ejbOwner.getOwnerInformation(1)); //call OwnerEJB getOwnerInformation(int) method
		return owner;
	}
	
	/* Getters and Setters */
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
