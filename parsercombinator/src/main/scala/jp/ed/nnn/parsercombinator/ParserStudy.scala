package jp.ed.nnn.parsercombinator

// パーサー: 文字列を受け取り、なんらかのプログラム上の値に変換する関数のこと
object ParserStudy {

  // パースした結果
  sealed trait ParseResult[+T]
  case class Success[+T](value: T, next: String) extends ParseResult[T]
  case object Failure extends ParseResult[Nothing]

  // 「String を ParseResult に変換するようなもの」を、Parser と名付ける
  type Parser[+T] = String => ParseResult[T]

  // パーサーの具体的な機能
  // input されたものが true か判別して、ParseResult を返す
  def trueParser: Parser[Boolean] = input =>
    if (input.startsWith("true")) {
      Success(true, input.substring("true".length))
    } else {
      Failure
    }

  def falseParser: Parser[Boolean] = input =>
    if (input.startsWith("false")) {
      Success(true, input.substring("false".length))
    } else {
      Failure
    }

  // パーサーコンビネーター
  def booleanParser: Parser[Boolean] = input =>
    trueParser(input) match {
      case success@Success(_,_) => success
      case Failure => falseParser(input)
    }


}
