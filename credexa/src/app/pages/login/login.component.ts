import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, TranslateModule],
  template: `
  <section class="min-h-screen flex items-center justify-center bg-bg px-6">
    <div class="card w-full max-w-md">
      <div class="flex justify-between items-center mb-4">
        <h2 class="text-2xl font-semibold">{{ 'login.title' | translate }}</h2>
        <div class="lang-toggle">
          <button class="mr-2" (click)="setLang('en')">🇬🇧 {{ 'lang.en' | translate }}</button>
          <button (click)="setLang('jp')">🇯🇵 {{ 'lang.jp' | translate }}</button>
        </div>
      </div>
      <form (ngSubmit)="onSubmit()" #f="ngForm" class="space-y-4">
        <div>
          <label class="block text-sm mb-1">{{ 'login.username' | translate }}</label>
          <input class="input-field w-full" name="username" [(ngModel)]="model.username" required />
        </div>
        <div>
          <label class="block text-sm mb-1">{{ 'login.password' | translate }}</label>
          <div class="relative">
            <input class="input-field w-full pr-24" [type]="showPassword ? 'text' : 'password'" name="password" [(ngModel)]="model.password" required />
            <button type="button" class="absolute right-2 top-2 text-sm text-primary" (click)="showPassword = !showPassword">{{ 'login.showPassword' | translate }}</button>
          </div>
        </div>
        <button class="button-primary w-full" [disabled]="f.invalid || loading">{{ 'login.submit' | translate }}</button>
        <p class="text-sm text-center text-gray-600">
          <a routerLink="/register" class="text-primary">{{ 'login.signup.link' | translate }}</a>
        </p>
        <p *ngIf="error" class="text-sm text-red-600">{{ error }}</p>
      </form>
    </div>
  </section>
  `
})
export class LoginComponent {
  model = { username: '', password: '' };
  showPassword = false;
  loading = false;
  error: string | null = null;

  constructor(private auth: AuthService, private router: Router, private i18n: TranslateService) {}

  setLang(lang: 'en' | 'jp') {
    this.i18n.use(lang);
  }

  onSubmit() {
    this.loading = true;
    this.error = null;
    const lang = this.i18n.currentLang || 'en';
    this.auth.login({ username: this.model.username, password: this.model.password, language: lang.toUpperCase() })
      .subscribe({
        next: () => {
          this.loading = false;
          this.router.navigate(['/dashboard']);
        },
        error: (err) => {
          this.loading = false;
          this.error = err?.error?.error || 'Login failed';
        }
      });
  }
}
