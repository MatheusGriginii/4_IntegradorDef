import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

export interface DashboardStats {
  totalProdutos: number;
  totalCategorias: number;
  totalClientes: number;
  totalPedidosHoje: number;
}

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  /**
   * Busca estatísticas para o dashboard
   */
  getStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(`${this.apiUrl}/dashboard/stats`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Busca produtos em destaque (primeiros 3 produtos ativos)
   */
  getProdutosDestaque(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/produtos?size=3`)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: any) {
    console.error('Erro no DashboardService:', error);
    return throwError(() => error);
  }
}
