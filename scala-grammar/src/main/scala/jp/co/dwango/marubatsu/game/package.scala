package jp.co.dwango.marubatsu

import jp.co.dwango.marubatsu.board.{CellState, Empty, Maru, Batsu}

// パッケージオブジェクト
// パッケージと同名のオブジェクト。importなしで利用できるメソッドを作ることができる。
package object game {

  // CellState を Winner に変換するメソッド
  private[game] def toWinner(cellState: CellState): Winner = cellState match {
    case board.Maru => game.Maru
    case board.Batsu => game.Batsu
    case board.Empty => game.NoWinner
  }

}
