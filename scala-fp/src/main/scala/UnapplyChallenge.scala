
object UnapplyChallenge extends App {

  class Book(private val title: String)

  object Book {
    def unapply(book: Book):Option[String] = Some(book.title)
  }

  def printPatternMatched(obj: AnyRef): Unit = {
    obj match {
      case Book(title) => println(s"Title: ${title}")
      case _ => println("Can't extract")
    }
  }

  printPatternMatched(new Book("理科辞典"))

}
