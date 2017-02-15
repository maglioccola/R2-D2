package com.maglioccola.r2d2.command;

public class NavigateCommand {

	private ScenarioCommand scenario;
	private R2D2Command r2d2;
	private String navigationCommand = "";

	public ScenarioCommand getScenario() {
		return scenario;
	}

	public void setScenario(ScenarioCommand scenario) {
		this.scenario = scenario;
	}

	public R2D2Command getR2d2() {
		return r2d2;
	}

	public void setR2d2(R2D2Command r2d2) {
		this.r2d2 = r2d2;
	}

	public String getNavigationCommand() {
		return navigationCommand;
	}

	public void setNavigationCommand(String navigationCommand) {
		this.navigationCommand = navigationCommand;
	}

}