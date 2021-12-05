import { InjectionToken } from '@angular/core';
import { ThemeType } from './types/theme-type';

export const THEME = new InjectionToken<ThemeType>('Theme');
