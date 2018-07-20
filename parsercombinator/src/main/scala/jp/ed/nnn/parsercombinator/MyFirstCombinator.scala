package jp.ed.nnn.parsercombinator

// パーサーコンビネーターのライブラリ
abstract class MyFirstCombinator {

  sealed trait ParseResult[+T]
  case class Success[+T](valuse: T, next: String) extends ParseResult[T]
  case object Failure extends ParseResult[Nothing]

  // 関数としてのパーサーという型の定義
  // 「String を ParseResult に変換するようなもの」を、Parser と名付けている
  type Parser[+T] = String => ParseResult[T]

  // 文字列を String型 としてパースするパーサー
  // 与えられた文字列が、設定した文字列と同じなら、successを返す
  def string(literal: String): Parser[String] = input => {
    if(input.startsWith(literal)) {
      Success(literal, input.substring(literal.length))
    } else {
      Failure
    }
  }

  /**
    * string parser
    * string を s で呼び出せる
    * @param literal 文字列
    * @return
    */
  def s(literal: String): Parser[String] = string(literal)

  // 指定した文字のいずれかをパースするパーサー
  def oneOf(chars: Seq[Char]): Parser[String] = input => {
    if(input.length != 0 &&
    chars.contains(input.head)) {
      Success(input.head.toString, input.tail)
    } else {
      Failure
    }
  }

  // パーサーコンビネーター
  // left || right で選択する。
  def select[T, U >: T](left: => Parser[T], right: => Parser[U]): Parser[U] = input => {
    left(input) match {
      case success@Success(_, _) => success
      case Failure => right(input)
    }
  }

  // 逐次合成
  // left と right を1つずつ適用して、両方成功時に、両方の結果を返す。どちらかが失敗すれば、Failure
  def combine[T,U](left: Parser[T], right: Parser[U]): Parser[(T,U)] = input => {
    left(input) match {
      case Success(value1, next1) =>
        right(next1) match {
          case Success(value2, next2) =>
            Success((value1, value2), next2)
          case Failure => Failure
        }
      case Failure => Failure
    }
  }

//   繰り返しのパーサーコンビネーター
  def rep[T](parser: Parser[T]): Parser[List[T]] = input => {
    def repeatRec(input: String): (List[T], String) = parser(input) match {
      case Success(value, next1) =>
        val (result, next2) = repeatRec(next1); (value::result, next2)
      case Failure => (Nil, input)
    }
    val (result, next) = repeatRec(input)
    Success(result, next)
  }


  // 値の型の変更をする
  // String を Boolean に変換する
  def map[T, U](parser: Parser[T], function: T => U): Parser[U] = input => {
    parser(input) match {
      case Success(value, next) => Success(function(value), next)
      case Failure => Failure
    }
  }
}
