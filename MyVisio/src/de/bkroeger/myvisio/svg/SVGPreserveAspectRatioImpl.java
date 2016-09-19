package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.svg.SVGPreserveAspectRatio;

public class SVGPreserveAspectRatioImpl implements SVGPreserveAspectRatio, Serializable {
	
	private static final long serialVersionUID = -2099916818882098267L;

	private short align;
	
	private short meetOrSlice;

	public SVGPreserveAspectRatioImpl() {
		this.align = SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_UNKNOWN;
		this.meetOrSlice = SVGPreserveAspectRatio.SVG_MEETORSLICE_UNKNOWN;
	}
	
	public SVGPreserveAspectRatioImpl(short align, short meetOrSlice) {
		this.align = align;
		this.meetOrSlice = meetOrSlice;
	}
	
	public SVGPreserveAspectRatio clone() {
		return new SVGPreserveAspectRatioImpl(this.align, this.meetOrSlice);
	}

	
	public short getAlign() {
		return this.align;
	}

	
	public void setAlign(short align) {
		this.align = align;
	}

	
	public short getMeetOrSlice() {
		return this.meetOrSlice;
	}

	
	public void setMeetOrSlice(short meetOrSlice) {
		this.meetOrSlice = meetOrSlice;
	}

}
