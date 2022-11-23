package com.diallo.restfull.applicationventes.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartFileFormatter implements Formatter<MultipartFile> {

	@Override
	public String print(MultipartFile object, Locale locale) {

		return object.getOriginalFilename();
	}

	@Override
	public MultipartFile parse(String text, Locale locale) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
