package com.tejgo.main.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tejgo.main.core.Booking;
import com.tejgo.main.core.Cab;
import com.tejgo.main.core.Driver;
import com.tejgo.main.core.OTP;
import com.tejgo.main.core.User;
import com.tejgo.main.dto.BookingDto;
import com.tejgo.main.dto.OTPDto;
import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.dto.RideResponse;
import com.tejgo.main.enums.ResponseStatus;
import com.tejgo.main.enums.RideStatus;
import com.tejgo.main.repository.BookingRepository;
import com.tejgo.main.repository.CabRepository;
import com.tejgo.main.repository.DriverRepository;
import com.tejgo.main.repository.OtpRepostiory;
import com.tejgo.main.repository.UserRepository;
import com.tejgo.main.security.JwtUser;
import com.tejgo.main.service.BookingService;
import com.tejgo.main.service.OtpService;
import com.tejgo.main.utils.Utils;

@Service
public class BookingServiceImpl implements BookingService {

	Boolean isSmsSent = false;

	Boolean verified = false;

	@Value("${sms.text}")
	private String smsText;

	@Value("${sms.admin.text}")
	private String smsAdminText;

	@Value("${sms.otp.template.id}")
	private String otpTemplateId;

	@Value("${sms.admin.otp.template.id}")
	private String adminOtpTemplateId;

	@Autowired
	private DriverRepository driverRepository;

	@Autowired
	private OtpRepostiory otpRepostiory;

	@Autowired
	private OtpService otpService;

	@Autowired
	private OtpServiceImpl otpServiceImpl;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CabRepository cabRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private BookingService bookingService;

	@Override
	public ResponseVO<BookingDto> searchCabDriver(BookingDto bookingDto) {

		Random random = new Random();

		JwtUser jwtUser = getUserByToken();

		List<Driver> driverList = driverRepository.findByStatus("ACTIVE");

		if (!driverList.isEmpty()) {
			int randomDriverId = random.nextInt(driverList.size());

			Driver randomDriver = driverList.get(randomDriverId);

			Optional<Driver> driver = driverRepository.findById(randomDriver.getDriverId());

			Cab cab = cabRepository.findByCabNumber(driver.get().getCabNumber());

			Optional<Cab> dbcab = cabRepository.findById(cab.getCabId());

			// send OTP to customer
			Optional<User> user = userRepository.findFirstByEmail(jwtUser.getUsername());

			if (user.isPresent()) {
				isSmsSent = otpServiceImpl.generateOtp(user.get().getPhoneNumber(), smsText, otpTemplateId);
			}
			if (isSmsSent) {
				Booking booking = new Booking();
				booking.setCab(dbcab.get());
				booking.setDriver(driver.get());
				booking.setDropoffDatetime(null);
				booking.setPickupLocation(bookingDto.getPickupLocation());
				booking.setDropoffLocation(bookingDto.getDropoffLocation());
				booking.getFareAmount();
				booking.setPickupDatetime(null);
				booking.setStatus(RideStatus.BOOKED.toString());
				booking.setUser(user.get());
				bookingRepository.save(booking);
				return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(), bookingDto);
			}

		}
		return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.FAIL.name(), bookingDto);
	}

	private JwtUser getUserByToken() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (Objects.nonNull(object) && object instanceof JwtUser) {
			return ((JwtUser) object);
		}

		return null;
	}

	@Override
	public ResponseVO<OTPDto> validOtpAndstartRide(String otp) {
		JwtUser jwtUser = getUserByToken();

		Optional<User> userDriver = userRepository.findFirstByEmail(jwtUser.getUsername());

		Booking bookingDb = new Booking();

		Optional<Driver> driver=driverRepository.findById(userDriver.get().getUserId());
		
		Optional<Driver> optionalDriver = Optional.ofNullable(driverRepository.findByUser(userDriver.get()));

		List<Booking> bookings = bookingRepository.findByDriverAndStatus(optionalDriver.get(), "BOOKED");

		if (Objects.nonNull(bookings.get(0).getBookingId())) {
			bookingDb = bookingRepository.findById(bookings.get(0).getBookingId()).get();
		}

		Optional<User> userCustomer = userRepository.findById(bookingDb.getUser().getUserId());

		verified = otpService.verifyOtp(userCustomer.get().getPhoneNumber(), otp.toString());
		Optional<OTP> findByOtp;
		if (verified) {
			findByOtp = otpRepostiory.findByOtpAndUser(otp, userCustomer.get());
			otpService.updateOtp(findByOtp.get(), otp);
			bookingService.updatePickUpDateTime(userCustomer, Utils.generatedAtDate());

		}
		return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(), "OTP is Validated", null);
	}

	@Override
	public Booking updatePickUpDateTime(Optional<User> user, Date generatedAtDate) {

		Booking bookingDb = new Booking();

		Optional<Booking> booking = bookingRepository.findByUserAndStatus(user.get(), "BOOKED");

		if (Objects.nonNull(booking.get().getBookingId())) {
			bookingDb = bookingRepository.findById(booking.get().getBookingId()).get();
		}
		bookingDb.setPickupDatetime(generatedAtDate);
		bookingDb.setStatus(RideStatus.ENROUTE.toString());
		bookingRepository.save(bookingDb);
		return bookingDb;
	}

	@Override
	public ResponseVO<RideResponse> endstartRide() {

		JwtUser jwtUser = getUserByToken();

		Optional<User> userDriver = userRepository.findFirstByEmail(jwtUser.getUsername());

		Optional<Driver> driver=driverRepository.findById(userDriver.get().getUserId());
		
		Optional<Driver> optionalDriver = Optional.ofNullable(driverRepository.findByUser(userDriver.get()));

		Booking bookingDb = new Booking();

		 List<Booking> bookings = bookingRepository.findByDriverAndStatus(optionalDriver.get(), "ENROUTE");
		 
		//Optional<Booking> booking = bookingRepository.findByUserAndStatus(user.get(), "ENROUTE");

		if (Objects.nonNull(bookings.get(0).getBookingId())) {
			bookingDb = bookingRepository.findById(bookings.get(0).getBookingId()).get();
		}
		bookingDb.setDropoffDatetime(Utils.generatedAtDate());
		bookingDb.setStatus(RideStatus.COMPLETED.toString());
		bookingRepository.save(bookingDb);

		RideResponse res = new RideResponse();
		res.setPickupLocation(bookingDb.getPickupLocation());
		res.setDropLocation(bookingDb.getDropoffLocation());
		res.setJournyStartTime(Utils.generatedDateToString(bookingDb.getPickupDatetime()));
		res.setJournyEndTime(Utils.generatedDateToString(bookingDb.getDropoffDatetime()));
		res.setTotalFareAmount(null);

		return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(), "Thanks for Using TejGO App",
				res);
	}

}
