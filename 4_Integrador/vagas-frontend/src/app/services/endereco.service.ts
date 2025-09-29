import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Endereco } from '../models/endereco';

@Injectable({
  providedIn: 'root'
})
export class EnderecoService {
  private baseUrl = 'http://localhost:8080/enderecos';

  constructor(private http: HttpClient) { }

  listar(): Observable<Endereco[]> {
    return this.http.get<Endereco[]>(this.baseUrl);
  }

  buscarPorId(id: number): Observable<Endereco> {
    return this.http.get<Endereco>(`${this.baseUrl}/${id}`);
  }

  criar(endereco: Endereco): Observable<Endereco> {
    return this.http.post<Endereco>(this.baseUrl, endereco);
  }

  atualizar(id: number, endereco: Endereco): Observable<Endereco> {
    return this.http.put<Endereco>(`${this.baseUrl}/${id}`, endereco);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // Filtros personalizados (serão implementados conforme os endpoints do back)
  buscarPorCidade(cidade: string): Observable<Endereco[]> {
    return this.http.get<Endereco[]>(`${this.baseUrl}/buscar?cidade=${cidade}`);
  }

  buscarPorEstado(estado: string): Observable<Endereco[]> {
    return this.http.get<Endereco[]>(`${this.baseUrl}/estado?estado=${estado}`);
  }
}
