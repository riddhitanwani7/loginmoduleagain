import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'currencyFormat', standalone: true })
export class CurrencyFormatPipe implements PipeTransform {
  transform(value: number, currency: string): string {
    const curr = (currency || 'INR').toUpperCase();
    const decimalPlaces = curr === 'JPY' ? 0 : curr === 'KWD' ? 3 : 2;
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: curr,
      minimumFractionDigits: decimalPlaces,
      maximumFractionDigits: decimalPlaces
    }).format(value ?? 0);
  }
}
