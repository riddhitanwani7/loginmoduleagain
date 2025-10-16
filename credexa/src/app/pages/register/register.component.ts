import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, TranslateModule],
  template: `
  <section class="min-h-screen flex items-center justify-center bg-bg px-6">
    <div class="card w-full max-w-md">
      <div class="flex justify-between items-center mb-4">
        <h2 class="text-2xl font-semibold">{{ 'register.title' | translate }}</h2>
        <div class="lang-toggle">
          <button class="mr-2" (click)="setLang('en')">🇬🇧 {{ 'lang.en' | translate }}</button>
          <button (click)="setLang('jp')">🇯🇵 {{ 'lang.jp' | translate }}</button>
        </div>
      </div>
      <form (ngSubmit)="onSubmit()" #f="ngForm" class="space-y-4">
        <div>
          <label class="block text-sm mb-1">{{ 'register.name' | translate }}</label>
          <input class="input-field w-full" name="username" [(ngModel)]="model.username" required />
        </div>
        <div>
          <label class="block text-sm mb-1">{{ 'register.email' | translate }}</label>
          <input class="input-field w-full" type="email" name="email" [(ngModel)]="model.email" required />
        </div>
        <div>
          <label class="block text-sm mb-1">{{ 'register.password' | translate }}</label>
          <input class="input-field w-full" type="password" name="password" [(ngModel)]="model.password" required />
        </div>
        <div>
          <label class="block text-sm mb-1">{{ 'register.confirmPassword' | translate }}</label>
          <input class="input-field w-full" type="password" name="confirmPassword" [(ngModel)]="model.confirmPassword" required />
        </div>
        <div>
          <label class="block text-sm mb-1">{{ 'register.language' | translate }}</label>
          <select class="input-field w-full" name="preferredLanguage" [(ngModel)]="model.preferredLanguage" required>
            <option value="EN">{{ 'lang.en' | translate }}</option>
            <option value="JP">{{ 'lang.jp' | translate }}</option>
          </select>
        </div>
        <div>
          <label class="block text-sm mb-1">{{ 'register.currency' | translate }}</label>
          <select class="input-field w-full" name="preferredCurrency" [(ngModel)]="model.preferredCurrency" required>
            <option value="INR">INR</option>
            <option value="JPY">JPY</option>
            <option value="KWD">KWD</option>
          </select>
        </div>
        <button class="button-primary w-full" [disabled]="f.invalid || loading">{{ 'register.submit' | translate }}</button>
        <p *ngIf="error" class="text-sm text-red-600">{{ error }}</p>
      </form>
    </div>
  </section>
  `
})
export class RegisterComponent {
  model = { username: '', email: '', password: '', confirmPassword: '', preferredLanguage: 'EN', preferredCurrency: 'INR' };
  loading = false;
  error: string | null = null;

  constructor(private auth: AuthService, private router: Router, private i18n: TranslateService) {}

  setLang(lang: 'en' | 'jp') { this.i18n.use(lang); }

  onSubmit() {
    this.loading = true;
    this.error = null;
    this.auth.register(this.model).subscribe({
      next: () => { this.loading = false; this.router.navigate(['/dashboard']); },
      error: (err) => { this.loading = false; this.error = err?.error?.error || 'Registration failed'; }
    });
  }
}
