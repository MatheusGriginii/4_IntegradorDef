import { Injectable, PLATFORM_ID, Inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

export interface LoginRequest {
  email: string;
  senha: string;
}

export interface LoginResponse {
  token: string;
  tipo: string;
  id: number;
  nome: string;
  email: string;
  perfil: string;
}

export interface UsuarioLogado {
  id: number;
  nome: string;
  email: string;
  perfil: string;
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8080/api/auth';
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER_KEY = 'auth_user';
  private isBrowser: boolean;

  private usuarioLogadoSubject: BehaviorSubject<UsuarioLogado | null>;
  public usuarioLogado$: Observable<UsuarioLogado | null>;

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
    this.usuarioLogadoSubject = new BehaviorSubject<UsuarioLogado | null>(this.getUsuarioArmazenado());
    this.usuarioLogado$ = this.usuarioLogadoSubject.asObservable();
  }

  /**
   * Realiza login do usuário
   */
  login(email: string, senha: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.API_URL}/login`, { email, senha }).pipe(
      tap(response => {
        if (response && response.token) {
          this.armazenarSessao(response);
        }
      })
    );
  }

  /**
   * Realiza logout do usuário
   */
  logout(): void {
    if (this.isBrowser) {
      // Limpar storage
      localStorage.removeItem(this.TOKEN_KEY);
      localStorage.removeItem(this.USER_KEY);
      sessionStorage.removeItem(this.TOKEN_KEY);
      sessionStorage.removeItem(this.USER_KEY);
    }
    
    // Atualizar subject
    this.usuarioLogadoSubject.next(null);
    
    // Redirecionar para login
    this.router.navigate(['/login']);
  }

  /**
   * Verifica se o usuário está autenticado
   */
  isAutenticado(): boolean {
    return this.getToken() !== null;
  }

  /**
   * Obtém o token JWT armazenado
   */
  getToken(): string | null {
    if (!this.isBrowser) return null;
    return localStorage.getItem(this.TOKEN_KEY) || sessionStorage.getItem(this.TOKEN_KEY);
  }

  /**
   * Obtém o usuário logado
   */
  getUsuarioLogado(): UsuarioLogado | null {
    return this.usuarioLogadoSubject.value;
  }

  /**
   * Verifica se o usuário tem um perfil específico
   */
  temPerfil(perfil: string): boolean {
    const usuario = this.getUsuarioLogado();
    return usuario?.perfil === perfil;
  }

  /**
   * Verifica se o usuário tem um dos perfis especificados
   */
  temAlgumPerfil(perfis: string[]): boolean {
    const usuario = this.getUsuarioLogado();
    return perfis.some(perfil => usuario?.perfil === perfil);
  }

  /**
   * Verifica se o usuário é ADMIN
   */
  isAdmin(): boolean {
    return this.temPerfil('ADMIN');
  }

  /**
   * Verifica se o usuário é GERENTE
   */
  isGerente(): boolean {
    return this.temPerfil('GERENTE');
  }

  /**
   * Verifica se o usuário é FUNCIONARIO
   */
  isFuncionario(): boolean {
    return this.temPerfil('FUNCIONARIO');
  }

  /**
   * Armazena a sessão do usuário (token e dados)
   */
  private armazenarSessao(response: LoginResponse): void {
    if (!this.isBrowser) return;
    
    const usuario: UsuarioLogado = {
      id: response.id,
      nome: response.nome,
      email: response.email,
      perfil: response.perfil,
      token: response.token
    };

    // Armazenar no localStorage (permanece após fechar navegador)
    localStorage.setItem(this.TOKEN_KEY, response.token);
    localStorage.setItem(this.USER_KEY, JSON.stringify(usuario));

    // Atualizar subject
    this.usuarioLogadoSubject.next(usuario);
  }

  /**
   * Recupera o usuário armazenado no storage
   */
  private getUsuarioArmazenado(): UsuarioLogado | null {
    if (!this.isBrowser) return null;
    
    const userJson = localStorage.getItem(this.USER_KEY) || sessionStorage.getItem(this.USER_KEY);
    if (userJson) {
      try {
        return JSON.parse(userJson);
      } catch (e) {
        return null;
      }
    }
    return null;
  }

  /**
   * Valida o token no servidor
   */
  validarToken(): Observable<any> {
    return this.http.get(`${this.API_URL}/validate`);
  }
}
