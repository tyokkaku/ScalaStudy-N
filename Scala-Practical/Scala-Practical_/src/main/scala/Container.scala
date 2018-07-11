// Creature
// Animal extends Creature
// Cat extends Animal

// 共変や反変によって利用者の認識と実際の型が乖離してしまう。この乖離は、引数や戻り値でコンパイルエラーを起こす
  // この問題を防ぐために、引数や戻り値には、変位に応じた境界指定をしなければならない

// 共変: サブクラスを受け付ける
  // Animal に Cat を入れることができる。このとき利用者には、これを Animal だと認識する。(実際には Cat を入れている)
class Container[+T](n: T) {
  // Animal と認識されたまま、Animal を引数に取ろうとすると(実際は Cat なのでコンパイルエラーになる)
  // したがって、あらかじめ、Cat 以上(Animal以下)を受け入れることができると宣言する必要がある
  def put[E >: T](a: E): Container[E] = new Container(a)
  def get() :T = n
}
