package mindpin.java_step_tester.modle.gson;

public class Asset {
	public String test_description;
	public boolean  result;
	public String exception;
	
	public String getTest_description() {
		return test_description;
	}
	public void setTest_description(String test_description) {
		this.test_description = test_description;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public Asset(String test_description, boolean result, String exception) {
		super();
		this.test_description = test_description;
		this.result = result;
		this.exception = exception;
	}
	
	
}