package jp.ed.nnn.nightcoreplayer

import java.io.File

import javafx.application.Application
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control._
import javafx.scene.input.{DragEvent, MouseEvent, TransferMode}
import javafx.scene.layout.{BorderPane, HBox}
import javafx.scene.media.{Media, MediaPlayer, MediaView}
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.util.{Callback, Duration}

object Main extends App {
  Application.launch(classOf[Main], args: _*)
}

class Main extends Application {

  /**
    * サイズの定数
    */
  private[this] val mediaViewFitWidth = 800
  private[this] val mediaViewFitHeight = 450
  private[this] val toolBarMinHeight = 50
  private[this] val tableMinWidth = 300

  override def start(primaryStage: Stage): Unit = {

    val mediaView = new MediaView()

    /**
      * タイムラベル
      */
    val timeLabel = new Label()
    timeLabel.setText("00:00:00/00:00:00")
    timeLabel.setTextFill(Color.WHITE)
    val toolBar = new HBox(timeLabel)
    toolBar.setMinHeight(toolBarMinHeight)
    toolBar.setAlignment(Pos.CENTER)
    toolBar.setStyle("-fx-background-color: Black")

    val tableView = new TableView[Movie]()
    tableView.setMinWidth(tableMinWidth)

    /**
      * テーブル
      */
    // オブザーバーパターン
    // observableArrayList : オブザーバブルな配列。配列の内容が変化した際に、TableViewに変更を通知する。
    val movies = FXCollections.observableArrayList[Movie]()
    tableView.setItems(movies)

    // テーブルのムービーがクリックされたら、playMovie を実行する
    tableView.setRowFactory(new Callback[TableView[Movie], TableRow[Movie]]() {
      override def call(param: TableView[Movie]): TableRow[Movie] = {
        val row = new TableRow[Movie]()
        row.setOnMouseClicked(new EventHandler[MouseEvent] {
          override def handle(event: MouseEvent): Unit = {
            if (event.getClickCount >= 1 && !row.isEmpty) {
              playMovie(row.getItem, tableView, mediaView, timeLabel)
            }
          }
        })
        row
      }
    })

    // カラムの設定
    val fileNameColumn = new TableColumn[Movie, String]("ファイル名")
    fileNameColumn.setCellValueFactory(new PropertyValueFactory("fileName"))
    fileNameColumn.setPrefWidth(160)
    val timeColumn = new TableColumn[Movie, String]("時間")
    timeColumn.setCellValueFactory(new PropertyValueFactory("time"))
    timeColumn.setPrefWidth(80)
    val deleteActionColumn = new TableColumn[Movie, Long]("削除")
    deleteActionColumn.setCellValueFactory(new PropertyValueFactory("id"))
    deleteActionColumn.setPrefWidth(60)
    deleteActionColumn.setCellFactory(new Callback[TableColumn[Movie, Long], TableCell[Movie,Long]]() {
      override def call(param: TableColumn[Movie, Long]): TableCell[Movie, Long] = {
        new DeleteCell(movies, mediaView, tableView)
      }
    })

    tableView.getColumns.setAll(fileNameColumn, timeColumn, deleteActionColumn)

    /**
      * 全体のレイアウト
      */
    val baseBorderPane = new BorderPane()
    baseBorderPane.setBottom(toolBar)
    baseBorderPane.setRight(tableView)
    baseBorderPane.setStyle("-fx-background-color: Black")
    baseBorderPane.setCenter(mediaView)
    val scene = new Scene(baseBorderPane, mediaViewFitWidth + tableMinWidth, mediaViewFitHeight + toolBarMinHeight)
    scene.setFill(Color.BLACK)
    // オブザーバーパターン
    // bind メソッドは、値の変化すれば、自動的に値を追従する(ためのリスナーを追加する)
    mediaView.fitWidthProperty().bind(scene.widthProperty().subtract(tableMinWidth))
    mediaView.fitHeightProperty().bind(scene.heightProperty().subtract(toolBarMinHeight))

    /**
      * ドラッグ&ドロップでファイルをテーブルに追加する
      */
    // ドラッグされたとき
    scene.setOnDragOver(new EventHandler[DragEvent] {
      override def handle(event: DragEvent): Unit = {
        if (event.getGestureSource != scene &&
          event.getDragboard.hasFiles) {
          event.acceptTransferModes(TransferMode.COPY_OR_MOVE: _*)
        }
        event.consume()
      }
    })
    // ドロップしたとき
    scene.setOnDragDropped(new EventHandler[DragEvent] {
      override def handle(event: DragEvent): Unit = {
        val db = event.getDragboard
        if (db.hasFiles) {
          db.getFiles.toArray(Array[File]()).toSeq.foreach { f =>
            val filePath = f.getAbsolutePath
            val fileName = f.getName
            val media = new Media(f.toURI.toString)
            val player = new MediaPlayer(media)
            player.setOnReady(new Runnable {
              override def run(): Unit = {
                val time = formatTime(media.getDuration)
                val movie = Movie(System.currentTimeMillis(), fileName, time, filePath, media)
                while (movies.contains(movie)) {
                  movie.setId(movie.getId + 1L)
                }
                movies.add(movie)
                player.dispose()
              }
            })
          }
        }
        event.consume()
      }
    })


    primaryStage.setTitle("mp4ファイルをドラッグ&ドロップしてください")

    primaryStage.setScene(scene)
    primaryStage.show()
  }

