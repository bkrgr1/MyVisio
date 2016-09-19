package de.bkroeger.myvisio.utility;

/**
 * <p>
 * Diese Hilfsklasse liefert das Ergebnis eines Dialogs zurück.
 * </p><p>
 * Wenn der Status Ok ist, muss auch ein "value" ungleich null vorhanden sein.
 * </p>
 * @author bk
 *
 */
public class DialogResult {
	
	private Object value = null;

	public boolean isOk() {
		return (value != null);
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return this.value;
	}
}
