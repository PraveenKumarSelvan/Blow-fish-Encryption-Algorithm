package infosecurity;

import java.io.UnsupportedEncodingException;

public class Encryptions_Main{
	@SuppressWarnings("static-access")
	public static void main(String args[]) throws UnsupportedEncodingException
	{
		String key= "top sceret";								// Secret Key 
		String text = "This is a random sample text";			// Plain Text
		byte[] iv;											// Initialization Vector
		
		/* 
		 *  Blowfish CBC implementation. 
		 * 
		 */
		System.out.println("__________________ CBC IMPLEMENTATION ______________");
		Fscrypt cbc_pgm = new Fscrypt();
		
		byte[] encrypted_data1 = cbc_pgm.fs_encrypt(text,key);
		PrintByte(encrypted_data1,"Encrypted data");

		byte[] decrypted_data1 = cbc_pgm.fs_decrypt(encrypted_data1,key);
		PrintByte(decrypted_data1,"Decrypted data");
		
		String decrypted_string1 = new String(decrypted_data1,"utf-8");
		System.out.println("Decrypted String : " + decrypted_string1);
		
		iv = cbc_pgm.ivBytes;
		
		/* 
		 *  Blowfish ECB implementation. 
		 * 
		*/
		
		System.out.println("\n__________________ ECB IMPLEMENTATION ______________\n");
		Fscrypt2 ecb_pgm = new Fscrypt2();
		
		byte[] encrypted_data = ecb_pgm.fs_encrypt(text,key,iv);
		PrintByte(encrypted_data,"Encrypted data");

		byte[] decrypted_data = ecb_pgm.fs_decrypt(encrypted_data,key,iv);
		PrintByte(decrypted_data,"Decrypted data");
	
		String decrypted_string = new String(decrypted_data,"utf-8");
		System.out.println("Decrypted String : " + decrypted_string.substring(0,text.length()));
	 
	}

	private static void PrintByte(byte[] encrypted_data, String text) {
		try{
		System.out.println(text + " bytes : ");
		for(byte b: encrypted_data)
			System.out.print(b + " ");
		System.out.println("\n"+ text + " : " + new String(encrypted_data,"utf-8") + "\n");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
