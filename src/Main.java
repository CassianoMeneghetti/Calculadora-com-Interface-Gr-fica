import View.TelaCalculadora;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                TelaCalculadora::new
        );
    }
}