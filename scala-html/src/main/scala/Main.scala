import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.web.HTMLEditor
import javafx.stage.Stage

object Main extends App {
  Application.launch(classOf[HTMLEditorSample], args: _*)
}


class HTMLEditorSample extends Application {
  val INITIAL_TEXT = 
    ("<html><body>Lorem ipsum dolor sit " 
      + "amet, consectetur adipiscing elit. Nam tortor felis, pulvinar " 
      + "in scelerisque cursus, pulvinar at ante. Nulla consequat" 
      + "congue lectus in sodales. Nullam eu est a felis ornare " 
      + "bibendum et nec tellus. Vivamus non metus tempus augue auctor " 
      + "ornare. Duis pulvinar justo ac purus adipiscing pulvinar. " 
      + "Integer congue faucibus dapibus. Integer id nisl ut elit " 
      + "aliquam sagittis gravida eu dolor. Etiam sit amet ipsum " 
      + "sem.</body></html>"
      )

  override def start(stage: Stage): Unit = {
    stage.setTitle("HTMLEditor Sample")
    stage.setWidth(650)
    stage.setHeight(300)

    val htmlEditor:HTMLEditor  = new HTMLEditor()
    htmlEditor.setHtmlText(INITIAL_TEXT)
    htmlEditor.setPrefHeight(245)
    val scene:Scene = new Scene(htmlEditor)
    stage.setScene(scene)
    stage.show()
  }

}
