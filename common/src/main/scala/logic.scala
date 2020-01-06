/**
 * Created by eilir on 2019/12/24.
 * 虽然是报错的，但是运行良好
 */
object Logic {

  def matchlikelihood(kitten: Kitten, buyer: BuyerPreferences): Double = {
    val matches = buyer.attributes map { attribute =>
        kitten.attributes.contains(attribute)
    }
    val nums = matches map { b => if(b) 1.0 else 0.0 }
    nums.sum / nums.length
  }

}
