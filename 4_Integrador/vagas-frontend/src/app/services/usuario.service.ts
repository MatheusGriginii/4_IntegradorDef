import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private baseUrl = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) { }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    
    if (error.error instanceof ErrorEvent) {
      // Erro do lado do cliente
      errorMessage = `Erro: ${error.error.message}`;
    } else {
      // Erro do lado do servidor
      errorMessage = `Código do erro: ${error.status}\nMensagem: ${error.message}`;
    }
    
    console.error('Erro no UsuarioService:', errorMessage);
    return throwError(() => errorMessage);
  }

  listar(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.baseUrl)
      .pipe(catchError(this.handleError));
  }

  listarAtivos(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/ativos`)
      .pipe(catchError(this.handleError));
  }

  buscarPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  buscarPorNome(nome: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/buscar?nome=${nome}`)
      .pipe(catchError(this.handleError));
  }

  buscarPorPerfil(perfil: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/perfil/${perfil}`)
      .pipe(catchError(this.handleError));
  }

  buscarAdministradores(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/administradores`)
      .pipe(catchError(this.handleError));
  }

  buscarFuncionarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/funcionarios`)
      .pipe(catchError(this.handleError));
  }

  criar(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.baseUrl, usuario)
      .pipe(catchError(this.handleError));
  }

  atualizar(id: number, usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.baseUrl}/${id}`, usuario)
      .pipe(catchError(this.handleError));
  }

  alterarStatus(id: number, ativo: boolean): Observable<Usuario> {
    return this.http.patch<Usuario>(`${this.baseUrl}/${id}/status`, { ativo })
      .pipe(catchError(this.handleError));
  }

  alterarSenha(id: number, novaSenha: string): Observable<any> {
    return this.http.patch<any>(`${this.baseUrl}/${id}/senha`, { novaSenha })
      .pipe(catchError(this.handleError));
  }

  deletar(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  deletarPermanentemente(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${id}/permanente`)
      .pipe(catchError(this.handleError));
  }

  obterEstatisticas(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/estatisticas`)
      .pipe(catchError(this.handleError));
  }

  buscarUsuariosRecentes(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/recentes`)
      .pipe(catchError(this.handleError));
  }
}
