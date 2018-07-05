object reverse {
  def main(args: Array[String]): Unit = {

    val test = Seq(1,2,3)
    val tmp = Seq()

    def reverse(seq: Seq[Int]): Seq[Int] = {
      val result = seq match {
        case x::xs if xs.last == seq(x)  => tmp :+ x
        case x::xs => reverse(xs)
      }
      result
    }
    println(reverse(test))
    }
}
