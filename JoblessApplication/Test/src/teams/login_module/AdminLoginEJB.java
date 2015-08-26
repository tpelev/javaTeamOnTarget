package teams.login_module;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import model.Admin;

@Stateless
@NamedQuery(name = "Login.selectquery", query ="SELECT a FROM Admin a WHERE a.loginName = :loginName")
public class AdminLoginEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	public Admin getAdminUserName(String userName){
		Admin admin;
		try{
		Query query = em.createQuery("SELECT a FROM Admin a WHERE a.loginName = :loginName", Admin.class).setParameter("loginName", userName);
		 admin = (Admin) query.getSingleResult();
		}catch(Exception e){
			return null;
		}
		return admin;
	}
	public boolean validateUserNameAndPassword(String userName, String password){
		try {
			Admin admin = getAdminUserName(userName);
			//hashing
			if (admin.getLoginPassword().equals(password)) {
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
