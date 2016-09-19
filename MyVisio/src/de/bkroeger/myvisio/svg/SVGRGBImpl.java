package de.bkroeger.myvisio.svg;

import java.io.Serializable;

public class SVGRGBImpl implements Serializable {
	
	private static final long serialVersionUID = 1483726278811523508L;
	
	private int r;
	public int getR() { return this.r; }
	public String getHexR() { return convertToHex(r); }
	private int g;
	public int getG() { return this.g; }
	public String getHexG() { return convertToHex(g); }
	private int b;
	public int getB() { return this.b; }
	public String getHexB() { return convertToHex(b); }

	public SVGRGBImpl(int red, int green, int blue) {
		this.r = red;
		this.g = green;
		this.b = blue;
	}
	
	private String convertToHex(int i) {
		String hex = Integer.toHexString(i);
		if (hex.length() == 1) hex = "0"+hex;
		return hex;
	}
}
