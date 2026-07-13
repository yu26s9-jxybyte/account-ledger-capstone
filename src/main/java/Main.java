import com.pluralsight.HomeScreen;
import com.pluralsight.data.TransactionFileReader;
import com.pluralsight.ui.Console;

public class Main {
    public static void Main(String[] args){
        Console console = new Console();
        TransactionFileReader transactionFileReader = new TransactionFileReader();
        HomeScreen homeScreen = new HomeScreen(console, transactionFileReader);
        homeScreen.mainDisplay();
    }
}
