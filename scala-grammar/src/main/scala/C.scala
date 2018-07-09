trait A {
  val foo: String
}
trait B extends A{
  lazy val bar = foo + "World"
}
class C extends B {
  val foo = "Hello"

  def printBar():Unit = println(bar)
}
