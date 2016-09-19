package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegArcAbs;

/**
 * @author bk
 */
public class SVGPathSegArcAbsImpl extends SVGPathSegImpl implements
		SVGPathSegArcAbs {

	private static final long serialVersionUID = -6722776032077341870L;
	
	private float r1;
	private float r2;
	private float angle;
	private boolean sweepFlag;
	private boolean largeArcFlag;

	/**
	 * @param x2
	 * @param y2
	 * @param r12
	 * @param r22
	 * @param angle2
	 * @param largeArcFlag2
	 * @param sweepFlag2
	 */
	public SVGPathSegArcAbsImpl(float x2, float y2, float r12, float r22,
			float angle2, boolean largeArcFlag2, boolean sweepFlag2) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_ARC_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "A";
	}

	@Override
	public float getR1() {
		return r1;
	}

	@Override
	public void setR1(float r1) {
		this.r1 = r1;
	}

	@Override
	public float getR2() {
		return r2;
	}

	@Override
	public void setR2(float r2) {
		this.r2 = r2;
	}

	@Override
	public float getAngle() {
		return angle;
	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;
	}

	@Override
	public boolean getLargeArcFlag() {
		return largeArcFlag;
	}

	@Override
	public void setLargeArcFlag(boolean largeArcFlag) {
		this.largeArcFlag = largeArcFlag;
	}

	@Override
	public boolean getSweepFlag() {
		return sweepFlag;
	}

	@Override
	public void setSweepFlag(boolean sweepFlag) {
		this.sweepFlag = sweepFlag;
	}
}
