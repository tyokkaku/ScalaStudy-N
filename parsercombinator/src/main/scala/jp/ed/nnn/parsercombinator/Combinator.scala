package jp.ed.nnn.parsercombinator

abstract class Combinator {

  sealed trait ParseResult[+T]

  case class Success[+T](value: T, next: String) extends ParseResult[T]

  case object Failure extends ParseResult[Nothing]

  type Parser[+T] = String => ParseResult[T]

  def string(literal: String): Parser[String] = input => {
    if (input.startsWith(literal)) {
      Success(literal, input.substring(literal.length))
    } else {
      Failure
    }
  }

  /**
    * string parser
    *
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



  def rep[T](parser: => Parser[T]): Parser[List[T]] = input => {

    def repeatRec(input: String): (List[T], String) = parser(input) match {
      case Success(value, next1) => val (result, next2) = repeatRec(next1)
        (value :: result, next2)
      case Failure => (Nil, input)
    }

    val (result, next) = repeatRec(input)
    Success(result, next)
  }

  def rep1sep[T](parser: => Parser[T], sep: Parser[String]): Parser[List[T]] =
    parser ~ rep(sep ~> parser) ^^ { t => t._1 :: t._2 }

  def success[T](value: T): Parser[T] = input => Success(value, input)

  def repsep[T](parser: => Parser[T], sep: Parser[String]): Parser[List[T]] =
    rep1sep(parser, sep) | success(List())

  val floatingPointNumber: Parser[String] = input => {
    val r = """^(-?\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?[fFdD]?""".r
    val matchIterator = r.findAllIn(input).matchData
    if (matchIterator.hasNext) {
      val next = matchIterator.next()
      val all = next.group(0)
      val target = next.group(0)
      Success(target, input.substring(all.length))
    } else {
      Failure
    }
  }

  val stringLiteral: Parser[String] = input => {
    val r = ("^\"(" +"""([^"\p{Cntrl}\\]|\\[\\'"bfnrt]|\\u[a-fA-F0-9]{4})*+""" + ")\"").r
    val matchIterator = r.findAllIn(input).matchData
    if(matchIterator.hasNext) {
      val next = matchIterator.next()
      val all = next.group(0)
      val target = next.group(1)
      Success(target, input.substring(all.length))
    } else {
      Failure
    }
  }


  implicit class RichParser[T](val parser: Parser[T]) {

    /**
      * select
      *
      * @param right 選択を行うパーサー
      * @return
      */
    def |[U >: T](right: => Parser[U]): Parser[U] = input => {
      parser(input) match {
        case success@Success(_, _) => success
        case Failure => right(input)
      }
    }

    /**
      * combine
      *
      * @param right 逐次合成を行うパーサー
      * @tparam U パーサーの結果
      * @return
      */
    def ~[U](right: => Parser[U]): Parser[(T, U)] = input => {
      parser(input) match {
        case Success(value1, next1) =>
          right(next1) match {
            case Success(value2, next2) =>
              Success((value1, value2), next2)
            case Failure =>
              Failure
          }
        case Failure =>
          Failure
      }
    }

    /**
      * use left
      *
      * @param right 右側のパーサー
      * @return パーサーの結果の型
      */
    def <~(right: => Parser[Any]): Parser[T] = input => {
      parser(input) match {
        case Success(value1, next1) =>
          right(next1) match {
            case Success(value2, next2) =>
              Success(value1, next2)
            case Failure =>
              Failure
          }
        case Failure =>
          Failure
      }
    }

    /**
      * use right
      *
      * @param right 右側のパーサー
      * @tparam U パーサーの結果の型
      * @return
      */
    def ~>[U](right: => Parser[U]): Parser[U] = input => {
      parser(input) match {
        case Success(value1, next1) =>
          right(next1) match {
            case Success(value2, next2) =>
              Success(value2, next2)
            case Failure =>
              Failure
          }
        case Failure =>
          Failure
      }
    }

    /**
      * map
      *
      * @param function 適用する関数
      * @tparam U パーサーの結果の型
      * @return
      */
    def ^^[U](function: T => U): Parser[U] = input => {
      parser(input) match {
        case Success(value, next) => Success(function(value), next)
        case Failure => Failure
      }
    }

  }

}
