package controllers

import models.Todo
import play.api.libs.json.{Json, _}
import play.api.mvc.{Action, _}
import play.api.cache._
import play.api.mvc._
import javax.inject.Inject

import play.api.Logger
import validation.Validation

class Application @Inject() () extends Controller {
  var todos = Seq.empty[Todo]

  def index = Action {
    Logger.debug("scalajs says "+Validation.hello("server"))
    Ok(views.html.index1())
  }

  def getTodos() = Action {
    Ok(Json.toJson(todos))
  }

  def updateTodos() = Action(parse.json) { request =>
    val parseValue = request.body.validate[Seq[Todo]]

    parseValue match{
      case JsSuccess(newTodos,_) =>
        todos=newTodos
        Ok(Json.toJson( todos ))
      case JsError(errors) => BadRequest(JsString("Expecting application/json request body"))
    }
  }

}
