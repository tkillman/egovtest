package egovframework.com.cpa.model;

import java.util.List;

public class CpaRegistVO {
	
	private Integer tbIdx; //key
	
	private String title; //제목
	
	private String content; //내용
	
	private String typeCd; // select박스
	
	private String drinkYn; //음주여부 라디오

	private List<CpaRegistDetailVO> cpaRegistDetailList; //상세내용
	
	public List<CpaRegistDetailVO> getCpaRegistDetailList() {
		return cpaRegistDetailList;
	}

	public void setCpaRegistDetailList(List<CpaRegistDetailVO> cpaRegistDetailList) {
		this.cpaRegistDetailList = cpaRegistDetailList;
	}

	public Integer getTbIdx() {
		return tbIdx;
	}

	public void setTbIdx(Integer tbIdx) {
		this.tbIdx = tbIdx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public String getDrinkYn() {
		return drinkYn;
	}

	public void setDrinkYn(String drinkYn) {
		this.drinkYn = drinkYn;
	}
	
	
}
