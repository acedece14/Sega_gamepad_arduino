/*
 * Created by Konstantin Chuyasov
 * Last modified: 08.11.2021, 23:42
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.infrared;

public class IrKey {
    private final String keyCode;
    private final String name;
    private final String serverCommand;
    private final String serverMouseCommand;

    public IrKey(String keyCode, String keyName, String serverCommand) {
        this.keyCode = keyCode;
        this.name = keyName;
        this.serverCommand = serverCommand;
        this.serverMouseCommand = "";
    }

    public String getKeyCode() { return keyCode; }

    public String getName() { return name; }

    public String getServerCommand() { return serverCommand; }

    public String getServerMouseCommand() { return serverMouseCommand; }
}
