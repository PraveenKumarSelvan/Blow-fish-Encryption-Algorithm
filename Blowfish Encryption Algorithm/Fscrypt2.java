package infosecurity;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.*;
import javax.crypto.spec.*;

public class Fscrypt2 {

	public byte[] fs_encrypt(String text, String key,byte[] iv) {
		try {
			SecretKey skey = new SecretKeySpec(key.getBytes(),"Blowfish");	
			Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);

			if(text.length()% 8 != 0)
				text = paddedTo8(text);
		
			byte[] encrypted_data = new byte[text.length()];
			int idx_data=0;
			byte[] encrypt_tmp =iv;
			byte[] input_byte;

			for(int i =8,prev =0;;prev=i,i+=8)
			{
				String tmp = text.substring(prev, i);
				input_byte = tmp.getBytes();
				input_byte = XOR(input_byte,encrypt_tmp);
				encrypt_tmp = cipher.doFinal(input_byte);

				for(byte b: encrypt_tmp)
					encrypted_data[idx_data++] = b;

				if(i>= text.length())
					break;
			}
			return 	encrypted_data;
		} 
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) 
		{	
			e.printStackTrace();
		}
		return null;
	}

	public byte[] fs_decrypt(byte[] ciphertext, String key, byte[] iv) {
		try {
			SecretKey skey = new SecretKeySpec(key.getBytes(),"Blowfish");
			Cipher cipher  = Cipher.getInstance("Blowfish/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			int data_idx=0;
			byte[] decrypted_data = new byte[ciphertext.length];
			byte[] tmp_decrpty;
			byte[] prev_cipher = iv;

			for(int prev=0,i=8;; prev=i,i+=8)
			{
				byte[] tmp = Arrays.copyOfRange(ciphertext, prev, i);
				tmp_decrpty = cipher.doFinal(tmp);

				for(byte b: XOR(prev_cipher,tmp_decrpty))
					decrypted_data[data_idx++] = b;
				prev_cipher = Arrays.copyOf(tmp,tmp.length);

				if(i>=ciphertext.length)
					break;
			}
			return decrypted_data;
		} 
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {

			e.printStackTrace();
		}
		return null;
	}

	private static String paddedTo8(String text) 
	{
		int diff = 8 - text.length()%8;
		String val = String.valueOf(diff);
		String pad= "";
		for(int i=0; i<diff;i++)
			pad +=val;

		return text+pad;
	}


	public static byte[] XOR(byte[] ar1,byte[] ar2){

		for(int i=0; i<8; i++)
			ar1[i] =(byte) (ar1[i]^ ar2[i]);
		return ar1;
	}
}
