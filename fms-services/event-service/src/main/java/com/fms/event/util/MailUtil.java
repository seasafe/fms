/**
 * 
 */
package com.fms.event.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.util.StringUtils;

/**
 * @author Kesavalu
 *
 */
@Component
public class MailUtil {
	
	@Autowired
    JavaMailSender javaMailSender;
	
	@Value("${domain.feedback.request.url}")
	private String domailFBRUrl;
	
	
	
	private String encodeValue(String value) {
	    try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	private String decodeValue(String value) {
	    try {
			return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	private Map<String,String> getDecodedMap(String url) {
		String decodedQuery = Arrays.stream(url.split("&"))
			      .map(param -> param.split("=")[0] + "=" + decodeValue(param.split("=")[1]))
			      .collect(Collectors.joining(","));
		Map<String, String> map = Arrays.stream(decodedQuery.split(","))
	            .map(s -> s.split("="))
	            .collect(Collectors.toMap(s -> s[0], s -> s[1]));
		return map;
		
	}
	private String getEncodedUrl(Map<String,String> map) {
		return map.keySet().stream()
			      .map(key -> key + "=" + encodeValue(map.get(key)))
			      .collect(Collectors.joining("&", "", ""));
	}
	
	public void sendEmail(String fromAddress,String[] toAddress,String subject,String textBody) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAddress);
        msg.setTo(toAddress);

        msg.setSubject(subject);
        msg.setText(textBody);

        javaMailSender.send(msg);

    }
	public void sendEmailWithAttachment(String fromAddress,String[] toAddress,String subject,String textBody, File file,String fileName) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		
        helper.setTo(toAddress);

        helper.setSubject(subject);

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText(textBody, true);

		// hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));
        if(file!= null)
        	helper.addAttachment(fileName, file);

        javaMailSender.send(msg);

    }
	
	private String getBase64Encoded(String value) {
		return Base64.getUrlEncoder().encodeToString(value.getBytes());
	}
	private String getBase64Decoded(String value) {
		return new String(Base64.getUrlDecoder().decode(value));
	}
	
	public String getBase64FeedbackUrl( Map<String,String> map) {
		return this.domailFBRUrl+"?params="+this.getBase64Encoded(this.getEncodedUrl(map));
	}
	
}
