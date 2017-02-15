package com.maglioccola.r2d2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maglioccola.r2d2.command.NavigateCommand;
import com.maglioccola.r2d2.command.R2D2Command;
import com.maglioccola.r2d2.command.ScenarioCommand;
import com.maglioccola.r2d2.kernel.model.R2D2;
import com.maglioccola.r2d2.kernel.model.Scenario;
import com.maglioccola.r2d2.service.R2D2Service;
import com.maglioccola.r2d2.service.ScenarioService;

@RestController
public class R2D2Controller {

	@Autowired
	private R2D2Service r2d2Service;

	@Autowired
	private ScenarioService scenarioService;

	@RequestMapping(value = "/test-server-status", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> testServerStatus() {
		return new ResponseEntity<String>("Server started successfully", HttpStatus.OK);
	}

	@PostMapping("/initR2D2")
	public @ResponseBody ResponseEntity<R2D2> initR2D2(final @RequestBody R2D2Command command) {
		R2D2 r2d2 = r2d2Service.createR2D2(command);
		return new ResponseEntity<R2D2>(r2d2, HttpStatus.OK);
	}

	@RequestMapping(value = "/initAmbiente", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Scenario> initAmbiente(final @RequestBody ScenarioCommand command) {
		Scenario scenario = scenarioService.createScenario(command);
		return new ResponseEntity<Scenario>(scenario, HttpStatus.OK);
	}

	@RequestMapping(value = "/navigate", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<R2D2> navigate(final @RequestBody NavigateCommand command) {
		Scenario scenario = scenarioService.createScenario(command.getScenario());
		R2D2 r2d2 = r2d2Service.createR2D2(command.getR2d2());
		R2D2 result = r2d2Service.navigate(scenario, r2d2, command.getNavigationCommand());
		return new ResponseEntity<R2D2>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/server-shutdown")
	public void serverShutdown() {
		System.exit(0);
	}

}
