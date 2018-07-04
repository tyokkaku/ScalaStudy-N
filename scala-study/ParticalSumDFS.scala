object PartialSumDFS extends App {
  val a = Seq(3, 6, -5, 7)
  val n = a.length
  val k = 8

  // partial : ここまで足すことが決定した整数のシーケンス

  def isMatchAndResult(index: Int, partial: Seq[Int]): (Boolean, Seq[Int]) = {
    // 再帰呼び出しの修了条件
    if(index == n) {
      return if (partial.sum == k) (true, partial) else (false, Seq())
    }
    // 再帰呼び出しの途中のコード
    // 加えない場合の再帰呼び出し
    val (isMatchNotAdd, resultNotAdd) = isMatchAndResult(index + 1, partial)
    if (isMatchNotAdd) return (isMatchNotAdd, resultNotAdd)

    // 加える場合の再帰呼び出し
    isMatchAndResult(index + 1, partial :+ a(index))
  }

  val (isMatch, result) = isMatchAndResult(0, Seq())
  if (isMatch) println(s"Yes ${result}") else println("No")
}
