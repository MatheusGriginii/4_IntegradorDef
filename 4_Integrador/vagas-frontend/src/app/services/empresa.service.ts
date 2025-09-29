import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Empresa } from '../models/empresa';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {
  private baseUrl = 'http://localhost:8080/empresas';

  constructor(private http: HttpClient) { }

  listar(): Observable<Empresa[]> {
    return this.http.get<Empresa[]>(this.baseUrl);
  }

  buscarPorId(id: number): Observable<Empresa> {
    return this.http.get<Empresa>(`${this.baseUrl}/${id}`);
  }

  criar(empresa: Empresa): Observable<Empresa> {
    return this.http.post<Empresa>(this.baseUrl, empresa);
  }

  atualizar(id: number, empresa: Empresa): Observable<Empresa> {
    return this.http.put<Empresa>(`${this.baseUrl}/${id}`, empresa);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // Filtros personalizados (implementar conforme endpoints do back)
  buscarPorNome(nome: string): Observable<Empresa[]> {
    return this.http.get<Empresa[]>(`${this.baseUrl}/buscar?nome=${nome}`);
  }

  buscarPorTituloVaga(titulo: string): Observable<Empresa[]> {
    return this.http.get<Empresa[]>(`${this.baseUrl}/por-titulo?titulo=${titulo}`);
  }
}
