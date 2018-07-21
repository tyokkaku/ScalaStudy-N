package jp.ed.nnn.parsercombinator

import java.time.{LocalDateTime, ZoneId}

import scala.util.Random
import scala.util.matching.Regex


case class ChatBot(commands: List[Command])

sealed trait Command {
  def exec(input: String): Boolean
}

case class ReplyCommand(regex: Regex, replays: List[String]) extends Command {
  override def exec(input: String): Boolean = {
    regex.findFirstIn(input: String) match {
      case Some(_) =>
        println(Random.shuffle(replays).head)
        true
      case None => false
    }
  }
}

case class TimeCommand(regex: Regex, start: Int, end: Int, zone: String, replays: List[String]) extends Command {
  override def exec(input: String): Boolean = {
    val now = LocalDateTime.now().atZone {
      ZoneId.of(zone)
    }
    val isInTime = start <= now.getHour && now.getHour <= end
    regex.findFirstIn(input) match {
      case Some(_) if isInTime =>
        println(s"${Random.shuffle(replays).head} 現在時刻：${now}")
        true
      case _ => false
    }
  }
}
