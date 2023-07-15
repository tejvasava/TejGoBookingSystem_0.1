package com.tejgo.main.service;

import org.springframework.data.domain.Page;

import com.tejgo.main.core.UserRole;
import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.dto.UserRoleDto;


public interface UserRoleService {

	UserRole getUserRoleByRoleId(Long id);

	ResponseVO<Void> addEditRole(UserRoleDto role);

	UserRoleDto getUserVoByid(Long id);

	Page<UserRoleDto> findAllUsersRolesList(int pageNo, int pageSize);

}
