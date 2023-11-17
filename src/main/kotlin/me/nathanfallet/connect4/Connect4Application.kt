package me.nathanfallet.connect4

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

fun main() = Application.launch(Connect4Application::class.java)

class Connect4Application : Application() {

    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(Connect4Application::class.java.getResource("controllers/main-controller.fxml"))
        val scene = Scene(fxmlLoader.load(), 672.0, 622.0)
        stage.title = "Puissance 4 !"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }

}
