package com.camp.app.camp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CampService {
	public List<CampVO> showCampAll();
	public List<CampVO> showCampByPage(int page);
	public int count();
	public int getMaxCampId();
	public int getMaxCampImageId();
	public CampVO showCampOne(int campId);
	public boolean addCamp(InputCampVO camp);
}
