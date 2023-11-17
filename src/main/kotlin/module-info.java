module me.nathanfallet.connect4.connectfourjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    opens me.nathanfallet.connect4 to javafx.fxml;
    opens me.nathanfallet.connect4.controllers to javafx.fxml;
    opens me.nathanfallet.connect4.views to javafx.fxml;
    exports me.nathanfallet.connect4;
    exports me.nathanfallet.connect4.controllers;
    exports me.nathanfallet.connect4.views;
}
