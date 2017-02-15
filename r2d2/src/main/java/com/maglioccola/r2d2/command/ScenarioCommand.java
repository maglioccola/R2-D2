package com.maglioccola.r2d2.command;

import java.util.List;

public class ScenarioCommand {

	private Integer larghezza;
	private Integer altezza;

	private List<OstacoloCommand> listaOstacoli;

	public Integer getLarghezza() {
		return larghezza;
	}

	public void setLarghezza(Integer larghezza) {
		this.larghezza = larghezza;
	}

	public Integer getAltezza() {
		return altezza;
	}

	public void setAltezza(Integer altezza) {
		this.altezza = altezza;
	}

	public List<OstacoloCommand> getListaOstacoli() {
		return listaOstacoli;
	}

	public void setListaOstacoli(List<OstacoloCommand> listaOstacoli) {
		this.listaOstacoli = listaOstacoli;
	}
}
