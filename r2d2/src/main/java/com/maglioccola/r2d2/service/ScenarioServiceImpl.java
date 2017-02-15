package com.maglioccola.r2d2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.maglioccola.r2d2.command.OstacoloCommand;
import com.maglioccola.r2d2.command.ScenarioCommand;
import com.maglioccola.r2d2.kernel.model.Ostacolo;
import com.maglioccola.r2d2.kernel.model.Scenario;

@Service
public class ScenarioServiceImpl implements ScenarioService {

	@Override
	public Scenario createScenario(ScenarioCommand command) {
		Scenario scenario = Scenario.Builder.build(command.getLarghezza(), command.getAltezza());
		List<Ostacolo> listaOstacoli = new ArrayList<Ostacolo>();
		for (OstacoloCommand ostacoloCommand : command.getListaOstacoli()) {
			Ostacolo ostacolo = Ostacolo.Builder.build(ostacoloCommand.getPosX(), ostacoloCommand.getPosY(),
					ostacoloCommand.getAltezza());
			listaOstacoli.add(ostacolo);
		}
		scenario.addOstacoli(listaOstacoli);
		return scenario;
	}

}
