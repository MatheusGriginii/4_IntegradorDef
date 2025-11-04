import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto } from '../models/vaga';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private baseUrl = 'http://localhost:8080/produtos';

  constructor(private http: HttpClient) { }

  listar(): Observable<Produto[]> {
    return this.http.get<Produto[]>(this.baseUrl);
  }

  buscarPorId(id: number): Observable<Produto> {
    return this.http.get<Produto>(`${this.baseUrl}/${id}`);
  }

  criar(produto: Produto): Observable<Produto> {
    return this.http.post<Produto>(this.baseUrl, produto);
  }

  atualizar(id: number, produto: Produto): Observable<Produto> {
    return this.http.put<Produto>(`${this.baseUrl}/${id}`, produto);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // Filtros personalizados
  buscarPorNome(nome: string): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.baseUrl}/buscar?nome=${nome}`);
  }

  buscarPorCategoria(categoriaNome: string): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.baseUrl}/por-categoria?categoriaNome=${categoriaNome}`);
  }

  buscarDisponiveis(): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.baseUrl}/disponiveis`);
  }
}

// Export para compatibilidade com código antigo
export { ProdutoService as VagaService };
