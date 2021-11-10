/*
 * Created by Konstantin Chuyasov
 * Last modified: 10.11.2021, 19:06
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz;

import by.katz.gui.FormMain;
import com.formdev.flatlaf.FlatDarculaLaf;


public class Main {


    public static void main(String[] args) {
        Settings.getInstance().loadSettings();
        FlatDarculaLaf.setup();
        new FormMain();
    }
}
