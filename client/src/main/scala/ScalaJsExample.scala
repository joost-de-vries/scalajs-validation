
package example

import scala.scalajs.js
import org.scalajs.dom
import validation.Validation

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    dom.document.getElementById("scalajsShoutOut").textContent = Validation.hello()
  }
}