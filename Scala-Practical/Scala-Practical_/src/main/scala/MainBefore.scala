object MainRefactored {

  case class Address(id: Int, name: String, postalCode: Option[String])
  case class User(id: Int, name: String, addressId: Option[Int])

  val userDatabase: Map[Int, User] = Map (
    1 -> User(1, "太郎", Some(1)),
    2 -> User(2, "二郎", Some(2)),
    3 -> User(3, "プー太郎", None)
  )

  val addressDatabase: Map[Int, Address] = Map (
    1 -> Address(1, "渋谷", Some("150-0002")),
    2 -> Address(2, "国際宇宙ステーション", None)
  )

  // 代数的データ型
  sealed abstract class PostalCodeResult
  case class Success(postalCode: String) extends PostalCodeResult
  abstract class Failure extends PostalCodeResult
  case object UserNotFound extends Failure
  case object UserNotHasAddress extends Failure
  case object AddressNotFound extends Failure
  case object AddressNotHasPostalCode extends Failure

  // 本質的に何をしているかわかりやすくリファクタリング
  def getPostalCodeResult(userId: Int): PostalCodeResult = {
    (for {
      user <- findUser(userId)
      address <- findAddress(user)
      postalCode <- findPostalCode(address)
    } yield Success(postalCode)).merge
  }

  def findUser(userId: Int): Either[Failure, User] = {
    // toRight : Some であれば Right に包んで返す。None であれば 指定した値を Left に包んで返す
    userDatabase.get(userId).toRight(UserNotFound)
  }

  def findAddress(user: User): Either[Failure, Address] = {
    for {
      addressId <- user.addressId.toRight(UserNotHasAddress)
      address <- addressDatabase.get(addressId).toRight(AddressNotFound)
    } yield address
  }

  def findPostalCode(address: Address): Either[Failure, String] = {
    address.postalCode.toRight(AddressNotHasPostalCode)
  }
}
