package by.katz.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TrayLeftClickListener implements MouseListener {

    private FormMain formMain;

    TrayLeftClickListener(FormMain formMain) { this.formMain = formMain; }

    @Override public void mouseClicked(MouseEvent e) { formMain.setVisible(!formMain.isVisible()); }

    @Override public void mousePressed(MouseEvent e) { }

    @Override public void mouseReleased(MouseEvent e) { }

    @Override public void mouseEntered(MouseEvent e) { }

    @Override public void mouseExited(MouseEvent e) { }
}
