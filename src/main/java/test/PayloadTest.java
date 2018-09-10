package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import test.helper.Bank;

public class PayloadTest {

	String url = "http://preview.airwallex.com:30001/bank";

	HttpURLConnection httpConnection;
	OutputStreamWriter outputStreamWriter;
	Bank bankInfo;

	public PayloadTest(Bank bankInfo) {
		this.bankInfo = bankInfo;
	}

	@BeforeMethod
	public void getHttpConnection() {
		try {

			URL restApiURL = new URL(url);
			System.out.println(restApiURL);
			httpConnection = (HttpURLConnection) restApiURL.openConnection();
			httpConnection.setRequestMethod("POST");

			httpConnection.setDoOutput(true);
			httpConnection.setDoOutput(true);
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.connect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void payloadTest() {

		Gson gson = new GsonBuilder().create();
		int getReturnCode = -1;
		String input = "";
		try {
			input = gson.toJson(bankInfo);
			System.out.println(input);
			outputStreamWriter = new OutputStreamWriter(httpConnection.getOutputStream());
			outputStreamWriter.write(input);
			outputStreamWriter.flush();

			String output;
			BufferedReader responseBuffer;
			getReturnCode = httpConnection.getResponseCode();
			if (getReturnCode != 200) {
				try {
					responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getErrorStream())));
					System.out.println();
					System.out.println("Output from Server:  \n");
					while ((output = responseBuffer.readLine()) != null) {
						System.out.println(output);
						output = output + "\n";
					}

					// Assert.pass(output);
				} catch (Exception e) {
					// e.getStackTrace();
					int errorCode = fieldsCheck(bankInfo);
					Assert.assertEquals(getReturnCode / 100, errorCode / 100, errorMessage(errorCode)+" INPUT: "+input);
				}

//				throw new RuntimeException("HTTP GET Request Failed with Error code : "
//						+ httpConnection.getResponseCode() + " " + httpConnection.getResponseMessage());

			}

			responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));

			System.out.println();
			System.out.println("Output from Server:  \n");
			while ((output = responseBuffer.readLine()) != null) {
				System.out.println(output);
				output = output + "\n";
			}

		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int errorCode = fieldsCheck(bankInfo);
		Assert.assertEquals(getReturnCode / 100, errorCode / 100, errorMessage(errorCode)+" INPUT: "+input);

	}

	private int fieldsCheck(Bank bank) {
		// payment_method: mandatory, value should be either LOCAL or SWIFT
		String paymentMethod = bank.getPayment_method();
		// bank_country_code: mandatory, can be one of US, AU, CN
		String countryCode = bank.getBank_country_code();
		// account name: mandatory, any character, length from 2 to 10
		String accountName = bank.getAccount_name();
		// account number: mandatory
		// *for US, account number is 1-17 character long, can be any character
		// *for AU, account number is 6-9 character long, can be any character
		// *for CN, account number is 8-20 character long, can be any character
		String accountNumber = bank.getAccount_number();
		// swift_code:
		// *mandatory when payment method is SWIFT
		// *the 5th and 6th character of swift code should match the bank country code,
		// e.g. ICBK CN BJ is a valid swift code for CN
		// *swift code should be either 8 or 11 characters
		String swiftCode = bank.getSwift_code();
		// bsb:
		// *mandatory when bank country is AU
		// *6 characters
		// aba:
		// *mandatory when bank country is US
		// *9 characters
		// aba and bsb share an attribute
		String aba = bank.getAba();

		// payment_method
		if (paymentMethod.equals("LOCAL") || paymentMethod.equals("SWIFT")) {
		} else
			return 400;
		// bank_country_code and account number
		if (accountNumber == null || accountNumber.length() == 0) {return 401;}
		if (countryCode.equals("US") || countryCode.equals("AU") || countryCode.equals("CN")) {} 
		else return 410;
		
		if (countryCode.equals("US")) {
			if (accountNumber.length() >= 1 || accountNumber.length() <= 17) {} else return 402;
			if (!swiftCode.substring(4, 6).equals("US")) {return 407;} 
			if (aba.length() != 9){return 411;}
		}
		if (countryCode.equals("AU")) {
			if (accountNumber.length() >= 6 || accountNumber.length() <= 9) {} else return 403;
			if (!swiftCode.substring(4, 6).equals("AU")) {return 408;}
			if (aba.length() != 6){return 412;}
		}
		if (countryCode.equals("CN")) {
			if (accountNumber.length() >= 8 || accountNumber.length() <= 20) {} else return 404;
			if (!swiftCode.substring(4, 6).equals("CN")) { return 409;}
		}
		// account name
		if (accountName.length() >= 2 || accountName.length() <= 10) {} 
		else return 405;
		// swift_code
		if (swiftCode.length() == 8 || swiftCode.length() == 11) {
		} 
		else return 406;
		System.out.println(swiftCode.substring(4, 6));
	
		return 200;

	}

	private String errorMessage(int i) {
		String s = "";
		switch (i) {
		case 200:
			s = "\"success\": \"Bank details saved\"";
			break;
		case 400:
			s = "";
			break;
		case 401:
			s = "account_number' is required";
			break;
		case 402:
			s = "account_number length error: \\\"Length of account_number should be between 1 and 17 when bank_country_code is 'US'\\\"";
			break;
		case 403:
			s = "account_number length error: \"Length of account_number should be between 6 and 9 when bank_country_code is 'AU'\"";
			break;
		case 404:
			s = "account_number length error: \\\"Length of account_number should be between 8 and 20 when bank_country_code is 'CN'\\\"";
			break;
		case 405:
			s = "";
			break;
		case 406:
			s = "swift code length error: \"Length of 'swift_code' should be either 8 or 11";
			break;
		case 407:
			s = "wrong swift code: \"The swift code is not valid for the given bank country code: US\"";
			break;
		case 408:
			s = "wrong swift code: \"The swift code is not valid for the given bank country code: AU\"";
			break;
		case 409:
			s = "wrong swift code: \"The swift code is not valid for the given bank country code: CN\"";
			break;
		case 410:
			s = "";
			break;
		case 411:
			s = "";
			break;
		case 412:
			s = "";
			break;
		default:
			break;
		}

		return s;
	}

	@AfterMethod
	public void closeHttpConnection() {
		if (httpConnection != null) {
			httpConnection.disconnect();
		}
		if (outputStreamWriter != null) {
			try {
				outputStreamWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
