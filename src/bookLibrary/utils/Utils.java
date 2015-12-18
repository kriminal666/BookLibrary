package bookLibrary.utils;



import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import bookLibrary.constants.Constants;

public final class Utils {

	 public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
	    {
	        int iterations = 1000;
	        char[] chars = password.toCharArray();
	        byte[] salt = getSalt().getBytes();
	         
	        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
	        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	        byte[] hash = skf.generateSecret(spec).getEncoded();
	        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	    }
	     
	    private static String getSalt() throws NoSuchAlgorithmException
	    {
	        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	        byte[] salt = new byte[16];
	        sr.nextBytes(salt);
	        return salt.toString();
	    }
	     
	    private static String toHex(byte[] array) throws NoSuchAlgorithmException
	    {
	        BigInteger bi = new BigInteger(1, array);
	        String hex = bi.toString(16);
	        int paddingLength = (array.length * 2) - hex.length();
	        if(paddingLength > 0)
	        {
	            return String.format("%0"  +paddingLength + "d", 0) + hex;
	        }else{
	            return hex;
	        }
	    }
	    
	    //Password validations
	    /**
	     * Validates the password
	     * @param originalPassword
	     * @param storedPassword
	     * @return
	     * @throws NoSuchAlgorithmException
	     * @throws InvalidKeySpecException
	     */
	    public static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
	    {
	        String[] parts = storedPassword.split(":");
	        int iterations = Integer.parseInt(parts[0]);
	        byte[] salt = fromHex(parts[1]);
	        byte[] hash = fromHex(parts[2]);
	         
	        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
	        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	        byte[] testHash = skf.generateSecret(spec).getEncoded();
	         
	        int diff = hash.length ^ testHash.length;
	        for(int i = 0; i < hash.length && i < testHash.length; i++)
	        {
	            diff |= hash[i] ^ testHash[i];
	        }
	        return diff == 0;
	    }
	    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
	    {
	        byte[] bytes = new byte[hex.length() / 2];
	        for(int i = 0; i<bytes.length ;i++)
	        {
	            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	        }
	        return bytes;
	    }
	    
	
	    /**
		 * Send message to the view
		 * 
		 * @param action
		 * @param fail
		 * @param exception
		 */
		
		public static void messageMaker(String action, boolean fail, String... msg){
			
			//Evaluate action
			switch(action){
				
				case Constants.INSERT :
					
					//error?
					if(!fail){
						
						FacesContext.getCurrentInstance().addMessage(null,
				                new FacesMessage(FacesMessage.SEVERITY_ERROR,"Insert error!", msg[0]));
						
					}else{
						
						FacesContext.getCurrentInstance().addMessage(null, 
								new FacesMessage(FacesMessage.SEVERITY_INFO,msg[0]+" Saved", msg[1]));
					}
					break;
					
				case Constants.UPDATE :
					
					break;
				case Constants.DELETE :
					
					break;
				case Constants.RETRIVE_ONE :
					
					if(!fail){
						
						FacesContext.getCurrentInstance().addMessage(null,
				                new FacesMessage(FacesMessage.SEVERITY_ERROR, msg[0], msg[1]));
								
					}else{
						
					}
					
					break;
				case Constants.RETRIVE_ALL :
					
					FacesContext.getCurrentInstance().addMessage(null,
			                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Retrive error! ", msg[0]));
					
					
					break;
				case Constants.GENERIC_SUCCESS :
					
					FacesContext.getCurrentInstance().addMessage(null,
			                new FacesMessage(FacesMessage.SEVERITY_INFO, msg[0], msg[1]));
					break;
				case Constants.GENERIC_ERROR :
					FacesContext.getCurrentInstance().addMessage(null,
			                new FacesMessage(FacesMessage.SEVERITY_ERROR, msg[0], msg[1]));
					break;
					
				default :
						
			}	
			
		}
		
		/**
		 * Reset inputs from form
		 * @param form
		 */
		public static void reset(String form) {
	      RequestContext.getCurrentInstance().reset(form);
	      
	  }
		
	
	

}
