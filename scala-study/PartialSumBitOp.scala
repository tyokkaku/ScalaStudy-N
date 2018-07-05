object PartialSumBitOp extends App {


  def printBits(num: Int) = {
    for (i <- 31 to 0 by -1) {
      if(i == 23 || i == 15 || i == 7) print(" ")
      if ((num & (1 << i)) != 0) print("1") else print("0")
    }
  }

  val a = Seq(1, 10, 49, 3, 8, 13, 7, 23, 60, -500, 42, 599, 45, -23, 1, 10, 49, 3, 8, 13)
  val n = a.length
  val k = 444

  var isMatch = false
  var bitsCounter = 0   // 00000000 00000000 00000000 00000000
  val max = ~(-1 << n)  // 00000000 00001111 11111111 11111111

  // 合計ループ回数は (n * 2^n) になる
    // 2 の n 乗  : 0からmaxまで。bitsCounter。全探索。
    // n         : 数列の長さ分。maskする回数。添字を足すか足さないか。

  // すべてのパターン(2 の n 乗回)を試す。
  while (!isMatch && bitsCounter <= max) {
    var sum = 0
    // 「足す」「足さない」を n 個分試す。ビット列から合算する添字を読み取る。
      //  masked は 数列の添字番号 と 対応する
    for (i <- 0 to (n - 1)) {
      val mask = 1 << i
      val masked = bitsCounter & mask
//      println("  : bitsCounter" + printBits(bitsCounter))
//      println("  : mask     " + printBits(mask))
//      println("  : masked   " + printBits(masked))
      if (masked != 0) {
        sum = sum + a(i)  // masked されたものは合算される
//        println(a(i) + "を足した")
      } else {
//        println("足さなかった")
      }
    }
//    println("----------------------------------  ")
    if (sum == k) {
      isMatch = true
    } else {
      bitsCounter = bitsCounter + 1
    }
  }

  if (isMatch) {
    var result: Seq[Int] = Seq()
    for (i <- 0 to (n - 1)) {
      val mask = 1 << i
      val masked = bitsCounter & mask
      if (masked != 0) result = result :+ a(i)
    }
    println(s"Yes ${result}")
  } else {
    println("No")
  }
}