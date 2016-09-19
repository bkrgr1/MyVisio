package de.bkroeger.myvisio.svg.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;

import org.w3c.dom.svg.SVGBasicShapeElement;
import org.w3c.dom.svg.SVGLength;

import de.bkroeger.myvisio.svg.SVGRGBImpl;
import de.bkroeger.myvisio.svg.attributes.SVGColorAttributeImpl;
import de.bkroeger.myvisio.svg.attributes.SVGFillAttributeImpl.FillAttributeType;
import de.bkroeger.myvisio.svg.attributes.SVGStrokeLinecapAttributeImpl.StrokeLinecapType;
import de.bkroeger.myvisio.svg.attributes.SVGStrokeLinejoinAttributeImpl.StrokeLinejoinType;

/**
 * <p>
 * Standard shapes which are predefined in SVG as a convenience for common graphical operations.
 * Specifically: ‘circle’, ‘ellipse’, ‘line’, ‘polygon’, ‘polyline’ and ‘rect’.
 * </p>
 * @author bk
 */
public class SVGBasicShapeElementImpl extends SVGShapeElementImpl implements SVGBasicShapeElement {
	
	private static final long serialVersionUID = 5746273746625987933L;
	
	private static final Logger logger = Logger.getLogger(SVGBasicShapeElementImpl.class.getName());

	public SVGBasicShapeElementImpl() {
		super();
	}
	
	
	public Object clone() {
		return null;
	}

	
	public void paint(Graphics2D g2d) {
		
		for (int i=0; i<this.getChildCount(); i++) {
			SVGElementImpl childNode = (SVGElementImpl)this.getChildAt(i);
			SVGBasicShapeElement shape = (SVGBasicShapeElement)childNode;
			shape.paint(g2d);
		}
	}
	
	protected void drawAsSelected(Graphics2D g2d, Shape shape2d) {
		
		Rectangle2D boundingRectangle = shape2d.getBounds2D();
		Color selectedColor = Color.lightGray;
		Paint prevPaint = g2d.getPaint();
		g2d.setPaint(selectedColor);
		g2d.fill(boundingRectangle);
		g2d.setPaint(prevPaint);
	}

	/**
	 * Ermittelt die Stroke- und Fill-Attribute und wendet sie auf die Graphics-Umgebung an.
	 * 
	 * @param g2d
	 * @param shape2d
	 */
	public void drawFillAndStroke(Graphics2D g2d, Shape shape2d) {
		
		Stroke prevStroke = g2d.getStroke();
		Paint  prevPaint  = g2d.getPaint();
		
		BasicStroke basicStroke = null;
		Color strokeColor = null;
		Color fillColor = null;
		
		// übergeordnetes SVG-Element suchen
		SVGSVGElementImpl svg = (SVGSVGElementImpl)getOwnerSVGElement();
		
		// ist ein Stroke-Attribute definiert?
		if (this.stroke.getStrokeType() != FillAttributeType.NONE) {
			
			strokeColor = getStrokeColor(svg);
			
			basicStroke = getStroking(g2d);
		}

		// ist eine Füllfarbe definiert?
		if (this.fill.getFillType() != FillAttributeType.NONE) {
			
//			SVGColorAttributeImpl prevSvgColorAttr = svg.getCurrentColor();
			
			fillColor = getFillColor(svg);
		}
			
		if (fillColor != null
		&&  strokeColor != null) {
			
			// Stroke-Farbe einstellen
			g2d.setPaint(strokeColor);
			// Rahmen zeichnen
			g2d.draw(basicStroke.createStrokedShape(shape2d));
			
			// Füll-Farbe einstellen
			g2d.setPaint(fillColor);
//			svg.setCurrentColor(svgColorAttr);
			// Inhalt füllen
			g2d.fill(shape2d);
			
		} else if (fillColor != null) {
			
				// Füll-Farbe einstellen
				g2d.setPaint(fillColor);
//				svg.setCurrentColor(svgColorAttr);
				// Shape mit der Füllfarbe zeigen
				g2d.fill(shape2d);
				
		} else if (strokeColor != null) {
			
			// Stroke-Farbe einstellen
			g2d.setPaint(strokeColor);
			// Rahmen zeichnen
			g2d.draw(basicStroke.createStrokedShape(shape2d));
			
		} else {
			
			// Shape ohne Füllung anzeigen
			g2d.draw(shape2d);
		}
					
		// vorherige Paint-Attribute wieder herstellen
		g2d.setPaint(prevPaint);
//		svg.setCurrentColor(prevSvgColorAttr);
		
		g2d.setStroke(prevStroke);
	}

