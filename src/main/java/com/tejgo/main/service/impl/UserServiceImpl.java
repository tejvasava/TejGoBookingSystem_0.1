package com.tejgo.main.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tejgo.main.core.User;
import com.tejgo.main.core.UserRole;
import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.dto.UserDto;
import com.tejgo.main.enums.ResponseStatus;
import com.tejgo.main.repository.UserRepository;
import com.tejgo.main.repository.UserRoleRepository;
import com.tejgo.main.service.UserService;
import com.tejgo.main.utils.Messages;



@Service
@ComponentScan(basePackages = "com.tejgo.main.security")
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public ResponseVO<UserDto> addEditEmployee(UserDto employeeDto) {
		try {

			ResponseVO resVo = validateRequest(employeeDto);

			if (resVo == null) {
				User user = new User();

				if (Objects.nonNull(employeeDto.getEmployeeId())) {
					user = userRepository.findById(employeeDto.getEmployeeId()).get();
				}
				user.setEmail(employeeDto.getEmail());
				user.setAddress(employeeDto.getAddress());
				user.setUserId(employeeDto.getEmployeeId());
				user.setFirstName(employeeDto.getFirstName());
				user.setLastName(employeeDto.getLastName());
				user.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
				user.setPhoneNumber(employeeDto.getPhoneNumber());
				Optional<UserRole> userRole = userRoleRepository.findById(employeeDto.getUserRoleId());
				user.setUserRole(userRole.get());

				userRepository.save(user);

				return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
						employeeDto.getEmployeeId() == null ? Messages.EMPLOYEE_SUBMIT_SUCCESS
								: Messages.EMPLOYEE_UPDATE_SUCCESS,
						employeeDto);

			}
			return resVo;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage(), employeeDto);
		}
	}

	private ResponseVO validateRequest(UserDto employeeDto) {
		
		if (!StringUtils.isNotBlank(employeeDto.getFirstName())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.EMPLOYEE_FIRSTNAME);
		}
		
		if (!StringUtils.isNotBlank(employeeDto.getAddress())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.EMPLOYEE_ADDRESS);
		}
		
		if (!StringUtils.isNotBlank(employeeDto.getEmail())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.EMPLOYEE_EMAIL);
		}
		
		if (!StringUtils.isNotBlank(employeeDto.getLastName())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.EMPLOYEE_LASTNAME);
		}
		
		if (!StringUtils.isNotBlank(employeeDto.getPhoneNumber())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.PHONE_NUMBER);
		}
		return null;
	}

	@Override
	public Page<UserDto> findAllEmployee(int pageNo, int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("firstName").ascending());
		Page<User> employeePage = userRepository.findAll(pageRequest);
		return employeePage.map(obj -> convertToVO(obj, Boolean.TRUE));
	}

	private UserDto convertToVO(User user, Boolean true1) {
		UserDto vo = new UserDto();
		//BeanUtils.copyProperties(user,vo); 
		vo.setAddress(user.getAddress());
		vo.setEmail(user.getEmail());
		vo.setEmployeeId(user.getUserId());
		vo.setFirstName(user.getFirstName());
		vo.setLastName(user.getLastName());
		vo.setPhoneNumber(user.getPhoneNumber());
		//vo.setUserRole(employee.getUserRole());
		vo.setUserRoleId(user.getUserRole().getRoleId());
		return vo; 
		
	}

	@Override
	public UserDto getEmployeeVOById(Long id) {
		Optional<User> employee = userRepository.findById(id);
		if (employee.isPresent()) {
			return convertToVO(employee.get(), Boolean.TRUE);
		}
		return null;
	}

	@Override
	public User getUserByUserName(String email) {
		Optional<User> user = userRepository.findFirstByEmail(email);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	public User getUserById(Long employeeId) {
		Optional<User> employee = userRepository.findById(employeeId);
		if (employee.isPresent()) {
			return employee.get();
		}
		return null;
	}

	@Override
	public ResponseVO<UserDto> addEditCustomer(UserDto customerDto) {
		try {

			ResponseVO resVo = validateRequest(customerDto);

			if (resVo == null) {
				User user = new User();

				if (Objects.nonNull(customerDto.getEmployeeId())) {
					user = userRepository.findById(customerDto.getEmployeeId()).get();
				}
				user.setEmail(customerDto.getEmail());
				user.setAddress(customerDto.getAddress());
				user.setUserId(customerDto.getEmployeeId());
				user.setFirstName(customerDto.getFirstName());
				user.setLastName(customerDto.getLastName());
				user.setPassword(passwordEncoder.encode(customerDto.getPassword()));
				//employee.setPassword(employeeDto.getPassword());
				user.setPhoneNumber(customerDto.getPhoneNumber());
				Optional<UserRole> userRole = userRoleRepository.findById(customerDto.getUserRoleId());
				user.setUserRole(userRole.get());
				//user.setUserRole(null);

				userRepository.save(user);
				
				return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
						customerDto.getEmployeeId() == null ? Messages.CUSTOMER_SUBMIT_SUCCESS
								: Messages.CUSTOMER_UPDATE_SUCCESS,
								customerDto);

			}
			return resVo;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage(), customerDto);
		}
	}

}
