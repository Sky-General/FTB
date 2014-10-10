package me.skyGeneral.ftb.API;

public enum PlayerState {
	SPECTATOR("Spectator"), INGAME("Ingame"), INLOBBY("Inlobby");
	String state;

	PlayerState(String state) {
		this.state = state;
	}

}
