
// 深さ優先探索は、最大20個の数字を使って、その和が k になる組み合わせを探す
// 深さ優先探索は、それぞれにつき「足す」か「足さないか」を全通り確かめていく
// 深さ優先探索では、n が20個の時、調べる最大回数は 2 の 20通りの 1048757 になる

// 00000000 00000000 00000000 00000000 から
// 00000000 00001111 11111111 11111111 まで、カウントアップする

// 0のとき、足さない
// 1のとき、与えられた数列から対応する添字の整数を足す

// その合計値が k とマッチするかを確認する

object PartialSumBitOp exnteds App {
  val a = Seq(1, 10, 49, 3, 8, 13, 7, 23, 60, -500, 42, 599, 45, -23, 1, 10, 49, 3, 8, 13)
  val n = a.length
  val k = 444

  var isMatch = false
  var bitscounter = 0   // 00000000 00000000 00000000 00000000
  val max = ~(-1 << n)  // 00000000 00001111 11111111 11111111
  

  while (!isMatch && bitsCounter <= max) {
    var sum = 0
    for (i <- 0 to (n - 1)) {
      val mask = 1 << i
      val masked = bitsCounter & mask
      if (masked != 0) sum = sum + a(i)
    }
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

// bitcounter
// 00000000 00000000 00000000 00000011

// mask
// i = 2
// 00000000 00000000 00000000 00000010

// masked
// 00000000 00000000 00000000 00000010
