package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;

import org.testng.annotations.Factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import test.helper.Bank;

public class PayloadTestFactory {

	@Factory
	public Object[] createInstances() {
		
		String path = convertToPlatformPath("./sources/Payload.json");
		Bank[] bank = getBankData(path);
		
		Object[] result = new Object[bank.length];
		
		for (int i = 0; i < bank.length; i++) {
			
			result[i] = new PayloadTest(bank[i]);
		}
		return result;
	}

	private Bank[] getBankData(String path) {
		
		BufferedReader reader = null;
		Bank[] bank = null;
		
		try {
			
			reader = new BufferedReader(new FileReader(new File(path)));
			Gson gson = new GsonBuilder().create();

			bank = gson.fromJson(reader, Bank[].class);

			for (Bank b : bank) {
				System.out.println(b);
			}

		} catch (JsonIOException ex) {
			ex.getStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bank;

	}
	
	private String convertToPlatformPath(String path) {
		String cp = null;

		cp = path.replaceAll("\\\\", Matcher.quoteReplacement(File.separator));

		return cp;

	}
}
