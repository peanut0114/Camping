package com.camp.app.used.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.camp.app.camp.service.CampImageVO;
import com.camp.app.used.service.InputUsedVO;
import com.camp.app.used.service.UsedImageVO;
import com.camp.app.used.service.UsedService;
import com.camp.app.used.service.UsedVO;

@CrossOrigin(originPatterns = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT})
//@CrossOrigin(origins="*")
@RestController
@RequestMapping("/used")
public class UsedController {
	
	
	@Autowired
	UsedService service;
	
	//등록
	@PostMapping("/usedInsert")
	public boolean insertUsed(InputUsedVO used) {
		System.out.println(used);
		return service.insertUsed(used);
	}
	
	//수정
	@PutMapping("/usedUpdate")
	public void updateUsed(@RequestBody UsedVO used) {
		service.updateUsed(used);
	}
	
	//거래상태수정
	@PutMapping("/dealUpdate")
	public void updateDealStatus(@RequestBody UsedVO used) {
		service.updateUsed(used);
	}
	
	//게시물 상태 수정 (제한)
	@PutMapping("/statusRestrict")
	public void restrictStatus(@RequestBody UsedVO used) {
		service.restrictUsed(used);
	}
	
	//게시물 상태 수정 (삭제)
	@PutMapping("/statusUpdate")
	public void updateStatus(@RequestBody UsedVO used) {
		service.deleteUsed(used);
	}
	
	//전체조회
	@GetMapping("/usedMain")
	public List<UsedVO> selectAllUsedList() {
		return service.selectAllUsedList();
	}
	
	//키워드검색 조회
	@PostMapping("/search/{keyword}")
	public List<UsedVO> findUsedKeyword(@PathVariable String keyword){
		return service.findUsedKeyword(keyword);
	}
	
	//필터검색  ?
	@GetMapping("/usedSearch")
	public List<UsedVO> searchUsedList(){
		return service.searchUsedList();
	}
	
	//단건조회
	@GetMapping("/usedDetail/{usedId}")
	public UsedVO findDetail(@PathVariable int usedId) {
		service.updateCnt(usedId);
		return service.findDetail(usedId);
	}
		
	//사진
	@GetMapping("/usedImage/{usedId}")
	public List<UsedImageVO> getCampImageList(@PathVariable("usedId") int usedId){
		System.out.println(usedId);
		return service.showUsedImageByUsedId(usedId);
	}
	
	@GetMapping("/showImage/{usedPath}/{usedStoredName}")
	public ResponseEntity<Resource> showImage(@PathVariable String usedPath, @PathVariable String usedStoredName){
		String fullPath = "d:\\upload\\used\\" + usedPath + "\\" + usedStoredName;
		System.out.println("*** FullPath : " +fullPath);
		Resource resource = new FileSystemResource(fullPath);
		
		if(!resource.exists()) {
			System.out.println("File Not Found ! ");
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		
		try {
			filePath = Paths.get(fullPath);
			System.out.println(filePath);
			header.add("Content-Type", Files.probeContentType(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
}