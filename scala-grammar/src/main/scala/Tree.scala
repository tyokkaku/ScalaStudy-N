sealed abstract class Tree
case class Branch(value: Int, left: Tree, right: Tree) extends Tree
case object Empty extends Tree

object BinaryTree {
  val tree: Tree = Branch(1, Branch(2, Empty, Empty), Branch(3, Empty, Empty))
  //    Branch(1, Branch(2, Branch(0, Branch(1, Branch(3, Empty, Empty),Empty), Empty), Empty), Branch(3, Empty, Branch(2, Branch(3, Empty, Empty), Empty)))

  def max(tree: Tree): Int = tree match {

    case Branch(v, Empty, Empty) => v

    case Branch(v, x, Empty) => if (v < max(x)) max(x) else v

    case Branch(v, Empty, y) => if (v < max(y)) max(y) else v

    case Branch(v, x, y) =>
      if (v < max(x)) {
        if (max(x) < max(y)) {
          max(y)
        } else {
          max(x)
        }
      } else {
        if (v < max(y)) {
          max(y)
        } else {
          v
        }
      }
  }


  def min(tree: Tree): Int = tree match {
    case Branch(v, Empty, Empty) => v

    case Branch(v, x, Empty) => if (v < max(x)) v else max(x)

    case Branch(v, Empty, y) => if (v < max(y)) v else max(y)

    case Branch(v, x, y) =>
      if (v > max(x)) {
        if (max(x) > max(y)) {
          max(y)
        } else {
          max(x)
        }
      } else {
        if (v > max(y)) {
          max(y)
        } else {
          v
        }
      }
  }

  def depth(tree: Tree): Int = tree match {
    case Empty => 0
    case Branch(_, l, r) =>
      val ldepth = depth(l)
      val rdepth = depth(r)
      (if(ldepth > rdepth) ldepth else rdepth) + 1
  }
}
