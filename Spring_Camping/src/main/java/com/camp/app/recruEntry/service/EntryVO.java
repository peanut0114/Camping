package com.camp.app.recruEntry.service;
/*
 * 캠핑 동행 모집글에 대한 신청 VO
 * 담당 : 최유리
 * 221012
 */
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class EntryVO {

	private int entryId;
	private int recruId;
	private int entryCar;
	private String entryGear;
	@JsonFormat(pattern = "yyyyMMdd")
	private Date entryDate;
	private int entryStatus;		//신청상태 (0신청중,1수락,2거절,3취소대기(모집완료후),4취소완료)
	private String memberId;
}