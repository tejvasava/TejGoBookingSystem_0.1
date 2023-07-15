package com.tejgo.main.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tejgo.main.core.User;
import com.tejgo.main.core.UserRole;
import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.dto.UserRoleDto;
import com.tejgo.main.enums.ResponseStatus;
import com.tejgo.main.repository.UserRepository;
import com.tejgo.main.repository.UserRoleRepository;
import com.tejgo.main.security.JwtUser;
import com.tejgo.main.service.UserRoleService;
import com.tejgo.main.utils.Messages;



@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	@Override
	public ResponseVO<Void> addEditRole(UserRoleDto roleVO) {
		try {

			ResponseVO<Void> resVo = validateRequest(roleVO);

			if (resVo == null) {
				UserRole userRole = new UserRole();

				if (Objects.nonNull(roleVO.getId())) {
					userRole = userRoleRepository.findById(roleVO.getId()).get();
				}

				if (!ObjectUtils.isEmpty(userRole.getRoleId())) {
					Optional<UserRole> userOpt = userRoleRepository.findById(roleVO.getId());
					if (userOpt.isPresent()) {
						userRole = userOpt.get();
					}
				}

				userRole.setRoleName(roleVO.getRoleName().toUpperCase());

				userRoleRepository.save(userRole);
				return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
						roleVO.getId() == null ? Messages.USER_SUBMIT_SUCCESS : Messages.USER_UPDATE_SUCCESS);
			}

			return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.FAIL.name(),
					roleVO.getId() == null ? Messages.PERMISSION_DENIED : Messages.USER_UPDATE_SUCCESS);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage());
		}
		
	}

	private ResponseVO<Void> validateRequest(UserRoleDto role) {
		Boolean isvalid = Boolean.TRUE;

		if (StringUtils.isBlank(role.getRoleName())) {
			isvalid = Boolean.FALSE;
		}
		
		if (!isvalid) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.APP_SUBMIT_FAILURE);
		}

		return null;
	}

	@Override
	public UserRole getUserRoleByRoleId(Long id) {
		Optional<UserRole> userRole = userRoleRepository.findById(id);

		if (userRole.isPresent()) {
			return userRole.get();
		}
		return null;
	}

	@Override
	public UserRoleDto getUserVoByid(Long id) {
		Optional<UserRole> user = userRoleRepository.findById(id);
		if (user.isPresent()) {
			return convertToVO(user.get(), Boolean.TRUE);
		}
		return null;
	}

	private UserRoleDto convertToVO(UserRole userRole, Boolean includePassword) {
		UserRoleDto vo = new UserRoleDto();
		BeanUtils.copyProperties(userRole, vo);
		vo.setId(userRole.getRoleId());
		vo.setRoleName(userRole.getRoleName());
		/*
		 * vo.setPassword(userRole.getPassword()); if (!includePassword) {
		 * vo.setPassword(null); }
		 */
		
		return vo;
	}

	@Override
	public Page<UserRoleDto> findAllUsersRolesList(int pageNo, int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("roleName").ascending());
		 Page<UserRole> userRolePage = userRoleRepository.findAll(pageRequest);
         return userRolePage.map(obj -> convertToVO(obj, Boolean.TRUE));
	}

	public UserRole getUserRoleById(Long roleId) {
		Optional<UserRole> userRole = userRoleRepository.findById(roleId);
		if (userRole.isPresent()) {
			return userRole.get();
		}
		return null;
	}

	private JwtUser getUserByToken() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (Objects.nonNull(object) && object instanceof JwtUser) {
			return ((JwtUser) object);
		}

		return null;
	}

}
