import java.math.BigInteger;
import java.security.MessageDigest;

public class Commit {
	public Commit() {
		
	}
	
	public String generateSha1 (String str) {
		String value = str;
		String sha1 = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(value.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}

		return sha1;
	}
}