	/**
	 * Ermittelt eine Stroke-Definition.
	 * 
	 * @param g2d
	 * @return
	 */
	protected BasicStroke getStroking(Graphics2D g2d) {
		
		BasicStroke basicStroke;
		float strokeWidth = this.strokeWidth.getWidth().getValueInSpecifiedUnits();
		
		StrokeLinecapType linecapType = this.strokeLinecap.getType();
		StrokeLinejoinType linejoinType = this.strokeLinejoin.getType();
		
		if (this.strokeDasharray.size() == 0) {
			
		    basicStroke =
			        new BasicStroke(strokeWidth,
		                linecapType.getLinecapValue(),
		                linejoinType.getLinejoinValue(),
		                4.0f); 	// TODO: mitter limit aus Attribut ermitteln

		} else {
		
			float[] dashArray = new float[this.strokeDasharray.size()];
			for (int i=0; i < this.strokeDasharray.size(); i++) {
				SVGLength l = this.strokeDasharray.getLengthList().getItem(i);
				dashArray[i] = l.getValue();
			}
			
		    basicStroke =
		        new BasicStroke(strokeWidth,
		            linecapType.getLinecapValue(),
		            linejoinType.getLinejoinValue(),
		            4.0f, 	// mitter limit
		            dashArray, 
		            0.0f);	// dash phase
		}
		
		if (basicStroke != null) {
		    g2d.setStroke(basicStroke);
		}
		return basicStroke;
	}
	
	/**
	 * Ermittelt die Füllfarbe.
	 * 
	 * @param svg
	 * @return
	 */
	protected Color getFillColor(SVGSVGElementImpl svg) {
		
		Color fillColor = null;
		SVGColorAttributeImpl svgColorAttr = null;
		if (this.fill.getFillType() == FillAttributeType.COLOR) {
			
			svgColorAttr = this.fill.getFillColor();
			SVGRGBImpl rgb = svgColorAttr.getRGB();
			fillColor = new Color(rgb.getR(), rgb.getG(), rgb.getB());
			
		} else if (this.fill.getFillType() == FillAttributeType.CURRENT_COLOR) {
			
			svgColorAttr = svg.getCurrentFillColor();
			
		} else if (this.fill.getFillType() == FillAttributeType.INHERIT) {
			
			// Hierarchie nach oben durchsuchen
			SVGElementImpl parentNode = (SVGElementImpl) this.getParent();
			if (parentNode != null) {
				do {
					parentNode = (SVGElementImpl) parentNode.getParent();
				} while (parentNode != null);
			} else {
//				svgColorAttr = 
			}
		} else if (this.fill.getFillType() == FillAttributeType.FUNCIRI) {
			
			// Functional IRI auswerten
			
		}
		return fillColor;
	}
	
	protected Color getStrokeColor(SVGSVGElementImpl svg) {
		
		Color strokeColor = null;
		SVGColorAttributeImpl svgColorAttr = null;
		if (this.stroke.getStrokeType() == FillAttributeType.COLOR) {
			
			svgColorAttr = this.stroke.getPaint();
			SVGRGBImpl rgb = svgColorAttr.getRGB();
			strokeColor = new Color(rgb.getR(), rgb.getG(), rgb.getB());
			
		} else if (this.stroke.getStrokeType() == FillAttributeType.CURRENT_COLOR) {
			
			svgColorAttr = svg.getCurrentStrokeColor();
			
		} else if (this.stroke.getStrokeType() == FillAttributeType.INHERIT) {
			
			// Hierarchie nach oben durchsuchen
			SVGElementImpl parentNode = (SVGElementImpl) this.getParent();
			if (parentNode != null) {
				do {
					parentNode = (SVGElementImpl) parentNode.getParent();
				} while (parentNode != null);
			} else {
//				svgColorAttr = 
			}
		} else if (this.stroke.getStrokeType() == FillAttributeType.FUNCIRI) {
			
			// Functional IRI auswerten
			
		}
		return strokeColor;
	}
	
	
	public boolean containsPoint(Point.Float p) {
		logger.info("BasicShapeElement contains point="+p.toString()+": false");
		return false;
	}
}
