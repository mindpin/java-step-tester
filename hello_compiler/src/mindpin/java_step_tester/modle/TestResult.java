package mindpin.java_step_tester.modle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import mindpin.java_step_tester.modle.gson.Asset;
import mindpin.java_step_tester.modle.gson.ResponseModel;

public class TestResult {
	public static String COMPILE_ERROR_RESULT = ResponseModel.build_complie_error_response().to_json();
	private HashMap<String, TextItem> success_map;
	private HashMap<String, TextItem> failure_map;
	
	public TestResult(){
		this.success_map = new HashMap<String, TextItem>();
		this.failure_map = new HashMap<String, TextItem>();
	}
	
	public void add_failure(String name, String text_desc, String exception){
		TextItem item = TextItem.build_failure_item(name, text_desc, exception);
		failure_map.put(name, item);
	}
	
	public boolean text_is_failure(String name){
		return (failure_map.get(name) != null);
	}
	
	public void add_success(String name, String text_desc){
		if(text_is_failure(name)){return;}
		
		TextItem item = TextItem.build_success_item(name, text_desc);
		success_map.put(name, item);
	}
	
	public boolean is_success(){
		return failure_map.isEmpty();
	}
	
	public String to_json(){
		List<Asset> assets_list = new ArrayList<Asset>();
		
		map_to_list(success_map, assets_list);
		map_to_list(failure_map, assets_list);
		
		ResponseModel response_model = new ResponseModel("", is_success(), assets_list);
		return response_model.to_json();
	}
	
	
	private void map_to_list(HashMap<String, TextItem> map, List<Asset> list){
		Set<String> names = map.keySet();
		
		for(String name : names){
			TextItem item = map.get(name);
			list.add(item.to_asset());
		}
	}
	
}
