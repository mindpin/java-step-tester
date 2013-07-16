package mindpin.java_step_tester.modle;

import mindpin.java_step_tester.modle.gson.Asset;

public class TextItem {
	private String name;
	private String desc;
	private boolean success;
	private String exception;

	public TextItem(String name, String desc, boolean success, String exception){
		this.name = name;
		this.desc = desc;
		this.success = success;
		this.exception = exception;
	}
	
	public static TextItem build_success_item(String method_name, String method_desc){
		return new TextItem(method_name, method_desc, true, "");
	}

	public static TextItem build_failure_item(String method_name, String method_desc, String exception) {
		return new TextItem(method_name, method_desc, false, exception);
	}

	public Asset to_asset() {
		String text_desc = name; 
		if(desc != null && !desc.equals("")){
			text_desc = desc;
		}
		return new Asset(text_desc, success, exception);
	}
	
}
