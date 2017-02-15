package com.maglioccola.r2d2.service;

import com.maglioccola.r2d2.command.ScenarioCommand;
import com.maglioccola.r2d2.kernel.model.Scenario;

public interface ScenarioService {

	Scenario createScenario(ScenarioCommand command);

}
