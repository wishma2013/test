/**
 * Created by eilir on 2019/2/26.
 */
//import org.specs2.mutable.Specification

//object LogicSpec extends Specification {
//  "The 'matchlikelihood' method" should{
//    "be 100% when all attributes match" in {
//      val tabby = Kitten(1, List("male", "tabby"))
//      val prefs = BuyerPreferences(List("male", "tabby"))
//      val result = Logic.matchlikelihood(tabby, prefs)
//      result must beGreaterThan(.999)
//    }
//  }
//}
/**
 * 这个测试需要最新的spec2包
 */
//import org.specs2._
//
//class QuickStartSpec extends Specification { def is = s3"""
//
// This is a specification to check the 'Hello world' string
//
// The 'Hello world' string should
//   contain 11 characters                                         $e1
//   start with 'Hello'                                            $e2
//   end with 'world'                                              $e3
//                                                                 """
//  def e1 = "Hello world" must have size(11)
//  def e2 = "Hello world" must startWith("Hello")
//  def e3 = "Hello world" must endWith("world")
//}

//class PluralSpec extends Specification { def is = s2"""
//
//  Names can be pluralized depending on a quantity
//  ${ "apple".plural(1) === "apple"  }
//  ${ "apple".plural(2) === "apples" }
//  ${ "foot".plural(2)  === "feet"   }
//
//  ${ 1.qty("apple") === "1 apple"  }
//  ${ 2.qty("apple") === "2 apples" }
//  ${ 2.qty("foot")  === "2 feet"   }
//  """
//}