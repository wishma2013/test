import org.specs2.mutable.Specification

/**
 * Created by eilir on 2019/12/24.
 */
class AnotherTest {

}
object LogicSpec extends Specification {
  "The 'matchlikelihood' method" should{
    "be 100% when all attributes match" in {
      val tabby = Kitten(1, List("male", "tabby"))
      val prefs = BuyerPreferences(List("male", "tabby"))
      val result = Logic.matchlikelihood(tabby, prefs)
      result must beGreaterThan(.999)
    }
  }

//  "Home page" should {
//    // (1)
//    "redirect to kitten list" in {
//      go to "http://localhost:9000" // (2)
//      currentUrl should startWith("http://localhost:9000/first") // (3)
//    }
//  }
//
//  it should "show three dropdown lists of attributes in sorted order" in {
//    def select(name: String) =
//      findAll(xpath("//select[@name='" + name + "']/option")).
//        map { _.text }.toList
//    def assertListCompleteAndIsSorted(list: Seq[String]) = {
//      list.size should be(20)
//      list.sorted should be(list)
//    }
//    go to homePage + "/kittens"
//    assertListCompleteAndIsSorted(select("select1"))
//    assertListCompleteAndIsSorted(select("select2"))
//    assertListCompleteAndIsSorted(select("select3"))
//  }
}
class LogicSpec2 extends Specification {
  "The 'matchlikelihood' method" should {
  "be 0% when no attributes match" in{
    val tabby = Kitten(1, List("male", "tabby"))
    val prefs = BuyerPreferences(List("female", "calico"))
    val result = Logic.matchlikelihood(tabby, prefs)
    result must beLessThan(.001)

  }
}
}
