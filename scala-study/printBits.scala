object PrintBits  {
  def printBits(num: Int) = {
    for (i <- 31 to 0 by -1) { 
      if(i == 23 || i == 15 || i == 7) print(" ")
      if ((num & (1 << i)) != 0) print("1") else print("0") 
      }
  }
}