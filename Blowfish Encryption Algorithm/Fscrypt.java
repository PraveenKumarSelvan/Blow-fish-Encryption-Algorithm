package infosecurity;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Fscrypt {
	static SecretKey skey;
	static IvParameterSpec iv;
	static byte[] ivBytes;
	public byte[] fs_encrypt(String plaintext, String key)  {		
		try{
			
			skey= new SecretKeySpec(key.getBytes(),"Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE,skey);

			byte[] encoded_val = cipher.doFinal(plaintext.getBytes());
			
			ivBytes = cipher.getIV();
			iv = new IvParameterSpec(ivBytes);
			 
			return encoded_val;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public byte[] fs_decrypt(byte[] ciphertext, String key){	
		try
		{
			Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey,iv);

			byte[] decoded_val = cipher.doFinal(ciphertext);
			return decoded_val;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}


