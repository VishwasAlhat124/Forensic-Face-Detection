// com.rec.service.OtpService.java
package com.rec.service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class OtpService {

	private static class OtpEntry {
		String otp;
		Instant expiresAt;
	}

	private final Map<String, OtpEntry> otpStore = new ConcurrentHashMap<>();
	private final Random random = new Random();

	public String generateOtp(String email, long ttlSeconds) {
		String otp = String.format("%06d", random.nextInt(1_000_000));
		OtpEntry entry = new OtpEntry();
		entry.otp = otp;
		entry.expiresAt = Instant.now().plusSeconds(ttlSeconds);
		otpStore.put(email.toLowerCase(), entry);
		return otp;
	}

	public boolean validateOtp(String email, String otp) {
		OtpEntry entry = otpStore.get(email.toLowerCase());
		if (entry == null)
			return false;
		if (Instant.now().isAfter(entry.expiresAt)) {
			otpStore.remove(email.toLowerCase());
			return false;
		}
		boolean ok = entry.otp.equals(otp);
		if (ok) {
			otpStore.remove(email.toLowerCase());
		}
		return ok;
	}
}
