package de.bkroeger.myvisio.svg.attributes;

import java.io.Serializable;

import de.bkroeger.myvisio.svg.SVGRGBImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * A <color> is either a keyword (see Recognized color keyword names) or a numerical RGB specification.
 * In addition to these color keywords, users may specify keywords that correspond to the colors used by objects
 * in the user's environment. The normative definition of these keywords is found in User preferences for colors
 * ([CSS2], section 18.2).
 * </p><p>
 * The format of an RGB value in hexadecimal notation is a "#" immediately followed by either three or six
 * hexadecimal characters. The three-digit RGB notation (#rgb) is converted into six-digit form (#rrggbb) by replicating
 * digits, not by adding zeros. For example, #fb0 expands to #ffbb00. This ensures that white (#ffffff) can be specified
 * with the short notation (#fff) and removes any dependencies on the color depth of the display. The format
 * of an RGB value in the functional notation is an RGB start-function followed by a comma-separated list of three
 * numerical values (either three integer values or three percentage values) followed by ")". An RGB start-function is
 * the case-insensitive string "rgb(", for example "RGB(" or "rGb(". For compatibility, the all-lowercase form "rgb(" is
 * preferred. The integer value 255 corresponds to 100%, and to F or FF in the hexadecimal notation: rgb(255,255,255)
 * = rgb(100%,100%,100%) = #FFF. White space characters are allowed around the numerical values. All RGB colors
 * are specified in the sRGB color space [SRGB]. Using sRGB provides an unambiguous and objectively measurable
 * definition of the color, which can be related to international standards (see [COLORIMETRY]).
 * </p>
 * @author bk
 */
public class SVGColorAttributeImpl implements Serializable {

	private static final long serialVersionUID = -7709138246864599993L;
	
	private SVGRGBImpl rgb;
	public SVGRGBImpl getRGB() { return rgb; }
	
	public SVGColorAttributeImpl() {
		
	}
	
	public Object clone() {
		
		SVGColorAttributeImpl clone = new SVGColorAttributeImpl();
		clone.rgb = rgb;
		return clone;
	}

	/**
	 * <p>
	 * Analysiert eine Color-Referenz.
	 * </p>
	 * @param trim
	 * @return
	 * @throws TechnicalException 
	 */
	public void parseColor(String value) throws TechnicalException {
		
		if (value.startsWith("#") || value.startsWith("rgb(")) {
			parseRGB(value);
		} else {
			parseKeyword(value);
		}
	}
	
	/**
	 * <pre>
	 * color ::= "#" hexdigit hexdigit hexdigit (hexdigit hexdigit hexdigit)?
	 * 		| "rgb(" wsp* integer comma integer comma integer wsp* ")"
	 * 		| "rgb(" wsp* integer "%" comma integer "%" comma integer "%" wsp* ")"
	 * 		| color-keyword
	 * hexdigit ::= [0-9A-Fa-f]
	 * comma ::= wsp* "," wsp*
     * </pre>
	 * @param value
	 * @throws TechnicalException 
	 */
	private void parseRGB(String value) throws TechnicalException {
		
		if (value.startsWith("#")) {
			String hexR = "00";
			String hexG = "00";
			String hexB = "00";
			if (value.length() == 4) {
				hexR = value.substring(1,2)+value.substring(1,2);
				hexG = value.substring(2,3)+value.substring(2,3);
				hexB = value.substring(3,4)+value.substring(3,4);
			} else if (value.length() == 7) {
				hexR = value.substring(1,3);
				hexG = value.substring(3,5);
				hexB = value.substring(5,7);
			} else {
				throw new TechnicalException("Invalid hex RGB string: "+value);
			}
			this.rgb = new SVGRGBImpl(
				Integer.parseInt(hexR, 16),
				Integer.parseInt(hexG, 16),
				Integer.parseInt(hexB, 16));
		} else {
			
		}
	}
	
	/**
	 * Die definierten Keywords durchsuchen.
	 * 
	 * @param value
	 * @throws TechnicalException 
	 */
	private void parseKeyword(String value) throws TechnicalException {
		
		for (ColorKeywordNames keyword : ColorKeywordNames.values()) {
			if (keyword.toString().equalsIgnoreCase(value)) {
				this.rgb = keyword.getRGB();
				return;
			}
		}
		throw new TechnicalException("Invalid color keyword: "+value);
	}
	
	public String toXmlString() {
		if (this.rgb != null)
			return String.format("#%s%s%s", 
				this.rgb.getHexR(), this.rgb.getHexG(), this.rgb.getHexB());
		else
			return "";
	}

	/**
	 * Enumeration der Color-Keynames.
	 * 
	 * @author bk
	 */
	public static enum ColorKeywordNames {
		aliceblue(new SVGRGBImpl(240, 248, 255)),
		antiquewhite(new SVGRGBImpl(250, 235, 215)),
		aqua(new SVGRGBImpl( 0, 255, 255)),
		aquamarine(new SVGRGBImpl(127, 255, 212)),
		azure(new SVGRGBImpl(240, 255, 255)),
		beige(new SVGRGBImpl(245, 245, 220)),
		bisque(new SVGRGBImpl(255, 228, 196)),
		black(new SVGRGBImpl( 0, 0, 0)),
		blanchedalmond(new SVGRGBImpl(255, 235, 205)),
		blue(new SVGRGBImpl( 0, 0, 255)),
		blueviolet(new SVGRGBImpl(138, 43, 226)),
		brown(new SVGRGBImpl(165, 42, 42)),
		burlywood(new SVGRGBImpl(222, 184, 135)),
		cadetblue(new SVGRGBImpl( 95, 158, 160)),
		chartreuse(new SVGRGBImpl(127, 255, 0)),
		chocolate(new SVGRGBImpl(210, 105, 30)),
		coral(new SVGRGBImpl(255, 127, 80)),
		cornflowerblue(new SVGRGBImpl(100, 149, 237)),
		cornsilk(new SVGRGBImpl(255, 248, 220)),
		crimson(new SVGRGBImpl(220, 20, 60)),
		cyan(new SVGRGBImpl( 0, 255, 255)),
		darkblue(new SVGRGBImpl( 0, 0, 139)),
		darkcyan(new SVGRGBImpl( 0, 139, 139)),
		darkgoldenrod(new SVGRGBImpl(184, 134, 11)),
		darkgrey(new SVGRGBImpl(169, 169, 169)),
		darkgreen(new SVGRGBImpl( 0, 100, 0)),
		darkgray(new SVGRGBImpl(169, 169, 169)),
		darkkhaki(new SVGRGBImpl(189, 183, 107)),
		darkmagenta(new SVGRGBImpl(139, 0, 139)),
		darkolivegreen(new SVGRGBImpl( 85, 107, 47)),
		darkorange(new SVGRGBImpl(255, 140, 0)),
		darkorchid(new SVGRGBImpl(153, 50, 204)),
		darkred(new SVGRGBImpl(139, 0, 0)),
		darksalmon(new SVGRGBImpl(233, 150, 122)),
		darkseagreen(new SVGRGBImpl(143, 188, 143)),
		darkslateblue(new SVGRGBImpl( 72, 61, 139)),
		lightpink(new SVGRGBImpl(255, 182, 193)),
		lightsalmon(new SVGRGBImpl(255, 160, 122)),
		lightseagreen(new SVGRGBImpl( 32, 178, 170)),
		lightskyblue(new SVGRGBImpl(135, 206, 250)),
		lightslategrey(new SVGRGBImpl(119, 136, 153)),
		lightslategray(new SVGRGBImpl(119, 136, 153)),
		lightsteelblue(new SVGRGBImpl(176, 196, 222)),
		lightyellow(new SVGRGBImpl(255, 255, 224)),
		lime(new SVGRGBImpl( 0, 255, 0)),
		limegreen(new SVGRGBImpl( 50, 205, 50)),
		linen(new SVGRGBImpl(250, 240, 230)),
		magenta(new SVGRGBImpl(255, 0, 255)),
		maroon(new SVGRGBImpl(128, 0, 0)),
		mediumaquamarine(new SVGRGBImpl(102, 205, 170)),
		mediumblue(new SVGRGBImpl( 0, 0, 205)),
		mediumorchid(new SVGRGBImpl(186, 85, 211)),
		mediumpurple(new SVGRGBImpl(147, 112, 219)),
		mediumseagreen(new SVGRGBImpl( 60, 179, 113)),
		mediumslateblue(new SVGRGBImpl(123, 104, 238)),
		mediumspringgreen(new SVGRGBImpl( 0, 250, 154)),
		mediumturquoise(new SVGRGBImpl( 72, 209, 204)),
		mediumvioletred(new SVGRGBImpl(199, 21, 133)),
		midnightblue(new SVGRGBImpl( 25, 25, 112)),
		mintcream(new SVGRGBImpl(245, 255, 250)),
		mistyrose(new SVGRGBImpl(255, 228, 225)),
		moccasin(new SVGRGBImpl(255, 228, 181)),
		navajowhite(new SVGRGBImpl(255, 222, 173)),
		navy(new SVGRGBImpl( 0, 0, 128)),
		oldlace(new SVGRGBImpl(253, 245, 230)),
		olive(new SVGRGBImpl(128, 128, 0)),
		olivedrab(new SVGRGBImpl(107, 142, 35)),
		orange(new SVGRGBImpl(255, 165, 0)),
		orangered(new SVGRGBImpl(255, 69, 0)),
		orchid(new SVGRGBImpl(218, 112, 214)),
		palegoldenrod(new SVGRGBImpl(238, 232, 170)),
		palegreen(new SVGRGBImpl(152, 251, 152)),
		darkslategray(new SVGRGBImpl( 47, 79, 79)),
		darkslategrey(new SVGRGBImpl( 47, 79, 79)),
		darkturquoise(new SVGRGBImpl( 0, 206, 209)),
		darkviolet(new SVGRGBImpl(148, 0, 211)),
		deeppink(new SVGRGBImpl(255, 20, 147)),
		deepskyblue(new SVGRGBImpl( 0, 191, 255)),
		dimgray(new SVGRGBImpl(105, 105, 105)),
		dimgrey(new SVGRGBImpl(105, 105, 105)),
		dodgerblue(new SVGRGBImpl( 30, 144, 255)),
		firebrick(new SVGRGBImpl(178, 34, 34)),
		floralwhite(new SVGRGBImpl(255, 250, 240)),
		forestgreen(new SVGRGBImpl( 34, 139, 34)),
		fuchsia(new SVGRGBImpl(255, 0, 255)),
		gainsboro(new SVGRGBImpl(220, 220, 220)),
		ghostwhite(new SVGRGBImpl(248, 248, 255)),
		gold(new SVGRGBImpl(255, 215, 0)),
		goldenrod(new SVGRGBImpl(218, 165, 32)),
		gray(new SVGRGBImpl(128, 128, 128)),
		grey(new SVGRGBImpl(128, 128, 128)),
		green(new SVGRGBImpl( 0, 128, 0)),
		greenyellow(new SVGRGBImpl(173, 255, 47)),
		honeydew(new SVGRGBImpl(240, 255, 240)),
		hotpink(new SVGRGBImpl(255, 105, 180)),
		indianred(new SVGRGBImpl(205, 92, 92)),
		indigo(new SVGRGBImpl( 75, 0, 130)),
		ivory(new SVGRGBImpl(255, 255, 240)),
		khaki(new SVGRGBImpl(240, 230, 140)),
		lavender(new SVGRGBImpl(230, 230, 250)),
		lavenderblush(new SVGRGBImpl(255, 240, 245)),
		lawngreen(new SVGRGBImpl(124, 252, 0)),
		lemonchiffon(new SVGRGBImpl(255, 250, 205)),
		lightblue(new SVGRGBImpl(173, 216, 230)),
		lightcoral(new SVGRGBImpl(240, 128, 128)),
		lightcyan(new SVGRGBImpl(224, 255, 255)),
		lightgoldenrodyellow(new SVGRGBImpl(250, 250, 210)),
		lightgray(new SVGRGBImpl(211, 211, 211)),
		paleturquoise(new SVGRGBImpl(175, 238, 238)),
		palevioletred(new SVGRGBImpl(219, 112, 147)),
		papayawhip(new SVGRGBImpl(255, 239, 213)),
		peachpuff(new SVGRGBImpl(255, 218, 185)),
		peru(new SVGRGBImpl(205, 133, 63)),
		pink(new SVGRGBImpl(255, 192, 203)),
		plum(new SVGRGBImpl(221, 160, 221)),
		powderblue(new SVGRGBImpl(176, 224, 230)),
		purple(new SVGRGBImpl(128, 0, 128)),
		red(new SVGRGBImpl(255, 0, 0)),
		rosybrown(new SVGRGBImpl(188, 143, 143)),
		royalblue(new SVGRGBImpl( 65, 105, 225)),
		saddlebrown(new SVGRGBImpl(139, 69, 19)),
		salmon(new SVGRGBImpl(250, 128, 114)),
		sandybrown(new SVGRGBImpl(244, 164, 96)),
		seagreen(new SVGRGBImpl( 46, 139, 87)),
		seashell(new SVGRGBImpl(255, 245, 238)),
		sienna(new SVGRGBImpl(160, 82, 45)),
		silver(new SVGRGBImpl(192, 192, 192)),
		skyblue(new SVGRGBImpl(135, 206, 235)),
		slateblue(new SVGRGBImpl(106, 90, 205)),
		slategray(new SVGRGBImpl(112, 128, 144)),
		slategrey(new SVGRGBImpl(112, 128, 144)),
		snow(new SVGRGBImpl(255, 250, 250)),
		springgreen(new SVGRGBImpl( 0, 255, 127)),
		steelblue(new SVGRGBImpl( 70, 130, 180)),
		tan(new SVGRGBImpl(210, 180, 140)),
		teal(new SVGRGBImpl( 0, 128, 128)),
		thistle(new SVGRGBImpl(216, 191, 216)),
		tomato(new SVGRGBImpl(255, 99, 71)),
		turquoise(new SVGRGBImpl( 64, 224, 208)),
		violet(new SVGRGBImpl(238, 130, 238)),
		wheat(new SVGRGBImpl(245, 222, 179)),
		white(new SVGRGBImpl(255, 255, 255)),
		whitesmoke(new SVGRGBImpl(245, 245, 245)),
		yellow(new SVGRGBImpl(255, 255, 0)),
		lightgreen(new SVGRGBImpl(144, 238, 144)),
		lightgrey(new SVGRGBImpl(211, 211, 211)),
		yellowgreen(new SVGRGBImpl(154, 205, 50));
		
		
		private SVGRGBImpl colorRGB;
		
		private ColorKeywordNames(SVGRGBImpl rgb) {
			this.colorRGB = rgb;
		}
		
		public SVGRGBImpl getRGB() {
			return colorRGB;
		}
	}
}
