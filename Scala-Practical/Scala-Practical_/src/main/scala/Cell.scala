class Cell[T](var value: T) {

  def put(newValue: T):Unit = {
    value = newValue
  }

  def get(): T = value

//  def printValue[T](cell: Cell[T]): Unit = println(cell.get())
}
