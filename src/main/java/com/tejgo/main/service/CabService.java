package com.tejgo.main.service;

import com.tejgo.main.dto.CabDto;
import com.tejgo.main.dto.ResponseVO;

public interface CabService {

	ResponseVO<CabDto> addEditCab(CabDto cabDto);

}
