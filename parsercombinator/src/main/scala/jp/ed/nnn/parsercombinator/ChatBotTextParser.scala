package jp.ed.nnn.parsercombinator

import scala.util.parsing.combinator._

object ChatBotTextParser extends JavaTokenParsers {

  def chatBot: Parser[ChatBot] =  "(" ~ "chatbot" ~ commandList ~ ")" ^^ {t => ChatBot(t._1._2)}

  def commandList: Parser[List[Command]] = rep(command)

  def command: Parser[Command] = replyCommand

  def replyCommand: Parser[ReplyCommand] =
    "(" ~ "reply" ~ string ~ replyList ~ ")" ^^ {t => ReplyCommand(t._1._1._2.r, t._1._2) }

  def replyList: Parser[List[String]] = "(" ~ rep(string) ~ ")" ^^ { t => t._1._2}

  def string: Parser[String] =  stringLiteral ^^ { s => s.substring(1, s.length - 1) }

  def apply(input: String): ParseResult[ChatBot] = parseAll(chatBot, input)

}
