import scala.annotation.tailrec

object Main {

//  @tailrec
//  def fact(n: Int, acc: Int):Int = if (n <= 1) acc else fact(n - 1, acc * n)

  // double(2, m => m * 3)
  def double(n: Int, f: Int => Int): Int = {
    f(f(n))
  }


  // twice
  // f に fを適用した新しい関数を返す

  // Intを返すことはできるが、関数を返す方法が分からない

  def twice(f: Int => Int): Int => Int = {
    f.andThen(f)
    // f.compose(f)
    // (x) => f(f(x))
  }

  // Main.twice((x) => x * x)
    // 10 * 10
    // 100 * 100
  // res0(10)   result: 10000

  // Main.twice((x) => x + 10)
    // 1 + 10
    // 11 + 10
  // res2(1)    result: 21

//  def search(seq: Seq[Int], f: Int => Boolean):Boolean = {
//    def searchRec(i: Int): Boolean = {
//      if(seq.length == 1) false
//      else if (f(seq(i))) true
//      else searchRec(i + 1)
//    }
//    searchRec(0)
//  }
  // 多相関数
  // すべての型で利用できる
//  def search[A](seq: Seq[A], f: A => Boolean):Boolean = {
//    def searchRec(i: Int): Boolean = {
//      if(seq.length == 1) false
//      else if (f(seq(i))) true
//      else searchRec(i + 1)
//    }
//    searchRec(0)
//  }

  // 呼び出し時の変数には型を指定する
  // search(Seq("a,","b","c","d"), { x:String => x == "a"})

  // カリー化すれば型指定なしで呼び出せる
  def search[A](seq: Seq[A])(f: A => Boolean): Boolean = {
    def searchRec(i: Int): Boolean = {
      if(seq.length == i) false
      else if(f(seq(i))) true
      else searchRec(i + 1)
    }
    searchRec(0)
  }
  // search(Seq("a,","b","c","d")){x => x == "a")

//  def isSorted[E](sortedSeq: Seq[E])(ordered: (E,E) => Boolean): Boolean = {
//    def sortRec(a: Int, b: Int): Boolean = {
//      if(sortedSeq.length == b) true
//      else if(!ordered(sortedSeq(a),sortedSeq(b))) false
//      else sortRec(a + 1, b + 1)
//    }
//    sortRec(0, 1)
//  }
  // isSorted(Seq(1,2,3,4)(_ < _)
  // isSorted(Seq(1,2,3,2)

  // 末尾再帰
  def isSorted[E](sortedSeq: Seq[E])(isOrdered: (E,E) => Boolean): Boolean = {
    def isSortedRecursive(i : Int): Boolean  = {
      if(i == sortedSeq.length - 1) true
      else if(!isOrdered(sortedSeq(i),sortedSeq(i + 1))) false
      else isSortedRecursive(i + 1)
    }
    if(sortedSeq == 0) true else isSortedRecursive(0)
  }


//  val v1: Option[Int] = Some(2)
//  val v2: Option[Int] = Some(3)
//  val v3: Option[Int] = Some(5)
//  val v4: Option[Int] = Some(7)
//  val v5: Option[Int] = Some(11)
//
//  for {
//    i1 <- v1
//    i2 <- v2
//    i3 <- v3
//    i4 <- v4
//    i5 <- v5
//  } yield i1*i2*i3*i4*i5

  // ((x * 2) + 10) / 3)

  //val f1: Option[Int => Int] = Some((x) => x * 2)
  //val f2: Option[Int => Int] = Some((x) => x + 10)
  //val f3: Option[Int => Int] = Some((x) => x / 3)
  //
  //  for {
  //    i1 <- f1
  //    i2 <- f2
  //    i3 <- f3
  //  } yield i3(i2(i1(15)))
}
