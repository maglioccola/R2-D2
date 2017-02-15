package com.maglioccola.r2d2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maglioccola.r2d2.command.R2D2Command;
import com.maglioccola.r2d2.kernel.base.LocalizableObject.Orientamento;
import com.maglioccola.r2d2.kernel.base.Point;
import com.maglioccola.r2d2.kernel.base.Robot;
import com.maglioccola.r2d2.kernel.exceptions.InsufficientBatteryException;
import com.maglioccola.r2d2.kernel.model.Ostacolo;
import com.maglioccola.r2d2.kernel.model.R2D2;
import com.maglioccola.r2d2.kernel.model.Scenario;

@Service
public class R2D2ServiceImpl implements R2D2Service {

	@Override
	public R2D2 createR2D2(R2D2Command command) {
		int posX = command.getPosX();
		int posY = command.getPosY();
		char orientamento = command.getOrientamento().charAt(0);
		int livelloBatteria = command.getBatteryLevel();
		return R2D2.Builder.build(posX, posY, Orientamento.getOrientamentoBySigla(orientamento), livelloBatteria);
	}

	@Override
	public R2D2 navigate(Scenario scenario, R2D2 r2d2, String navigationCommand) {
		String[] commands = navigationCommand.split("-");
		for (String command : commands) {
			int consumoBatteria;
			Azione azione = Azione.getAzioneBySigla(command.trim());
			if (azione.equals(Azione.ROTATE_RIGHT)) {
				r2d2.rotateRight();
			} else {
				if (azione.equals(Azione.ROTATE_LEFT)) {
					r2d2.rotateLeft();
				} else {
					Point nextPoint = this.getNextPoint(r2d2, azione);
					int altezzaOstacolo = this.altezzaOstacolo(scenario, nextPoint);
					if (altezzaOstacolo > 0) {
						consumoBatteria = altezzaOstacolo;
					} else {
						consumoBatteria = Robot.BATTERY_CONSUMPTION_FOR_SINGLE_ACTION;
					}
					this.spostaR2D2(scenario, r2d2, consumoBatteria, nextPoint);
				}
			}
			if (r2d2.getBatteryLevel() == 0) {
				break;
			}
		}
		return r2d2;

	}

	/**
	 * Verifica se è presente un ostacolo nella posizione di destinazione e
	 * restituisce l'eventuale altezza dell'ostacolo
	 * 
	 * @param scenario
	 * @param r2d2
	 * @param nextPoint
	 * @return Altezza dell'ostacolo
	 */
	private int altezzaOstacolo(Scenario scenario, Point nextPoint) {
		List<Ostacolo> listOstacolo = scenario.getListaOstacoli();
		for (Ostacolo ostacolo : listOstacolo) {
			if (ostacolo.positionEquals(nextPoint)) {
				return ostacolo.getAltezza();
			}
		}
		return 0;
	}

	private void spostaR2D2(Scenario scenario, Robot r2d2, int consumoBatteria, Point nextPoint) {
		try {
			r2d2.consumeBattery(consumoBatteria);
			if (nextPoint.getPosX() > scenario.getLarghezza()) {
				r2d2.setPosX(1);
			} else {
				if (nextPoint.getPosX() > 0) {
					r2d2.setPosX(nextPoint.getPosX());
				} else {
					r2d2.setPosX(scenario.getLarghezza());
				}
			}
			if (nextPoint.getPosY() > scenario.getAltezza()) {
				r2d2.setPosY(1);
			} else {
				if (nextPoint.getPosY() > 0) {
					r2d2.setPosY(nextPoint.getPosY());
				} else {
					r2d2.setPosY(scenario.getAltezza());
				}
			}
		} catch (InsufficientBatteryException ex) {
			// Esco senza completare l'ultimo spostamento
		}
	}

	private Point getNextPoint(Robot r2d2, Azione azione) {
		Point point = new Point();
		Orientamento orientamento = r2d2.getOrientamento();
		boolean direzioneNord = orientamento.equals(Orientamento.NORD) && azione.equals(Azione.FORWARD)
				|| orientamento.equals(Orientamento.SUD) && azione.equals(Azione.BACKWARD);
		boolean direzioneSud = orientamento.equals(Orientamento.SUD) && azione.equals(Azione.FORWARD)
				|| orientamento.equals(Orientamento.NORD) && azione.equals(Azione.BACKWARD);
		boolean direzioneOvest = orientamento.equals(Orientamento.OVEST) && azione.equals(Azione.FORWARD)
				|| orientamento.equals(Orientamento.EST) && azione.equals(Azione.BACKWARD);
		boolean direzioneEst = orientamento.equals(Orientamento.EST) && azione.equals(Azione.FORWARD)
				|| orientamento.equals(Orientamento.OVEST) && azione.equals(Azione.BACKWARD);
		if (direzioneNord) {
			point.setPosX(r2d2.getPosX());
			point.setPosY(r2d2.getPosY() - 1);
		} else {
			if (direzioneSud) {
				point.setPosX(r2d2.getPosX());
				point.setPosY(r2d2.getPosY() + 1);
			} else {
				if (direzioneOvest) {
					point.setPosX(r2d2.getPosX() - 1);
					point.setPosY(r2d2.getPosY());
				} else {
					if (direzioneEst) {
						point.setPosX(r2d2.getPosX() + 1);
						point.setPosY(r2d2.getPosY());
					}
				}
			}
		}
		return point;
	}

	public enum Azione {
		FORWARD("F"), BACKWARD("B"), ROTATE_LEFT("L"), ROTATE_RIGHT("R");

		private final String sigla;

		private Azione(String sigla) {
			this.sigla = sigla;
		}

		public String getSigla() {
			return this.sigla;
		}

		public static Azione getAzioneBySigla(String sigla) throws IllegalArgumentException {
			for (Azione azione : Azione.values()) {
				if (azione.getSigla().equals(sigla)) {
					return azione;
				}
			}
			throw new IllegalArgumentException("Azione non valida");
		}
	}
}
