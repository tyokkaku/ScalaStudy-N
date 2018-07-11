// 上限境界
// T1 < T2  : T1 は T2 を継承していなければならない

abstract class Show {
  def show: String
}

class ShowablePair[T1 <: Show, T2 <: Show](val t1: T1, val t2: T2) extends Show {
  override def show: String = "(" + t1.show + "," + t2.show + ")"
}
