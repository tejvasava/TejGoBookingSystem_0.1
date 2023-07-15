package com.tejgo.main.service.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tejgo.main.core.Cab;
import com.tejgo.main.core.Driver;
import com.tejgo.main.dto.CabDto;
import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.enums.ResponseStatus;
import com.tejgo.main.repository.CabRepository;
import com.tejgo.main.service.CabService;
import com.tejgo.main.utils.Messages;

@Service
public class CabServiceImpl implements CabService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CabServiceImpl.class);
	@Autowired
	private CabRepository cabRepository;
	
	@Override
	public ResponseVO<CabDto> addEditCab(CabDto cabDto) {
		try {
			ResponseVO resVo = validateRequest(cabDto);
			if (resVo == null) {
				Cab cab = new Cab();
				if (Objects.nonNull(cabDto.getCabId())) {
					cab = cabRepository.findById(cabDto.getCabId()).get();
				}
				cab.setCabNumber(cabDto.getCabNumber());
				cab.setCapacity(cabDto.getCapacity());
				cab.setModel(cabDto.getModel());
				cab.setYear(cabDto.getYear());
				cabRepository.save(cab);
				return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
						cabDto.getCabId()== null ? Messages.CAB_SUBMIT_SUCCESS
								: Messages.CAB_UPDATE_SUCCESS,
								cabDto);
			}
			return resVo;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage(), cabDto);
		}
	}

	private ResponseVO validateRequest(CabDto cabDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
