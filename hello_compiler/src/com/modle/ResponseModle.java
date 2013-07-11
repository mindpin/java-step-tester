package com.modle;

import java.util.List;

public class ResponseModle {
	public String error;
	public boolean success;
	public List<Assets> assets_list;
	
	
	
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



	public List<Assets> getAssets_list() {
		return assets_list;
	}



	public void setAssets_list(List<Assets> assets_list) {
		this.assets_list = assets_list;
	}



	public ResponseModle(String error, boolean success, List<Assets> assets_list) {
		super();
		this.error = error;
		this.success = success;
		this.assets_list = assets_list;
	}

	
}
