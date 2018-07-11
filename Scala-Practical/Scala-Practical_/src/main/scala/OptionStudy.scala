object OptionStudy {

  trait Option[+A] {

    // this をパターンマッチすることで、型パラメータを有効に使うことができる
    def map[B](f: A => B): Option[B] = this match {
      case None => None
        // getフィールドを束縛して、a に f を適用したものを Option[B] として返す
      case Some(a) => Some(f(a))
    }

    def getOrElse[B >: A](default: => B): B = this match {
      case None => default
      case Some(a) => a
    }

    // getOrElse None の意味が分からない
    def flatMap[B](f: A => Option[B]): Option[B] = map(f) getOrElse None
  }

  // Some ケースクラスは、getフィールドに値を保持する
  case class Some[+A](get: A) extends Option[A]

  case object None extends Option[Nothing]
}
