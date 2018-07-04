
object PrintFactorization {

  def main(args: Array[String]):Unit = {

    Seq(32, 25, 90, 510510).map(Factorization.factorization).map(println)

  }

}