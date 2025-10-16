import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, TranslateModule],
  template: `
  <section class="min-h-screen bg-bg px-6 py-8">
    <div class="max-w-xl mx-auto card">
      <h2 class="text-2xl font-semibold mb-4">{{ 'profile.title' | translate }}</h2>
      <form class="space-y-4" (ngSubmit)="save()">
        <div>
          <label class="block text-sm mb-1">{{ 'profile.language' | translate }}</label>
          <select class="input-field w-full" [(ngModel)]="model.preferredLanguage" name="preferredLanguage">
            <option value="EN">{{ 'lang.en' | translate }}</option>
            <option value="JP">{{ 'lang.jp' | translate }}</option>
          </select>
        </div>
        <div>
          <label class="block text-sm mb-1">{{ 'profile.currency' | translate }}</label>
          <select class="input-field w-full" [(ngModel)]="model.preferredCurrency" name="preferredCurrency">
            <option value="INR">INR</option>
            <option value="JPY">JPY</option>
            <option value="KWD">KWD</option>
          </select>
        </div>
        <button class="button-primary">{{ 'profile.update' | translate }}</button>
      </form>
    </div>
  </section>
  `
})
export class ProfileComponent {
  model = { preferredLanguage: this.auth.user?.preferredLanguage || 'EN', preferredCurrency: this.auth.user?.preferredCurrency || 'INR' };
  constructor(private auth: AuthService) {}
  save() {
    const u = this.auth.user || {};
    localStorage.setItem('auth_user', JSON.stringify({ ...u, ...this.model }));
    alert('Updated');
  }
}
