package com.cultuzz.channel.template.pojo;

public class Vorlage_gutscheine {

	String text;
	String text2;
	String gutschein_text;
	String gueltigkeit_text;
	int gueltigkeit;
	
	
	
	public String getGutschein_text() {
		return gutschein_text;
	}
	public String getGueltigkeit_text() {
		return gueltigkeit_text;
	}
	public int getGueltigkeit() {
		return gueltigkeit;
	}
	
	
	public String getText() {
		return text;
	}
	public String getText2() {
		return text2;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
	public void setGutschein_text(String gutschein_text) {
		this.gutschein_text = gutschein_text;
	}
	public void setGueltigkeit_text(String gueltigkeit_text) {
		this.gueltigkeit_text = gueltigkeit_text;
	}
	public void setGueltigkeit(int gueltigkeit) {
		this.gueltigkeit = gueltigkeit;
	}
	
}
