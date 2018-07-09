object Graph {
  // 隣接行列でグラフを表現する
  // 二次元配列
  // ABCDE  :: 01234
  val matrix: Array[Array[Int]] = Array(
    Array(0,1,1,0,0),
    Array(1,0,0,1,0),
    Array(1,0,0,1,1),
    Array(0,1,1,0,1),
    Array(0,0,1,1,0)
  )

  // 隣接リスト
  // 連想配列
  val list: Map[Char, Seq[Char]] = Map(
    'A' -> Seq('B','C'),
    'B' -> Seq('A','D'),
    'C' -> Seq('A','D','E'),
    'D' -> Seq('B','C','E'),
    'E' -> Seq('C','D')
  )

  // 隣接リストの辺の情報だけに注目
  case class Edge(from: Char, to: Char)

  val edges = Seq(
    Edge('A', 'B'),
    Edge('A', 'C'),
    Edge('B', 'A'),
    Edge('B', 'D'),
    Edge('C', 'A'),
    Edge('C', 'D'),
    Edge('C', 'E'),
    Edge('D', 'B'),
    Edge('D', 'C'),
    Edge('D', 'E'),
    Edge('E', 'C'),
    Edge('E', 'D')
  )
}
