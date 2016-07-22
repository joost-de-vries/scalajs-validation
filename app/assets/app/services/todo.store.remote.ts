import {Injectable} from "@angular/core"
import {TodoStore} from "./todo.store"
import { Http, Response } from "@angular/http"
import { Observable } from "rxjs/Observable"
import { Observer } from "rxjs/Observer"

import { Headers, RequestOptions } from "@angular/http"

import "rxjs/add/operator/map"
import "rxjs/add/operator/catch"
import "rxjs/add/observable/empty"

export interface Todo {
    completed: Boolean
    editing: Boolean
    title: String
}

@Injectable()
export class RemoteStorageTodoStore implements TodoStore {
    todos:Array<Todo> = []
    private toDoUrl: string = "todos"
    errorMessage: string

    constructor(private http: Http) {
        this.getTodos()
            .subscribe(
                tds => this.todos = tds,
                error =>  this.errorMessage = error
            )
    }

    getTodos (): Observable<Todo[]> {
        return this.http.get(this.toDoUrl)
            .map(this.extractData)
            .catch(this.handleError)
    }

    private extractData(res: Response) {
        let body = res.json()
        return body || []
    }

    private handleError (error: any) {
        let errMsg = (error.message) ? error.message :
            error.status ? `${error.status} - ${error.statusText}` : "Server error"
        console.error(errMsg) // log to console instead
        return Observable.empty()
    }

    private updateStore() {
        let headers = new Headers({ "Content-Type": "application/json" })
        let options = new RequestOptions({ headers: headers })
        return this.http.post(this.toDoUrl, JSON.stringify(this.todos), options)
            .map(this.extractData)
            .catch(this.handleError)
            .subscribe(todos => console.log("updated the store successfully"),
                       error =>  console.log(`received an error ${error}`))
    }

    private getWithCompleted(completed:Boolean) {
        return this.todos.filter((todo:Todo) => todo.completed === completed)
    }

    allCompleted() {
        return this.todos.length === this.getCompleted().length
    }

    setAllTo(completed:Boolean) {
        this.todos.forEach((t:Todo) => t.completed = completed)
        this.updateStore()
    }

    removeCompleted() {
        this.todos = this.getWithCompleted(false)
        this.updateStore()
    }

    getRemaining() {
        return this.getWithCompleted(false)
    }

    getCompleted() {
        return this.getWithCompleted(true)
    }

    toggleCompletion(todo:Todo) {
        todo.completed = !todo.completed
        this.updateStore()
    }

    remove(todo:Todo) {
        this.todos.splice(this.todos.indexOf(todo), 1)
        this.updateStore()
    }

    add(title:String) {
        const todo = <Todo>{
            title: title
        }
        this.todos.push(todo)
        this.updateStore()
    }
}
