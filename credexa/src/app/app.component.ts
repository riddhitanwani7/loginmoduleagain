import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html'
})
export class AppComponent {
  title = 'credexa';

  constructor(private i18n: TranslateService) {
    this.i18n.setDefaultLang('en');
    this.i18n.use('en');
  }
}
