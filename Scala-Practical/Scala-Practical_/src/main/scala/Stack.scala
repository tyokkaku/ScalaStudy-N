// 条件の指定
  // 共変
  // +T は、 T の サブクラスであれば代入できる

  // 反変
  // -T は、 T の スーパークラスであれば代入できる

// 境界の指定
  // 上限境界
  // [E <: T] は、 E は T のサブクラスでなければならない

  // 下限境界
  // [E >: T] は、 E は T の継承元でなければならない

trait Stack[+T] {
  def pop: (T, Stack[T])

  def push[E >: T](e: E): Stack[E]

  def isEmpty: Boolean
}

class NonEmptyStack[+T](private val top: T, private val rest: Stack[T]) extends Stack[T] {
  def push[E >: T](e: E): Stack[E] = new NonEmptyStack[E](e, this)

  // タプルを返す。タプルにアクセスするには、「_1」や「_2」を使う。基本的には、分割代入やパターンマッチが利用される。
  def pop: (T, Stack[T]) = (top, rest)

  def isEmpty: Boolean = false
}

case object EmptyStack extends Stack[Nothing] {
  def pop: Nothing = throw new IllegalArgumentException("empty stack")

  // 下限境界: Nothing のスーパークラスが入ってくる可能性をコンパイラに明示する。（これがないと、コンパイルエラー）
  def push[E >: Nothing](e: E): Stack[E] = new NonEmptyStack[E](e, this)

  def isEmpty: Boolean = true
}

object Stack {
  def apply(): Stack[Nothing] = EmptyStack
}
