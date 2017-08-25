/*
 * Created By: Abhinav Kumar Mishra
 * Copyright &copy; 2017. Abhinav Kumar Mishra. 
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.abhinavmishra14.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

/**
 * The Class RestAppUtils.
 */
public final class RestAppUtils {

	/**
	 * Gets the expiry date.<br/>
	 * Expire by parameter takes input as Calendar.Month, Calendar.Hour, Calendar.Minute etc. 
	 *
	 * @param expireBy the expire by 
	 * @param expireByValue the expire by value
	 * @return the expiry date
	 * @see Calendar
	 */
	public static String getExpiryDate(final int expireBy, final int expireByValue) {
		final Date currentDate = new Date();
	    final Calendar cal = Calendar.getInstance();
	    cal.setTime(currentDate);
	    cal.add(expireBy, expireByValue);
	    final SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
		return sdf.format(cal.getTime());
	}
	
	/**
	 * Valiate expiry date.
	 *
	 * @param expiryDate the expiry date
	 * @return true, if successful
	 */
	public static boolean valiateExpiryDate(final Date expiryDate) {
		final Date currentDate = new Date();
	    final Calendar calCurrent = Calendar.getInstance();
	    calCurrent.setTime(currentDate);
	    final Date dateNow = calCurrent.getTime();
	    
	    final Calendar expCal = Calendar.getInstance();
	    expCal.setTime(expiryDate);
	    
	    final Date dateExp = expCal.getTime();
	    return dateNow.after(dateExp); //Current date is after expirydate
	}
	
	/**
	 * Gets the date from string.
	 *
	 * @param expiryDate the expiry date
	 * @return the date from string
	 * @throws ParseException 
	 */
	public static Date getDateFromString(final String expiryDate)
			throws ParseException {
		final SimpleDateFormat dtFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss"); 
		return dtFormat.parse(expiryDate);
	}
	
	/**
	 * Encode bytes to base64.
	 *
	 * @param binaryData the binary data
	 * @return the string
	 */
	public static String encodeBytesToBase64String(final byte[] binaryData){
		return Base64.encodeBase64String(binaryData);
	}
	
	/**
	 * Encode bytes to base64.
	 *
	 * @param binaryData the binary data
	 * @return the byte[]
	 */
	public static byte[] encodeBytesToBase64(final byte[] binaryData){
		return Base64.encodeBase64(binaryData);
	}
	
	/**
	 * Decode bse64 string to bytes.
	 *
	 * @param encodedStr the encoded str
	 * @return the byte[]
	 */
	public static byte[] decodeBse64StringToBytes(final String encodedStr){
		return Base64.decodeBase64(encodedStr);
	}
	
	/**
	 * Decode bse64 string.
	 *
	 * @param encodedStr the encoded str
	 * @return the string
	 */
	public static String decodeBse64String(final String encodedStr){
		return new String (decodeBse64StringToBytes(encodedStr));
	}
	
	/**
	 * Gets the random string.
	 *
	 * @param length the length
	 * @return the random string
	 */
	public static String getRandomString(final int length) {
		final String charset = "123456789abcdefghjkmnpqrstuvwxyz";
		final Random random = new Random(System.currentTimeMillis());
		final StringBuilder strBuilder = new StringBuilder();
		for (int each = 0; each < length; each++) {
			int pos = random.nextInt(charset.length());
			strBuilder.append(charset.charAt(pos));
		}
		return strBuilder.toString().toLowerCase();
	}
		
	/**
	 * Instantiates a new rest app utils.
	 */
	private RestAppUtils (){
		super();
	}
}
