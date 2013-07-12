package com.modle;

import java.util.List;

public class ResponseModle {
	public String error;
	public boolean success;
	public List<Assets> assets;
	
	
	
	public String getError() {
		return error;
	}



	public void setError(String error) {
		this.error = error;
	}



	public boolean isSuccess() {
		return success;
	}



	public void setSuccess(boolean success) {
		this.success = success;
	}



	public List<Assets> getAssets() {
		return assets;
	}



	public void setAssets(List<Assets> assets) {
		this.assets = assets;
	}



	public ResponseModle(String error, boolean success, List<Assets> assets) {
		super();
		this.error = error;
		this.success = success;
		this.assets = assets;
	}

	

	
}
