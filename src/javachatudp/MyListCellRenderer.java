/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachatudp;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.SwingConstants;

/**
 *
 * @author Nguyen Hai Dang
 */
public class MyListCellRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = 1L;
    private String name;

    public MyListCellRenderer(String name) {
        setOpaque(true);
        this.name = name;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        String text = value.toString();
        
        Color foreground = Color.black;
        int alignment = SwingConstants.LEFT;
        String tmp[] = text.split(":");
        
        if (text.contains("Server:")) {
            foreground = Color.green;
        }
        else if (text.contains("----->")) {
            foreground = Color.red;
            alignment = SwingConstants.CENTER;
        }
        else if (!tmp[0].equals(this.name) && tmp.length>1) {
            foreground = Color.black;
        }
        else {
            foreground = Color.blue;
            alignment = SwingConstants.RIGHT;
        }

        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        c.setForeground(foreground);
        setHorizontalAlignment(alignment);
        return c;
    }
}
