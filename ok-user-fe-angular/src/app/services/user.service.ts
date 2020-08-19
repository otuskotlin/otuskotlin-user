import { Injectable } from '@angular/core';
import * as transport from 'otuskotlin-user-ok-user-fe-transport-multiplatform';
import * as models from 'otuskotlin-user-ok-user-mp-transport-models';

import KmpUserBackendServiceJS = transport.ru.otus.otuskotlin.user.transport.frontend.multiplatform.KmpUserBackendServiceJS;
export import KmpUser = models.ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUser;
export import KmpUserGet = models.ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserGet;
export import KmpUserIndex = models.ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserIndex;
export import KmpUserCreate = models.ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserCreate;
export import KmpUserUpdate = models.ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserUpdate;
export import KmpUserDelete = models.ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserDelete;
export import KmpUserResponseItem = models.ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserResponseItem;
export import KmpUserResponseIndex = models.ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserResponseIndex;

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private kmpUserService = new KmpUserBackendServiceJS('http://localhost:8080/user');

  async get(id: string): Promise<KmpUserResponseItem> {
    console.log(this.kmpUserService);
    return await this.kmpUserService.get(new KmpUserGet(id, null));
  }
  async index(query: KmpUserIndex): Promise<KmpUserResponseIndex> {
    return await this.kmpUserService.index(query);
  }
  async create(query: KmpUserCreate): Promise<KmpUserResponseItem> {
    return await this.kmpUserService.create(query);
  }

  async update(query: KmpUserUpdate): Promise<KmpUserResponseItem> {
    return await this.kmpUserService.update(query);
  }

  async delete(id: string): Promise<KmpUserResponseItem> {
    return await this.kmpUserService.update(new KmpUserDelete(id));
  }
}
