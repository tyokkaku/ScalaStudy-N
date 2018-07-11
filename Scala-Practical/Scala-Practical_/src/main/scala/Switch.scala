
// 副作用
// オブジェクトが持っているフィールドの値を書き換えている
  // SwitchクラスのisOnを書き換えてしまう

// 副作用を消す
  // Switchのフィールド
  // 副作用のない状態とは？  参照透過性を持っている。値と式が交換できる。
  // 引数が同じなら戻り値は必ず同じになる。n + n に、10を入れたら必ず 100 になる。

// toggleの場合は、switch を入れたら、true か false のどちらかになってしまう。
  // toggleに n を入れたら、必ず n になるようにする
  // ただし、メソッドは1つでする

// toggleに switch を入れたら必ず switch が返ってくる

// 新しいスイッチを返す

case class Switch(isOn: Boolean) {

  def toggle(switch: Switch): Switch = {
    if (switch.isOn) Switch(false) else Switch(true)
  }

}
