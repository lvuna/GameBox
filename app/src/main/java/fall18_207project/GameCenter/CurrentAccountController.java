package fall18_207project.GameCenter;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/***
 * Singleton controller for Current Account.
 */
public class CurrentAccountController implements Serializable {

    private static CurrentAccountController instance = new CurrentAccountController();
    private static AccountManager accountManager = new AccountManager();
    private static String userEmail;

    public static CurrentAccountController getInstance() {
        return instance;
    }

    static AccountManager getAccountManager() {
        return accountManager;
    }

    static Account getCurrAccount() {
        if (accountManager.getAccount(userEmail) == null) {
            accountManager.addAccount(new Account("null", "null", "null"));
        }
        return accountManager.getAccount(userEmail);
    }

    private static void initUserEmail(String email) {
        userEmail = email;
    }

    static void readSavedData(Context context, String userEmail) {
        initUserEmail(userEmail);
        try {
            InputStream inputStream = context.openFileInput("account_manager.ser");
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                accountManager = (AccountManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("CurrentAccountController", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("CurrentAccountController", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("CurrentAccountController", "File contained unexpected data type: " + e.toString());
        }
    }

    static void writeData(Context context) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("account_manager.ser", Context.MODE_PRIVATE));
            outputStream.writeObject(accountManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
