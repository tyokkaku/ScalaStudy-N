package jp.ed.nnn.parsercombinator

// <postal-code> ::~ <zone-code> "-" <town-code>
  // <zone-code> ::= <digit><digit><digit>
  // <town-code> ::= <digit><digit><digit><digit>

case class PostalCode(zoneCode: String, townCode: String)

object PostalCodeParser extends MyFirstCombinator {

  def digit: Parser[String] = oneOf('0' to '9')

  def zoneCode: Parser[String] = map(combine(combine(digit, digit), digit), {
    t: ((String, String), String) => t._1._1 + t._1._2 + t._2
  })

  def townCode: Parser[String] = map(combine(combine(combine(digit, digit),digit)), digit), {
    t: (())
  }



}
