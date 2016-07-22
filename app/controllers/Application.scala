package controllers

import models.Todo
import play.api.libs.json.{Json, _}
import play.api.mvc.{Action, _}

import play.api.cache._
import play.api.mvc._
import javax.inject.Inject

class Application @Inject() () extends Controller {
  var todos = Seq.empty[Todo]

  def index = Action {
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
