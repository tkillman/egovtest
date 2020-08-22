package egovframework.com.cpa.model;

public enum CpaExcelMappingEnum {
	GOUBUN(0,"교번"), NAME(1,"이름"), ADDR(2,"주소");

	private int excelNum;
	private String excelName;
	
	CpaExcelMappingEnum(int excelNum, String excelName){
		this.excelNum = excelNum;
		this.excelName = excelName;
	}

	public int getExcelNum() {
		return excelNum;
	}

	public void setExcelNum(int excelNum) {
		this.excelNum = excelNum;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
}
