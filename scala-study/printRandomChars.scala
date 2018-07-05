object PrintRandomChars {
  def main(args: Array[String]): Unit = {

    val test = Seq(10,-1,1,2,3,4,3,5,6,3)

    def last(seq: Seq[Int]): Int = {
      val result = seq match {
        case x::xs if xs.length > 1 => last(xs)
        case x::xs if xs.length == 1 => xs.head
      }
      result
    }
    println(last(test))
    }
}
