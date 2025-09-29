import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private baseUrl = 'http://localhost:8080/usuarios';

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

  buscarPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  buscarPorNome(nome: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/buscar?nome=${nome}`)
      .pipe(catchError(this.handleError));
  }

  buscarPorCidade(cidade: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/cidade?cidade=${cidade}`)
      .pipe(catchError(this.handleError));
  }

  buscarPorNomeECidade(nome: string, cidade: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/busca-por?nome=${nome}&cidade=${cidade}`)
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

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }
}
