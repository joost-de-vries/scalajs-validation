
package example

import scala.scalajs.js
import org.scalajs.dom
import validation.Validation

import scala.scalajs.js.annotation.JSExport

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    dom.document.getElementById("scalajsShoutOut").textContent = Validation.hello("browser")
  }
}

@JSExport
object Client {
  @JSExport
  def hello(msg:String)={
    s"hello $msg"
  }
}