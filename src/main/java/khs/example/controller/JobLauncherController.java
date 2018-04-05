package khs.example.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLauncherController {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("helloworldjob")
	private Job job;

	@Autowired
	@Qualifier("stockticker")
	private Job stockTickerJob;

	@RequestMapping("/launchhelloworld")
	public String handle() throws Exception {

		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "Job Done, check console...";
	}

	@RequestMapping(value = "/helloWorld", produces="application/json", method=RequestMethod.GET)
	public ResponseEntity<String> helloWorldLaunch() {
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(job, jobParameters);
			String message = "Job Complete. Check the Console for additional logs...";
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			String error = "An Error Occured while processing.";
			return new ResponseEntity<String>(error + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping("/launchstockticker")
	public String stockticker() throws Exception {

		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(stockTickerJob, jobParameters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "Job Done, check console...";
	}
	
	@RequestMapping(value="/stockTicker", produces="application/json",  method=RequestMethod.GET)
	public ResponseEntity<String> launchStockSticker(){
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(stockTickerJob, jobParameters);
			String message = "Job Complete  Check the console for additional logs...";
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			String error= "An Error Occured while processing.";
			return new ResponseEntity<String>(error + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}