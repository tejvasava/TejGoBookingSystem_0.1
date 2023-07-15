package com.tejgo.main.service;

import com.tejgo.main.core.OTP;

public interface OtpService {

	Boolean generateOtp(String key, String smsText, String templateId);

	Boolean verifyOtp(String mobileNo, String userOtp);

	boolean sendSms(String phoneNo, String smsText, String templateId);

	OTP updateOtp(OTP otp, String generatedotp);

}
