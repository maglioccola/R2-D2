package com.maglioccola.r2d2.service;

import com.maglioccola.r2d2.command.R2D2Command;
import com.maglioccola.r2d2.kernel.model.R2D2;
import com.maglioccola.r2d2.kernel.model.Scenario;

public interface R2D2Service {

	R2D2 createR2D2(R2D2Command command);

	R2D2 navigate(Scenario scenario, R2D2 r2d2, String navigationCommand);

}
