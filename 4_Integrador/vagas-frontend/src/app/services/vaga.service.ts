import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vaga } from '../models/vaga';

@Injectable({
  providedIn: 'root'
})
export class VagaService {
  private baseUrl = 'http://localhost:8080/vagas';

  constructor(private http: HttpClient) { }

  listar(): Observable<Vaga[]> {
    return this.http.get<Vaga[]>(this.baseUrl);
  }

  buscarPorId(id: number): Observable<Vaga> {
    return this.http.get<Vaga>(`${this.baseUrl}/${id}`);
  }

  criar(vaga: Vaga): Observable<Vaga> {
    return this.http.post<Vaga>(this.baseUrl, vaga);
  }

  atualizar(id: number, vaga: Vaga): Observable<Vaga> {
    return this.http.put<Vaga>(`${this.baseUrl}/${id}`, vaga);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // Filtros personalizados (implementar conforme endpoints do back)
  buscarPorTitulo(titulo: string): Observable<Vaga[]> {
    return this.http.get<Vaga[]>(`${this.baseUrl}/buscar?titulo=${titulo}`);
  }

  buscarPorEmpresa(empresaNome: string): Observable<Vaga[]> {
    return this.http.get<Vaga[]>(`${this.baseUrl}/por-empresa?empresaNome=${empresaNome}`);
  }
}
