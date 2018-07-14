import scala.reflect.runtime.universe._

object ReflectionStudy extends App {

  println("======= from class ========")
  println(typeTag[Option[_]].tpe.decls)

  val list = List(1,2,3)

  def getTypeTagFromList[T: TypeTag](list: List[T]): TypeTag[T] = {
    typeTag[T]
  }

  println("======= from class ========")
  println(getTypeTagFromList(list).tpe.decls)

}
