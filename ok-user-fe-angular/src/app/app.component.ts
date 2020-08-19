import { Component } from '@angular/core';
import {KmpUser, UserService} from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ok-user-fe-angular';
  user = new KmpUser(
    null,
    'fName',
    'mName',
    'lName',
    '2000-01-01',
    'email@em.em',
    '+7909',
    // null
  );

  constructor(private userService: UserService) {
    userService
      .get('some-id')
      .then((response) => {
        this.user = response.data;
      });
  }

  async update(): Promise<void> {
    const userNew = { ... this.user };
    userNew.fname = 'NewFname';
    const response = await this.userService.update(userNew);
    console.log(response.status);
    if (response.status.name === 'SUCCESS') {
      this.user = response?.data;
    } else {
      console.error(response);
    }
    return;
  }
}
