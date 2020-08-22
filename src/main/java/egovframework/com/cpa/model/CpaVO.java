package egovframework.com.cpa.model;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class CpaVO {
	
	private String gisu;
	
	private MultipartFile uploadFile;

	public String getGisu() {
		return gisu;
	}

	public void setGisu(String gisu) {
		this.gisu = gisu;
	}

	public MultipartFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	public CpaVO build() {
		return new CpaVO();
	}
	
	public CpaVO gisu(String gisu) {
		this.gisu = gisu;
		return this;
	}
	
	public CpaVO uploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
		return this;
	}
}
