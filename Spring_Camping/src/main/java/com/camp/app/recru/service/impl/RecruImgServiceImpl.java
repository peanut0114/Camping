package com.camp.app.recru.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.camp.app.recru.mapper.RecruImgMapper;
import com.camp.app.recru.mapper.RecruMapper;
import com.camp.app.recru.service.RecruImgService;
import com.camp.app.recru.service.RecruImgVO;

@Service
public class RecruImgServiceImpl implements RecruImgService {

	@Autowired
	RecruMapper recruMapper;
	@Autowired
	RecruImgMapper mapper;
	
	@Override
	public boolean insertRecruImg(List<MultipartFile> files) {
		//경로 설정
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		//등록날짜로 경로 설정
		String directoryPath = sdf.format(date);
		String uploadPath = "D:\\upload\\camp\\recruGear\\"+directoryPath;
		//폴더 주소
		File uploadPathDir = new File(uploadPath);
		if(!uploadPathDir.exists()) {
			uploadPathDir.mkdirs();
		}
		files.forEach(file->{
			RecruImgVO img = new RecruImgVO();
			img.setOriginName(file.getOriginalFilename());
			img.setImgFormat(img.getOriginName().substring(img.getOriginName().indexOf('.')));
			img.setImgSize(file.getSize());
			img.setImgPath(uploadPath);
			img.setRecruId(recruMapper.getMaxRecruId());
			//uuid를 통한 랜덤으로 아이디 주기
			UUID uuid = UUID.randomUUID();
			String[] uuids = uuid.toString().split("-");
			img.setStoredName(uuids[0]+"_"+img.getOriginName());
			//업로드
			File uploadFile = new File(uploadPath, img.getStoredName());
			
			try {
				file.transferTo(uploadFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mapper.insertRecru(img);
		});
		return true;
	}

	@Override
	public List<RecruImgVO> findImg(int recruId) {
		return null;
	}

}
