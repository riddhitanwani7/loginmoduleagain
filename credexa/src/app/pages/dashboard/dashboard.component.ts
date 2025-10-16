import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { CurrencyFormatPipe } from '../../pipes/currency-format.pipe';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, TranslateModule, CurrencyFormatPipe],
  template: `
  <section class="min-h-screen bg-bg px-6 py-8">
    <div class="max-w-4xl mx-auto">
      <div class="card mb-6">
        <h2 class="text-xl font-semibold">{{ 'dashboard.welcome' | translate:{ name: user?.username || 'User' } }}</h2>
        <p class="text-gray-600">Preferred: {{ user?.preferredLanguage }} / {{ user?.preferredCurrency }}</p>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div class="card">
          <h3 class="font-semibold mb-2">Savings</h3>
          <p>{{ 1500.456 | currencyFormat:(user?.preferredCurrency || 'INR') }}</p>
        </div>
        <div class="card">
          <h3 class="font-semibold mb-2">Checking</h3>
          <p>{{ 25000 | currencyFormat:(user?.preferredCurrency || 'INR') }}</p>
        </div>
        <div class="card">
          <h3 class="font-semibold mb-2">Investments</h3>
          <p>{{ 98765.432 | currencyFormat:(user?.preferredCurrency || 'INR') }}</p>
        </div>
      </div>
      <div class="mt-6 flex gap-3">
        <a class="button-primary" [routerLink]="['/profile']">{{ 'profile.title' | translate }}</a>
        <button class="button-primary bg-red-500 hover:bg-red-600" (click)="logout()">{{ 'logout' | translate }}</button>
      </div>
    </div>
  </section>
  `
})
export class DashboardComponent {
  get user() { return this.auth.user; }
  constructor(private auth: AuthService) {}
  logout() { this.auth.logout(); location.href = '/login'; }
}
