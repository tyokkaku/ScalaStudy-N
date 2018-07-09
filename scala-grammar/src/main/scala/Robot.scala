trait Greeter {
  def greet(): Unit
}

trait Robot {
  self: Greeter =>

  def start(): Unit = greet()
  override final def toString = "Robot"
}

// 1. Greeterを継承したトレイトに、greetメソッドを実装する
// 2. そのトレイトをミックスインして、自分型アノテーションを実装したトレイトをインスタンス化する


// 自分型アノテーション
  // 1. 型規則に対して、(継承よりも)より忠実に、可視的に、トレイトをミックスインできる
  // 2. 型の循環参照を許す
