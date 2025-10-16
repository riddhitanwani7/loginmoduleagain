import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [RouterLink, TranslateModule],
  template: `
  <section class="min-h-screen flex items-center justify-center bg-bg px-6">
    <div class="card max-w-xl text-center">
      <h1 class="text-3xl font-semibold text-gray-900 mb-4">{{ 'landing.title' | translate }}</h1>
      <a class="button-primary inline-block" [routerLink]="['/login']">{{ 'landing.cta' | translate }}</a>
    </div>
  </section>
  `
})
export class LandingComponent {}
