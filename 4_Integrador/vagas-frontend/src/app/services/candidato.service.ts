import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Candidato } from '../models/candidato';

@Injectable({
  providedIn: 'root'
})
export class CandidatoService {
  private baseUrl = 'http://localhost:8080/candidatos';

  constructor(private http: HttpClient) { }

  listar(): Observable<Candidato[]> {
    return this.http.get<Candidato[]>(this.baseUrl);
  }

  buscarPorId(id: number): Observable<Candidato> {
    return this.http.get<Candidato>(`${this.baseUrl}/${id}`);
  }

  criar(candidato: Candidato): Observable<Candidato> {
    return this.http.post<Candidato>(this.baseUrl, candidato);
  }

  atualizar(id: number, candidato: Candidato): Observable<Candidato> {
    return this.http.put<Candidato>(`${this.baseUrl}/${id}`, candidato);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // Filtros personalizados (implementar conforme endpoints do back)
  buscarPorNome(nome: string): Observable<Candidato[]> {
    return this.http.get<Candidato[]>(`${this.baseUrl}/buscar?nome=${nome}`);
  }

  buscarPorTituloVaga(titulo: string): Observable<Candidato[]> {
    return this.http.get<Candidato[]>(`${this.baseUrl}/por-titulo?titulo=${titulo}`);
  }
}
