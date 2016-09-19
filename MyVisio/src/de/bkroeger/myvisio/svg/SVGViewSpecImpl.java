package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.w3c.dom.svg.SVGAnimatedRect;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGPreserveAspectRatio;
import org.w3c.dom.svg.SVGTransformList;
import org.w3c.dom.svg.SVGViewSpec;
import org.w3c.dom.svg.SVGZoomAndPan;

public class SVGViewSpecImpl implements SVGViewSpec, Serializable {
	
	private static final long serialVersionUID = -4474105836408342330L;

	private SVGAnimatedRect viewBox;
	
	private SVGAnimatedPreserveAspectRatio preserveAspectRatio;
		
	private short zoomAndPan;
	
	private SVGTransformList transformList;
	
	private SVGElement viewTarget;

	/**
	 * Default-Konstruktor
	 */
	public SVGViewSpecImpl() {
		zoomAndPan = SVGZoomAndPan.SVG_ZOOMANDPAN_UNKNOWN;
		preserveAspectRatio = new SVGAnimatedPreserveAspectRatioImpl(
			new SVGPreserveAspectRatioImpl(
				SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_UNKNOWN,
				SVGPreserveAspectRatio.SVG_MEETORSLICE_UNKNOWN));
		transformList = new SVGTransformListImpl();
		viewTarget = null;
	}
	
	/**
	 * Konstruktor mit Werten.
	 * @param minX
	 * @param minY
	 * @param width
	 * @param height
	 */
	public SVGViewSpecImpl(float minX, float minY, float width, float height,
			SVGPreserveAspectRatio preserveAspectRatio) {
		
		zoomAndPan = SVGZoomAndPan.SVG_ZOOMANDPAN_UNKNOWN;	
		viewBox = new SVGAnimatedRectImpl(new SVGRectImpl(minX, minY, width, height));
		transformList = new SVGTransformListImpl();
		this.preserveAspectRatio = new SVGAnimatedPreserveAspectRatioImpl(
			preserveAspectRatio != null ? preserveAspectRatio :
				new SVGPreserveAspectRatioImpl(
					SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_UNKNOWN,
					SVGPreserveAspectRatio.SVG_MEETORSLICE_UNKNOWN));
		viewTarget = null;
	}

	
	public short getZoomAndPan() {
		return zoomAndPan;
	}

	
	public void setZoomAndPan(short zoomAndPan) {
		this.zoomAndPan = zoomAndPan;
	}

	
	public SVGAnimatedRect getViewBox() {
		return viewBox;
	}

	
	public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
		return preserveAspectRatio;
	}

	
	public SVGTransformList getTransform() {
		return transformList;
	}

	
	public SVGElement getViewTarget() {
		return viewTarget;
	}

	
	public String getViewBoxString() {
		return viewBox.toString();
	}

	
	public String getPreserveAspectRatioString() {
		return preserveAspectRatio.toString();
	}

	
	public String getTransformString() {
		return transformList.toString();
	}

	
	public String getViewTargetString() {
		return viewTarget.toString();
	}

}
