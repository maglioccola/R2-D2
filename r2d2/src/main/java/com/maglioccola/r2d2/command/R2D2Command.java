package com.maglioccola.r2d2.command;

public class R2D2Command {

	Integer posX;
	Integer posY;
	String orientamento;
	Integer batteryLevel;

	public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public String getOrientamento() {
		return orientamento;
	}

	public void setOrientamento(String orientamento) {
		this.orientamento = orientamento;
	}

	public Integer getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(Integer batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

}