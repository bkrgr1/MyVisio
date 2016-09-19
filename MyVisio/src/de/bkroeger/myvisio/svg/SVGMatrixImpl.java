package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.svg.SVGMatrix;

public class SVGMatrixImpl implements SVGMatrix, Serializable {
	
	private static final long serialVersionUID = -3236294165287440176L;
	
	private float a;
	private float b;
	private float c;
	private float d;
	private float e;
	private float f;

	public SVGMatrixImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public SVGMatrixImpl(Float[] floats) {
		a = floats[0];
		b = floats[1];
		c = floats[2];
		d = floats[3];
		e = floats[4];
		f = floats[5];
	}
	
	public SVGMatrixImpl(float a, float b, float c, float d, float e, float f) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}
	
	
	public Object clone() {
		return new SVGMatrixImpl(a, b, c, d, e, f);
	}

	
	public float getA() {
		return this.a;
	}

	
	public void setA(float a) {
		this.a = a;
	}

	
	public float getB() {
		return this.b;
	}

	
	public void setB(float b) {
		this.b = b;
	}

	
	public float getC() {
		return c;
	}

	
	public void setC(float c) {
		this.c = c;
	}

	
	public float getD() {
		return d;
	}

	
	public void setD(float d) {
		this.d = d;
	}

	
	public float getE() {
		return e;
	}

	
	public void setE(float e) {
		this.e = e;
	}

	
	public float getF() {
		return this.f;
	}

	
	public void setF(float f) {
		this.f = f;
	}

	
	public SVGMatrix multiply(SVGMatrix secondMatrix) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix inverse() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix translate(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix scale(float scaleFactor) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix scaleNonUniform(float scaleFactorX, float scaleFactorY) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix rotate(float angle) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix rotateFromVector(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix flipX() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix flipY() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix skewX(float angle) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix skewY(float angle) {
		// TODO Auto-generated method stub
		return null;
	}

}
