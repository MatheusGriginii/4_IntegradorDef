import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

/**
 * Interceptor funcional para adicionar token JWT nas requisições HTTP
 */
export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID);
  const isBrowser = isPlatformBrowser(platformId);

  console.log('🔍 AuthInterceptor FUNCIONAL executado!');
  console.log('🔍 URL:', request.url);
  console.log('🔍 isBrowser:', isBrowser);

  // ⚠️ IMPORTANTE: Só adicionar token no BROWSER (não no SSR)
  if (!isBrowser) {
    console.log('⚠️ AuthInterceptor - Executando no SERVIDOR (SSR), pulando token');
    return next(request);
  }

  // Obter token do AuthService
  const token = authService.getToken();
  console.log('🔐 AuthInterceptor - Token:', token ? 'EXISTE' : 'NÃO EXISTE');

  // Se existe token, adicionar no header Authorization
  if (token) {
    console.log('✅ Adicionando Authorization header:', `Bearer ${token.substring(0, 20)}...`);
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  } else {
    console.log('⚠️ Token NÃO ENCONTRADO no localStorage/sessionStorage');
  }

  // Processar requisição e capturar erros
  return next(request).pipe(
    catchError((error: HttpErrorResponse) => {
      console.log('❌ Erro HTTP:', error.status);
      
      // Se erro 401 (Não autorizado), fazer logout
      if (error.status === 401) {
        authService.logout();
      }
      
      // Se erro 403 (Forbidden), redirecionar para dashboard
      if (error.status === 403) {
        console.log('⚠️ Erro 403 - redirecionando para dashboard');
        router.navigate(['/app/dashboard']);
      }

      return throwError(() => error);
    })
  );
};
