package khs.example.cucumber;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import khs.example.job.helloworld.Processor;

public class BatchStepDefinitionTest{

	private List<String> messages = new ArrayList<String>();
	private List<String> output = new ArrayList<String>();;
	
	
	private Processor processor = new Processor();

	@Given("a set of data is read in")
	public void loadData() {
	    this.messages.add("Hello World!");
	    this.messages.add("Welcome to Spring Batch!");
	}
	
	@When("that data is processed")
	public void processData() throws Exception {
		for(String s : this.messages){
			String mess = this.processor.process(s);
			Assert.assertNotNull(mess);
			this.output.add(mess);
		}
	}

	@Then("the output data is all uppercase")
	public void outputData() {
		for(int i=0; i < this.messages.size(); i++){
			Assert.assertNotEquals(this.messages.get(i), this.output.get(i));
			System.out.println("Initial Message: " + this.messages.get(i));
			System.out.println("Processed Message: " + this.output.get(i));
		}
	}
}
