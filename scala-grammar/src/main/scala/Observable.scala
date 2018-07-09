
// 変化を監視するオブジェクト
  // イベントを起こす側
trait Listener {
  def changed(newValue: Int): Unit
}

// 変化する側のオブジェクト。監視対象のオブジェクト。オブザーバブル。イベントを待ち受ける側。
  // インスタンス化する際に、リスナーを追加し、メソッドをオーバーライドする
object Observable {
  private var num = 0
  // オブザーバブルは、リスナー(監視者)を持っている
  private var listeners = Seq[Listener]()

  def increment(): Unit = {
    num = num + 1
    // 変化があると、変化があったことを、保有するすべてのリスナーに対して通知する
    listeners.foreach(l => l.changed(num))
  }

  // リスナーを追加する
  def addListener(listener: Listener) = listeners = listeners :+ listener
}
