case class Edge(from: Char, to: Char, distance: Int)

object ShortestPath {

  /**
    * 頂点
    */
  val vertexes = 'A' to 'G'

  /**
    * 辺
    */
  val edges = Seq(
    Edge('A', 'B', 5),
    Edge('A', 'C', 2),
    Edge('B', 'A', 5),
    Edge('B', 'C', 2),
    Edge('B', 'D', 1),
    Edge('C', 'A', 2),
    Edge('C', 'B', 2),
    Edge('C', 'D', 5),
    Edge('D', 'B', 1),
    Edge('D', 'C', 5)
  )

  def test():Unit = {
    // リストを
  }


  // 自分が無限かどうか
  // 無限でなければ、自分と相手への距離を足したものが、相手よりも小さいかどうか
  // 小さければ、値を更新して、更新済みにする

  def solveByBellmanFord(start: Char, goal: Char):Unit = {
    // 各頂点までの距離の初期化
    var distances = vertexes.map(v => (v -> Int.MaxValue)).toMap
    distances = distances + (start -> 0)

    var isUpdated = true
    // 更新するものがなにもなくなったら終了する
    while (isUpdated) {
      isUpdated = false
      println("----------------------")
      edges.foreach { e =>
        if(distances(e.from) != Int.MaxValue
          // 無限の場合は必ず小さくなる
        && distances(e.to) > distances(e.from) + e.distance) {
          distances = distances + (e.to -> (distances(e.from) + e.distance))
          isUpdated = true // まだ更新できるものがあるならループを継続する
          println(isUpdated)
        } else {
          println(isUpdated)
        }
      }
    }
    println(distances)
    println(distances(goal))
  }
//
////   ダイクストラ法
////  すべて未確定とする。
////
////  「未確定」の中で、距離が距離が最も小さいノードを、その地点の最小距離として「確定」させる
////  今指定した場所から「未確定」かつ「連結」したノードの距離を計算し、小さければ更新する
//
//
//  def solveByDijkstra(start: Char, goal:Char):Unit = {
//
//    var distances = vertexes.map(v => (v -> Int.MaxValue)).toMap
//    distances = distances + (start -> 0)
//
//    var minimumDistances = vertexes.map(v => (v -> false)).toMap
//
//    // 未確定の中で距離が最も小さいノードを見つける。
//    // その地点の最小距離として「確定」させる
//
//    var min = Int.MaxValue
//
//    distances.foreach(f => {
//      if(f._2 < min) {
//        min = f._2
//      }
//    })
//
//    minimumDistances.foreach(e => {
//    })
//
//  }
}

