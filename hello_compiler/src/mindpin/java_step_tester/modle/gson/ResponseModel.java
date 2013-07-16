package mindpin.java_step_tester.modle.gson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class ResponseModel {
	public String error;
	public boolean success;
	public List<Asset> assets;
	
	
	
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



	public List<Asset> getAssets() {
		return assets;
	}



	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}



	public ResponseModel(String error, boolean success, List<Asset> assets) {
		super();
		this.error = error;
		this.success = success;
		this.assets = assets;
	}
	
	public String to_json(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public static ResponseModel build_complie_error_response(){
		ArrayList<Asset> assets = new ArrayList<Asset>();
		return new ResponseModel("代码编译异常", false, assets);
	}

	
}
