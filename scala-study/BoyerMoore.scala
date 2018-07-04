object BoyerMoore {
  // スキップテーブルの作成
  def createSkipTable(pattern: Seq[Char]): Map[Char, Int] = {
    // key   : パターンの文字
    // value : スキップ幅
    var skipTable: Map[Char, Int] = Map.empty

    // パターンの末尾はスキップテーブルに必要ないため、パターンの末尾より1つ前から番号を振る
    for (i <- 1 until pattern.length) {
      if (!skipTable.isDefinedAt(pattern(i))) {
        skipTable = skipTable + (pattern(i) -> i)
      }
    }
    skipTable
  }

  // 比較する文字列を得る
  def getTextPart(currentPoint: Int, text: Seq[Char], pattern: Seq[Char]): Seq[Char] = {
    val partText = text.slice(currentPoint, currentPoint + pattern.length)
    partText
  }

  // パターンと探索対象の文字列を比較する。異なった字とカウント数を返す。
  def comparePatternAndPartText(pattern: Seq[Char], textPart: Seq[Char], currentPoint: Int): (Char, Int) = {
    var isMatch = true
    var i = pattern.length - 1
    var count = -1 // いくつめで見つけたか
    var text = ' '
    while (isMatch && i >= 0) {
      // パターンと文字列が不一致
      if (i > textPart.length - 1 || textPart(i) != pattern(i)) isMatch = false
      text = textPart(i)
      i = i - 1
      count = count + 1
    }
    // パターンと文字列が一致
    if (isMatch) {
      text = pattern(pattern.length - 1)
      count = 0
      println(currentPoint + "に" + pattern + "を見つけました。")
    }
    val result = (text, count)
    result
  }

  // スキップ幅を得る
  def getSkipNumber(skipTable: Map[Char, Int], differenceChar: Char, count: Int, pattern: Seq[Char]): Int = {
    // パターンに文字が含まれているなら
    if (skipTable.isDefinedAt(differenceChar)) {
      val skipTableNumber = skipTable(differenceChar)
      val skipNumber = skipTableNumber - count
      skipNumber
      // パターンに文字が含まれていないなら
    } else {
      val skipNumber = pattern.length - count
      skipNumber
    }
  }

  def main(args: Array[String]): Unit = {
    val text = "カワカドカドカドドワンゴカドカドンゴドワドワンゴドワカワカドンゴドワ".toSeq
    val pattern = "ンゴ".toSeq
    var currentPoint = 0

    val skipTable = createSkipTable(pattern.reverse)

    while (currentPoint <= text.length - pattern.length) {
      // 切り取った文字列をパターンと比較する
      val comparedResult = comparePatternAndPartText(pattern, getTextPart(currentPoint, text, pattern), currentPoint)
      val differenceChar = comparedResult._1
      val count = comparedResult._2
      // 比較した結果をスキップテーブルと照合して、スキップ幅を得る
      val skipNumber = getSkipNumber(skipTable, differenceChar, count, pattern)
      // スキップする
      currentPoint = currentPoint + skipNumber
    }
  }
}
