package de.bkroeger.myvisio.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.bkroeger.myvisio.model.Category;

/**
 * <p>
 * Dieser ListCellRenderer wird für die Anzeige der Categories in einer Liste verwendet.
 * </p>
 * @author bk
 */
public class CategoryListCellRenderer extends JLabel implements ListCellRenderer<Category> {

	private static final long serialVersionUID = 5602780969747398095L;

	public Component getListCellRendererComponent(JList<? extends Category> list, 
			Category category, int index, boolean isSelected, boolean cellHasFocus) {
		
        setText(category.getName());

        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.WHITE;

        // check if this cell is selected
        } else if (isSelected) {
            background = Color.WHITE;
            foreground = Color.RED;

        // unselected, and not the DnD drop location
        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        };

        setBackground(background);
        setForeground(foreground);

        return this;
	}
}
