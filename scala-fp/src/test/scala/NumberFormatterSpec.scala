import org.scalatest._


class NumberFormatterSpec extends FlatSpec with DiagrammedAssertions  {

    "format関数" should "整数を受け取り、3桁ごとにカンマで区切った文字列を返す" in {
        assert(NumberFormatter.format(0) === "0")
        assert(NumberFormatter.format(1) === "1")
        assert(NumberFormatter.format(100) === "100")
        assert(NumberFormatter.format(-100) === "-100")
        assert(NumberFormatter.format(1000) === "1,000")
        assert(NumberFormatter.format(-1000) === "-1,000")
  }
}