  // 再生する
  private[this] def playMovie(movie: Movie, tableView: TableView[Movie], mediaView: MediaView, timeLabel: Label): Unit = {
    if (mediaView.getMediaPlayer != null) {
      val oldPlayer = mediaView.getMediaPlayer
      oldPlayer.stop()
      oldPlayer.dispose()
    }
    val mediaPlayer = new MediaPlayer(movie.media)

    /**
      * 現在時間の表示
      */
    mediaPlayer.currentTimeProperty().addListener(new ChangeListener[Duration] {
      override def changed(observable: ObservableValue[_ <: Duration], oldValue: Duration, newValue: Duration): Unit =
        timeLabel.setText(formatTime(mediaPlayer.getCurrentTime, mediaPlayer.getTotalDuration))
    })
    mediaPlayer.setOnReady(new Runnable {
      override def run(): Unit =
        timeLabel.setText(formatTime(mediaPlayer.getCurrentTime, mediaPlayer.getTotalDuration))
    })

    // 再生終了時のイベントハンドラを設定する
    mediaPlayer.setOnEndOfMedia(new Runnable {
      override def run(): Unit = playNext(tableView, mediaView, timeLabel)
    })

    mediaView.setMediaPlayer(mediaPlayer)
    mediaPlayer.setRate(3)
    mediaPlayer.play()
  }

  // 次を再生する
  private[this] def playNext(tableView: TableView[Movie], mediaView: MediaView, timeLabel: Label): Unit = {
    val selectionModel = tableView.getSelectionModel
    if(selectionModel.isEmpty) return
    val index = selectionModel.getSelectedIndex
    val nextIndex = (index + 1) % tableView.getItems.size()
    selectionModel.select(nextIndex)
    val movie = selectionModel.getSelectedItem
    playMovie(movie, tableView, mediaView, timeLabel)
  }


  /**
    * 現在時間のフォーマット
    *
    * @param elapsed
    * @return フォーマットされた現在時間
    */
  private[this] def formatTime(elapsed: Duration): String = {
    // String型の format メソッド。
    // 「%02d」で、桁数に満たない場合でも、「02 = 00」で埋める
    // 60の剰余で表すことで、60以上にはならない
    "%02d:%02d:%02d".format(
      elapsed.toHours.toInt,
      elapsed.toMinutes.toInt % 60,
      elapsed.toSeconds.toInt % 60
    )
  }

  private[this] def formatTime(elapsed: Duration, duration: Duration): String =
    s"${formatTime(elapsed)}/${formatTime(duration)}"
}

// 再生が終了したときのイベントを監視する
// 終了したらイベントを発生させる。
  // 次のムービーを取得する
  // 再生する
