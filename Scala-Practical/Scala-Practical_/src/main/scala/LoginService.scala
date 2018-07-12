// 代数的データ型
  // sealed trait : データ型
  // case class : データコンストラクタ
sealed trait LoginError
case object InvalidPassword extends LoginError  // パスワードの間違い
case object UserNotFound extends LoginError // name で指定されたユーザーが見つからない場合のエラー
case object PasswordLocked extends LoginError // パスワードがロックされている場合のエラー

case class User(id: Long, name: String, password: String)

object LoginService {
  def login(name: String, password: String): Either[LoginError, User] = {
    (name, password) match {
      case ("taro", "password1") => Right(User(1, name, password))
      case ("taro", _) => Left(InvalidPassword)
      case (_, _) => Left(UserNotFound)
    }
  }

}

//LoginService.login(name = "taro", password = "password1") match {
//  case Right(user) => println(s"id: ${user.id}")
//  case Left(InvalidPassword) => println(s"Invalid Password")
//  case Left(UserNotFound) => println(s"User Not Found")
//  case Left(PasswordLocked) => println(s"Password Locked")
//}
