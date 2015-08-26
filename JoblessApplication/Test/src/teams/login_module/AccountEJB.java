package teams.login_module;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import model.Accounter;
import model.Admin;
import model.User;

/**
 * Session Bean implementation class AccountEJB
 */
@Stateless

public class AccountEJB {

	@PersistenceContext
	private EntityManager em;
    
	public Accounter getAccountUserName(String userName){
		Query query = em.createQuery("SELECT a FROM Accounter a WHERE a.loginName = '"+userName+"'");
		Accounter accounter = (Accounter)query.getSingleResult();
		return accounter;
	}
	
	public boolean validateUserNameAndPassword(String userName, String password){
		try {	
			Accounter account = getAccountUserName(userName);
			//hashing
			if (account.getLoginPassword().equals(password)) {
				return true;
			}
			else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}	
	}
	public String hashing(String loginName, String loginPassword){
		HashFunction hash = Hashing.sha256();
		String salt = hash.newHasher().putString(loginName, Charsets.UTF_8).hash().toString();
		String pass = hash.newHasher().putString(loginPassword, Charsets.UTF_8).hash().toString();
		HashCode hs = hash.newHasher().putString(pass, Charsets.UTF_8).putString(salt, Charsets.UTF_8).hash();
		return hs.toString();
	}

}
