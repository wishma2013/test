/**
 * Created by eilir on 2019/2/25.
 */
class Rational(n: Int, d:Int) {
  require(d != 0)

  private val g = gcd(n.abs, d.abs)

  val number = n / g
  val denom = d / g

  def this(n: Int) = this(n, 1)

  private def gcd(a: Int, b: Int): Int = if(b == 0) a else gcd(b, a % b)

  def +(that: Rational) : Rational =
    new Rational(
      number * that.denom + denom + that.number,
      denom * that.denom
    )

  def *(that: Rational) : Rational =
    new Rational(
      number * that.number,
      denom * that.denom
    )

  override def toString = "" + number + "/" + denom

}
