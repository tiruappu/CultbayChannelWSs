package com.cultuzz.channel.template.pojo;



public class Vorlage {
	
	
	int ebayoptionen;
	String ebaybetreffzeile;
	String untertitel;
	int ebaykategorieid1;
	String ebaykategorieid2;
	String ueberschrift;
	String text;
	int ebayueberschrifthighlight;
	int ebayueberschriftfett;
	int ebaygaleriebild;
	int top_startseite;
	int ebaytop;
	int template_id;
	
	public int getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}
	public int getEbayueberschrifthighlight() {
		return ebayueberschrifthighlight;
	}
	public int getEbayueberschriftfett() {
		return ebayueberschriftfett;
	}
	public int getEbaygaleriebild() {
		return ebaygaleriebild;
	}
	public int getTop_startseite() {
		return top_startseite;
	}
	public int getEbaytop() {
		return ebaytop;
	}
	public void setEbayueberschrifthighlight(int ebayueberschrifthighlight) {
		this.ebayueberschrifthighlight = ebayueberschrifthighlight;
	}
	public void setEbayueberschriftfett(int ebayueberschriftfett) {
		this.ebayueberschriftfett = ebayueberschriftfett;
	}
	public void setEbaygaleriebild(int ebaygaleriebild) {
		this.ebaygaleriebild = ebaygaleriebild;
	}
	public void setTop_startseite(int top_startseite) {
		this.top_startseite = top_startseite;
	}
	public void setEbaytop(int ebaytop) {
		this.ebaytop = ebaytop;
	}
	public int getEbayoptionen() {
		return ebayoptionen;
	}
	public String getEbaybetreffzeile() {
		return ebaybetreffzeile;
	}
	public String getUntertitel() {
		return untertitel;
	}
	public int getEbaykategorieid1() {
		return ebaykategorieid1;
	}
	public String getEbaykategorieid2() {
		return ebaykategorieid2;
	}
	public String getUeberschrift() {
		return ueberschrift;
	}
	public String getText() {
		return text;
	}
	public void setEbayoptionen(int ebayoptionen) {
		this.ebayoptionen = ebayoptionen;
	}
	public void setEbaybetreffzeile(String ebaybetreffzeile) {
		this.ebaybetreffzeile = ebaybetreffzeile;
	}
	public void setUntertitel(String untertitel) {
		this.untertitel = untertitel;
	}
	public void setEbaykategorieid1(int ebaykategorieid1) {
		this.ebaykategorieid1 = ebaykategorieid1;
	}
	public void setEbaykategorieid2(String ebaykategorieid2) {
		this.ebaykategorieid2 = ebaykategorieid2;
	}
	public void setUeberschrift(String ueberschrift) {
		this.ueberschrift = ueberschrift;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "Vorlage [ebayoptionen=" + ebayoptionen + ", ebaybetreffzeile="
				+ ebaybetreffzeile + ", untertitel=" + untertitel
				+ ", ebaykategorieid1=" + ebaykategorieid1
				+ ", ebaykategorieid2=" + ebaykategorieid2 + ", ueberschrift="
				+ ueberschrift + ", text=" + text
				+ ", ebayueberschrifthighlight=" + ebayueberschrifthighlight
				+ ", ebayueberschriftfett=" + ebayueberschriftfett
				+ ", ebaygaleriebild=" + ebaygaleriebild + ", top_startseite="
				+ top_startseite + ", ebaytop=" + ebaytop + ", template_id="
				+ template_id + "]";
	}
}
