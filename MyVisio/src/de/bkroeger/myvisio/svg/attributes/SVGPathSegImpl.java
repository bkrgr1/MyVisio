package de.bkroeger.myvisio.svg.attributes;

import java.awt.Graphics2D;
import java.awt.geom.Path2D.Float;
import java.awt.geom.Point2D;
import java.io.Serializable;

import org.w3c.dom.svg.SVGBasicShapeElement;
import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGStructuralElement;

import de.bkroeger.myvisio.svg.elements.SVGElementImpl;

/**
 * @author bk
 */
public class SVGPathSegImpl extends SVGElementImpl implements SVGPathSeg, Serializable {

	private static final long serialVersionUID = 1124449439816136129L;
	
	protected float x; 
	protected float y;

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public float getY() {
		return this.y;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_UNKNOWN;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "?";
	}

	
	protected Point2D.Float convertCoordinate(Point2D.Float p1) {
		
		Point2D.Float p = p1;
//		if (this.transform != null && this.transform.getAnimVal() != null) {
//			SVGTransformList transformList = this.transform.getAnimVal();
//			for (long i=transformList.getNumberOfItems()-1; i>=0; i--) {
//				SVGTransformImpl transformImpl = (SVGTransformImpl) transformList.getItem(i);
//				p = transformImpl.transform(p);
//			}
//		}
		
		SVGElementImpl parentNode = (SVGElementImpl) this.getParent();
		if (parentNode != null) {
			SVGStructuralElement parent = (SVGStructuralElement) parentNode;
			return parent.convertCoordinate(p);
		} else {
			return p;
		}
	}

	@Override
	public Float paint(Float path2d, SVGBasicShapeElement shape, 
			Graphics2D g2d, SVGPathSeg prevSeg) {
		// TODO Auto-generated method stub
		return null;
	}
}
