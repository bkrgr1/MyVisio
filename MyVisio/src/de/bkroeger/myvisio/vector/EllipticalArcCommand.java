package de.bkroeger.myvisio.vector;

public class EllipticalArcCommand extends BaseVectorCommand {

	/**
	 * <p>
	 * Draws an elliptical arc from the current point to (x, y). 
	 * The size and orientation of the ellipse are defined by two radii (rx, ry) 
	 * and an x-axis-rotation, which indicates how the ellipse as a whole is rotated 
	 * relative to the current coordinate system. The center (cx, cy) of the ellipse 
	 * is calculated automatically to satisfy the constraints imposed by the other parameters. 
	 * large-arc-flag and sweep-flag contribute to the automatic calculations and help determine how the arc is drawn.
	 * </p>
	 * @param command
	 * @param rx
	 * @param ry
	 * @param x_axis_rotation
	 * @param large_arc_flag
	 * @param sweep_flag
	 * @param x
	 * @param y
	 */
	public EllipticalArcCommand(String command, int rx, int ry, 
			int x_axis_rotation, int large_arc_flag, int sweep_flag, int x, int y) {
		super(command, x, y);
	}

}
