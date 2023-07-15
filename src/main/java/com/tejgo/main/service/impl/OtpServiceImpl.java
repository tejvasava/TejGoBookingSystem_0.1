package com.tejgo.main.service.impl;

import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tejgo.main.core.OTP;
import com.tejgo.main.core.User;
import com.tejgo.main.repository.OtpRepostiory;
import com.tejgo.main.repository.UserRepository;
import com.tejgo.main.service.OtpService;
import com.tejgo.main.utils.Utils;

@Service
public class OtpServiceImpl implements OtpService {

	private static Long increment = 0L;
	
	@Autowired
	private OtpRepostiory OtpRepostiory;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OtpServiceImpl.class);
	
	private static final Integer EXPIRES_MIN = 10;

	@Value("${sms.url}")
	private String smsUrl;

	@Value("${sms.username}")
	private String smsUsername;

	@Value("${sms.password}")
	private String smsPassword;

	@Value("${sms.gsm}")
	private String smsGsm;

	@Value("${sms.enable}")
	private Boolean enableSms;

	@Value("${mobile.testing}")
	public String testingMobile;

	@Value("${sms.entity.id}")
	private String entityId;

	@Value("${sms.apikey}")
	private String apikey;
	
	private LoadingCache<String, String> otpCache;
	
	
	public OtpServiceImpl() {
		super();

		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRES_MIN, TimeUnit.MINUTES)
				.build(new CacheLoader<String, String>() {

					@Override
					public String load(String key) throws Exception {
						return StringUtils.EMPTY;
					}
				});
	}
	
	@Override
	public Boolean generateOtp(String key, String smsText, String templateId) {
		
		String phoneNumber=key;
		Optional<User> user= userRepository.findByPhoneNumber(phoneNumber);
		OTP otp=new OTP();
		
		if (StringUtils.equals(testingMobile, key)) {

			String randomOtp = "4321";
			otpCache.put(key, randomOtp);

			return true;

		} else if (enableSms) {
			String randomOtp = RandomStringUtils.randomNumeric(4);

			otpCache.put(key, randomOtp);

			String smsTxt = StringUtils.replace(smsText, "{code}", randomOtp);
			
			//my logic
			
			otp.setGeneratedAt(Utils.generatedAtDate());
			otp.setOtp(randomOtp);
			//otp.setOtpId(null);
			otp.setUser(user.get());
			otp.setVerificationAttempts(0L);
			otp.setVerified(false);
			
			OtpRepostiory.save(otp);
			//end
			
			return sendSms(key, smsTxt, templateId);
		} else {
			String randomOtp = "1234";
			otpCache.put(key, randomOtp);
			otp.setGeneratedAt(Utils.generatedAtDate());
			otp.setOtp(randomOtp);
			//otp.setOtpId(null);
			otp.setUser(user.get());
			otp.setVerificationAttempts(0L);
			otp.setVerified(false);
			
			OtpRepostiory.save(otp);
			return true;
		}
	}

	@Override
	public Boolean verifyOtp(String mobileNo, String userOtp) {
		String otp = otpCache.getIfPresent(mobileNo);
		return StringUtils.equals(otp, userOtp);
	}

	@Override
	public boolean sendSms(String phoneNo, String smsText, String templateId) {
		// {code} is OTP for TejGo App Verification. Your OTP is valid for 2 minutes.
		try {
			RestTemplate restTemplate = new RestTemplate();

			URI uri = UriComponentsBuilder
					.fromHttpUrl(smsUrl)
					.queryParam("APIkey",apikey)
					.queryParam("SenderID", smsGsm)
					.queryParam("SMSType", "2")
					//.queryParam("UserPassword", smsPassword)
					.queryParam("Mobile", phoneNo)
					.queryParam("MsgText", URLEncoder.encode(smsText, "UTF-8"))
					.queryParam("EntityID", entityId)
					.queryParam("TemplateID", templateId)
					//.queryParam("GSM", smsGsm)
					.build(true).toUri();
			
			ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

			if (response.getStatusCode() == HttpStatus.OK && StringUtils.containsIgnoreCase(response.getBody(), "Ok")) {
				return true;
			}

			return false;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return false;
		}
	}

	@Override
	public OTP updateOtp(OTP otp, String generatedotp) {
		String randomOtp = RandomStringUtils.randomNumeric(4);
		otp.setOtp(generatedotp+randomOtp);
		otp.setVerified(true);
		otp.setVerificationAttempts(increment++);
		return OtpRepostiory.save(otp);
	}

}
