object NumberFormatter {

  def format(number: Int): String = {

    // NumberFormat.getNumberInstance().format(number) 一行で ok

    val reversed: String = number.toString.reverse
    val indexedSeq: Seq[(Char, Int)] = reversed.zipWithIndex
    val nestedSeq: Seq[Seq[Char]] = indexedSeq.map(
      (t) => if (t._1 != '-' && t._2 != 0 && t._2 % 3 == 0) {
        Seq(',', t._1)
      } else {
        Seq(t._1)
      })
    nestedSeq.flatten.reverse.mkString
  }
}
