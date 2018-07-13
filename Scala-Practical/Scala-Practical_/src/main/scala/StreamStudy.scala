trait StreamStudy[+A] {

  // 最初の値をオプション型で取り出す？
  def headOption: Option[A] = this match {
    case EmptyStream => None
    case Cons(h, t) => Some(h())
  }

  // 最後の値を StreamStudy型 で取り出す？
  def tail: StreamStudy[A] = this match {
    case EmptyStream => throw new NoSuchMethodError()
    case Cons(h,t) => t()
  }
}

// 空のストリーム
case object EmptyStream extends StreamStudy[Nothing]

// 引数 h は初期値
// 引数 t は再帰で得られる値
case class Cons[+A](h: () => A, t: () => StreamStudy[A]) extends StreamStudy[A]

object StreamStudy {

  // 引数 h に 初期値、引数 t に再帰的に StreamStudy型 の値を持ち、 StreamStudy 型を返す
  // val eights: StreamStudy[Int] = StreamStudy.cons(8, eights)
  // eights : StreamStudy[Int] = Cons(,)
  // 遅延評価は () => h のように表すことができる
//  def cons[A](h: => A, t: => StreamStudy[A]): StreamStudy[A] = Cons(() => h, () => t)

  def cons[A](h: => A, t: => StreamStudy[A]): StreamStudy[A] = {
    lazy val headResult = h
    lazy val tailResult = t
    Cons(() => headResult, () => tailResult)
  }


  def empty[A]: StreamStudy[A] = EmptyStream

}
