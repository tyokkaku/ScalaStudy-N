import language.experimental.macros
import scala.reflect.macros.blackbox.Context

object MacroStudy {
  // このメソッドを利用すれば、オブジェクトのメンバーに自由にアクセスできる
  // obj : オブジェクト
  // property : オブジェクト名
  // accessorメソッドの実装は、macroで記述されることを明示する
  def accessor(obj: Any, property: String): Any = macro impl_accessor

  def impl_accessor(c: Context)(obj: c.Expr[Any], property: c.Expr[String]) = {
    import c.universe._
    val Expr(Literal(Constant(propString: String))) = property
    Select(obj.tree, TermName(propString))
  }
}
