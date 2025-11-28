import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categoria } from '../models/empresa';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {
  private baseUrl = 'http://localhost:8080/api/categorias';

  constructor(private http: HttpClient) { }

  // CRUD básico
  listar(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.baseUrl);
  }

  listarAtivas(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.baseUrl}/ativas`);
  }

  buscarPorId(id: number): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.baseUrl}/${id}`);
  }

  buscarPorNome(nome: string): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.baseUrl}/nome/${nome}`);
  }

  criar(categoria: Categoria): Observable<Categoria> {
    return this.http.post<Categoria>(this.baseUrl, categoria);
  }

  atualizar(id: number, categoria: Categoria): Observable<Categoria> {
    return this.http.put<Categoria>(`${this.baseUrl}/${id}`, categoria);
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
  buscarPorNomeContendo(nome: string): Observable<Categoria[]> {
    const params = new HttpParams().set('nome', nome);
    return this.http.get<Categoria[]>(`${this.baseUrl}/buscar`, { params });
  }

  listarComProdutos(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.baseUrl}/com-produtos`);
  }

  listarSemProdutos(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.baseUrl}/sem-produtos`);
  }
}

// Export para compatibilidade com código antigo
export { CategoriaService as EmpresaService };

