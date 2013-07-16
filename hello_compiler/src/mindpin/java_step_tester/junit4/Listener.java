package mindpin.java_step_tester.junit4;


import mindpin.java_step_tester.modle.TestResult;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class Listener extends RunListener {
	private TestResult test_result;
	
	public Listener(TestResult test_result) {
		this.test_result = test_result; 
	}

	@Override
	public void testAssumptionFailure(Failure failure) {
		super.testAssumptionFailure(failure);
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
		super.testFailure(failure);
		Description description = failure.getDescription();
		
		String text_desc = get_text_description(description);
		String name = description.getMethodName();
		String exception = failure.getException().getClass() == AssertionError.class ? "":failure.getException().toString();
		
		this.test_result.add_failure(name, text_desc, exception);
	}

	@Override
	public void testFinished(Description description) throws Exception {
		super.testFinished(description);
		String name = description.getMethodName();
		String text_desc = get_text_description(description);
		
		if(!this.test_result.text_is_failure(name)){
			this.test_result.add_success(name, text_desc);
		}
	}

	@Override
	public void testIgnored(Description description) throws Exception {
		super.testIgnored(description);
	}

	@Override
	public void testRunFinished(Result result) throws Exception {
		super.testRunFinished(result);
	}

	@Override
	public void testRunStarted(Description description) throws Exception {
		super.testRunStarted(description);
	}

	@Override
	public void testStarted(Description description) throws Exception {
		super.testStarted(description);
	}
	
	private String get_text_description(Description description){
		TestDescription text_desc = description.getAnnotation(TestDescription.class);
		String text_desc_str = "";
		if(text_desc != null && text_desc.value() != null && !text_desc.value().equals("")){
			text_desc_str = text_desc.value();
		}
		return text_desc_str;
	}
	
}
