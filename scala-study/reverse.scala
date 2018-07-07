object reverse {
  def main(args: Array[String]): Unit = {

    val test = Seq(1,5,3)

    def reverse(seq: Seq[Int]): Seq[Int] = {
      val result = seq match {
        case Seq(x) => Seq(x)
        case x::xs => reverse(xs) :+ x
      }
      result
    }
    println(reverse(test))
    }
}

object last {
  def main(args: Array[String]): Unit = {

    val test = Seq(1,5,3)

    def last(seq: Seq[Int]): Seq[Int] = {
      val result = seq match {
        case Seq(x) => Seq(x)
        case x::xs => last(xs)
      }
      result
    }
    println(last(test))
    }
}