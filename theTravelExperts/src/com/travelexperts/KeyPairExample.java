package com.travelexperts;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class KeyPairExample {
	public KeyPairExample() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		KeyPairGenerator myKPG = KeyPairGenerator.getInstance("RSA");
		
		myKPG.initialize(512);
		KeyPair myKP = myKPG.genKeyPair();
		
		Cipher myCipher = Cipher.getInstance("RSA");
		
		myCipher.init(Cipher.ENCRYPT_MODE, myKP.getPublic());
		byte[] bytEncryted = myCipher.doFinal("This message will be encrypted".getBytes());
		
		myCipher.init(Cipher.DECRYPT_MODE, myKP.getPrivate());
		byte[] bytDecrypted = myCipher.doFinal(bytEncryted);
		
		System.out.println("Encrypted: " + new String(bytEncryted, "UTF8"));
		System.out.println("Decrypted: " + new String(bytDecrypted, "UTF8"));
	}
	
	public static void main(String args[]) {
		try {
			new KeyPairExample();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		finally {
		}
	}

}
