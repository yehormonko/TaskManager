import Controller.Controller;
import Model.Task;
import View.View;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        View view = new View(controller);
        view.menu();
    }
}
