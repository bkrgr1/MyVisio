package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.svg.SVGRect;

/**
 * <p>
 * Represents rectangular geometry. Rectangles are defined as consisting of a (x,y) 
 * coordinate pair identifying a minimum X value, a minimum Y value, and a width and height, 
 * which are usually constrained to be non-negative.
 * </p><p>
 * An SVGRect object can be designated as read only, which means that attempts 
 * to modify the object will result in an exception being thrown, as described below.
 * </p>
 * @author bk
 */
public class SVGRectImpl implements SVGRect, Serializable {
	
	private static final long serialVersionUID = -6109264765328496800L;

	private float x = 0.0f;
	
	private float y = 0.0f;
	
	private float width = 0.0f;
	
	private float height = 0.0f;
	
	
	public SVGRectImpl() {
		
	}
	
	public SVGRectImpl(float x, float y, float width, float height) {
	
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Object clone() {
		return new SVGRectImpl(this.x, this.y,
				this.width, this.height);
	}

	
	public float getX() {
		return x;
	}

	
	public void setX(float x) {
		this.x = x;
	}

	
	public float getY() {
		return y;
	}

	
	public void setY(float y) {
		this.y = y;
	}

	
	public float getWidth() {
		return width;
	}

	
	public void setWidth(float width) {
		this.width = width;
	}

	
	public float getHeight() {
		return height;
	}

	
	public void setHeight(float height) {
		this.height = height;
	}
}
