import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto } from '../models/produto';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private baseUrl = `${environment.apiUrl}/produtos`;

  constructor(private http: HttpClient) { }

  // CRUD básico
  listar(): Observable<Produto[]> {
    return this.http.get<Produto[]>(this.baseUrl);
  }

  listarAtivos(): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.baseUrl}/ativos`);
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

  desativar(id: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/desativar`, {});
  }

  ativar(id: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/ativar`, {});
  }

  // Filtros e buscas
  buscarPorNome(nome: string): Observable<Produto[]> {
    const params = new HttpParams().set('nome', nome);
    return this.http.get<Produto[]>(`${this.baseUrl}/buscar`, { params });
  }

  listarPorCategoria(categoriaId: number): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.baseUrl}/categoria/${categoriaId}`);
  }

  listarEstoqueBaixo(): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.baseUrl}/estoque-baixo`);
  }

  // Gerenciamento de estoque
  atualizarEstoque(id: number, quantidade: number): Observable<void> {
    const params = new HttpParams().set('quantidade', quantidade.toString());
    return this.http.patch<void>(`${this.baseUrl}/${id}/estoque`, {}, { params });
  }

  adicionarEstoque(id: number, quantidade: number): Observable<void> {
    const params = new HttpParams().set('quantidade', quantidade.toString());
    return this.http.patch<void>(`${this.baseUrl}/${id}/adicionar-estoque`, {}, { params });
  }

  diminuirEstoque(id: number, quantidade: number): Observable<void> {
    const params = new HttpParams().set('quantidade', quantidade.toString());
    return this.http.patch<void>(`${this.baseUrl}/${id}/diminuir-estoque`, {}, { params });
  }
}
