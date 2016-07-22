import {bootstrap} from "@angular/platform-browser-dynamic"
import TodoAppComponent from "./app"
import {TodoStore} from "./services/todo.store"
import {RemoteStorageTodoStore} from "./services/todo.store.remote"
import {HTTP_PROVIDERS} from "@angular/http"

bootstrap(TodoAppComponent,
    [
        {provide:TodoStore, useClass: RemoteStorageTodoStore},
        HTTP_PROVIDERS
    ]
)
