package test.helper;

import com.google.gson.annotations.SerializedName;

public class Bank {

	private String payment_method;
	private String bank_country_code;
	private String account_name;
	private String account_number;
	private String swift_code;
	
	@SerializedName(value = "aba", alternate = {"bsb"}) 
	private String aba;

	/**
	 * @return the payment_method
	 */
	public String getPayment_method() {
		return payment_method;
	}

	/**
	 * @param payment_method the payment_method to set
	 */
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	/**
	 * @return the bank_country_code
	 */
	public String getBank_country_code() {
		return bank_country_code;
	}

	/**
	 * @param bank_country_code the bank_country_code to set
	 */
	public void setBank_country_code(String bank_country_code) {
		this.bank_country_code = bank_country_code;
	}

	/**
	 * @return the account_name
	 */
	public String getAccount_name() {
		return account_name;
	}

	/**
	 * @param account_name the account_name to set
	 */
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	/**
	 * @return the account_number
	 */
	public String getAccount_number() {
		return account_number;
	}

	/**
	 * @param account_number the account_number to set
	 */
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	/**
	 * @return the swift_code
	 */
	public String getSwift_code() {
		return swift_code;
	}

	/**
	 * @param swift_code the swift_code to set
	 */
	public void setSwift_code(String swift_code) {
		this.swift_code = swift_code;
	}

	/**
	 * @return the aba
	 */
	public String getAba() {
		return aba;
	}

	/**
	 * @param aba the aba to set
	 */
	public void setAba(String aba) {
		this.aba = aba;
	}

	@Override
	public String toString() {
		return payment_method + ":" + bank_country_code + ":" + account_name + ":" + account_number + ":"
				+ swift_code + ":" + aba;
	}
}
