import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../models/candidato';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private baseUrl = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) { }

  listar(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.baseUrl);
  }

  buscarPorId(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.baseUrl}/${id}`);
  }

  criar(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.baseUrl, cliente);
  }

  atualizar(id: number, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.baseUrl}/${id}`, cliente);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // Filtros personalizados
  buscarPorNome(nome: string): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.baseUrl}/buscar?nome=${nome}`);
  }

  buscarPorEmail(email: string): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.baseUrl}/email?email=${email}`);
  }
}

// Export para compatibilidade com código antigo
export { ClienteService as CandidatoService };
