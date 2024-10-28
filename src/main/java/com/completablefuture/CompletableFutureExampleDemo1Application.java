package com.completablefuture;

import io.micrometer.common.util.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootApplication
public class CompletableFutureExampleDemo1Application {

	public static void main(String[] args) {

		SpringApplication.run(CompletableFutureExampleDemo1Application.class, args);

		//StringUtil.isBlank - Checks if a string is null, empty (""), or consists only of whitespace characters.
		System.out.println("********StringUtil.isBlank***************************");
		System.out.println(StringUtils.isBlank(null));//true
		System.out.println(StringUtils.isBlank(""));//true
		System.out.println(StringUtils.isBlank("   "));//true
		System.out.println(StringUtils.isBlank("abc     "));//false
		//StringUtil.iSEmpty -- Checks if a string is null or empty ("").doesn't consider whitspace chars
		System.out.println("********StringUtil.iSEmpty***************************");
		System.out.println(StringUtils.isEmpty(null));//true
		System.out.println(StringUtils.isEmpty(""));//true
		System.out.println(StringUtils.isEmpty("   "));//false
		System.out.println(StringUtils.isEmpty("abc     "));//false
		// Optional-check if string is null
		System.out.println("********Optional***************************");
		System.out.println(Optional.ofNullable(null).isPresent());//false
		System.out.println(Optional.ofNullable("").isPresent());//true
		System.out.println(Optional.ofNullable("     ").isPresent());//true
		System.out.println(Optional.ofNullable("abcd    ").isPresent());//true
		//
		System.out.println("********StringUtil.isNotBlnak***************************");
		System.out.println(StringUtils.isNotBlank(null));//false
		System.out.println(StringUtils.isNotBlank(""));//false
		System.out.println(StringUtils.isNotBlank("   "));//false
		System.out.println(StringUtils.isNotBlank("abc     "));//true
		//BiDecimal Conversion

		System.out.println(new BigDecimal("123.45").movePointRight(2).setScale(0).toPlainString());
		System.out.println(new BigDecimal("123.4").movePointRight(1).setScale(0).toPlainString());
		System.out.println(new BigDecimal("123").movePointRight(2).setScale(0).toPlainString());
		System.out.println(new BigDecimal("00").movePointRight(2).setScale(0).toPlainString());


		System.out.println("************************************************");

		//123.45 - 12345 , 1234.5 - 12345 , 123.4 - 12340 , 1234 - 123400

		System.out.println(new BigDecimal("00").movePointRight(2).toPlainString().replace(".",""));
		//System.out.println(new BigDecimal("1234.5").movePointRight(2).toPlainString().replace(".",""));
		//System.out.println(new BigDecimal("123.4").movePointRight(2).toPlainString().replace(".",""));
		//System.out.println(new BigDecimal("1234").movePointRight(2).toPlainString().replace(".",""));

System.out.println("*******************************************");
		String origAuthAmt = "00";
		int length = origAuthAmt.length();
		int index = origAuthAmt.indexOf(".");
		if (index > 0) {
			if (length - index == 3) {
				// two digits after decimal
				origAuthAmt = origAuthAmt.replace(".", "");
			} else if (length - index == 2) {
				// two digits after decimal
				origAuthAmt = origAuthAmt.replace(".", "");
				origAuthAmt = origAuthAmt + "0";
			}
		} else {
			origAuthAmt = origAuthAmt + "00";
		}
		System.out.println(origAuthAmt);


	}

}
