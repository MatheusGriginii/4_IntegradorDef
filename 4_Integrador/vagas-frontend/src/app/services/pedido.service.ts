import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pedido, ItemPedido, StatusPedido, TipoPedido } from '../models/pedido';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {
  private baseUrl = `${environment.apiUrl}/pedidos`;

  constructor(private http: HttpClient) { }

  // CRUD básico
  listar(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(this.baseUrl);
  }

  buscarPorId(id: number): Observable<Pedido> {
    return this.http.get<Pedido>(`${this.baseUrl}/${id}`);
  }

  criar(pedido: Pedido): Observable<Pedido> {
    return this.http.post<Pedido>(this.baseUrl, pedido);
  }

  atualizar(id: number, pedido: Pedido): Observable<Pedido> {
    return this.http.put<Pedido>(`${this.baseUrl}/${id}`, pedido);
  }

  // Filtros e listagens específicas
  listarPorCliente(clienteId: number): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.baseUrl}/cliente/${clienteId}`);
  }

  listarPorStatus(status: StatusPedido): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.baseUrl}/status/${status}`);
  }

  listarPorTipo(tipo: TipoPedido): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.baseUrl}/tipo/${tipo}`);
  }

  listarPendentes(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.baseUrl}/pendentes`);
  }

  listarEmPreparo(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.baseUrl}/em-preparo`);
  }

  listarProntos(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.baseUrl}/prontos`);
  }

  listarDoDia(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.baseUrl}/do-dia`);
  }

  listarDaSemana(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.baseUrl}/da-semana`);
  }

  listarDoMes(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.baseUrl}/do-mes`);
  }

  listarPorPeriodo(inicio: string, fim: string): Observable<Pedido[]> {
    const params = new HttpParams()
      .set('inicio', inicio)
      .set('fim', fim);
    return this.http.get<Pedido[]>(`${this.baseUrl}/periodo`, { params });
  }

  // Operações de status
  confirmar(id: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/confirmar`, {});
  }

  cancelar(id: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/cancelar`, {});
  }

  atualizarStatus(id: number, status: StatusPedido): Observable<void> {
    const params = new HttpParams().set('status', status);
    return this.http.patch<void>(`${this.baseUrl}/${id}/status`, {}, { params });
  }

  marcarComoEntregue(id: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/entregar`, {});
  }

  // Gerenciamento de itens
  listarItens(pedidoId: number): Observable<ItemPedido[]> {
    return this.http.get<ItemPedido[]>(`${this.baseUrl}/${pedidoId}/itens`);
  }

  adicionarItem(pedidoId: number, produtoId: number, quantidade: number, observacoes?: string): Observable<ItemPedido> {
    let params = new HttpParams()
      .set('produtoId', produtoId.toString())
      .set('quantidade', quantidade.toString());
    
    if (observacoes) {
      params = params.set('observacoes', observacoes);
    }
    
    return this.http.post<ItemPedido>(`${this.baseUrl}/${pedidoId}/itens`, {}, { params });
  }

  removerItem(pedidoId: number, itemId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${pedidoId}/itens/${itemId}`);
  }

  // Estatísticas
  calcularVendasDoDia(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/estatisticas/vendas-dia`);
  }

  calcularVendasDoMes(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/estatisticas/vendas-mes`);
  }

  calcularVendasPorPeriodo(inicio: string, fim: string): Observable<number> {
    const params = new HttpParams()
      .set('inicio', inicio)
      .set('fim', fim);
    return this.http.get<number>(`${this.baseUrl}/estatisticas/vendas-periodo`, { params });
  }

  calcularTicketMedioDoMes(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/estatisticas/ticket-medio`);
  }

  contarPorStatus(status: StatusPedido): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/estatisticas/contar/${status}`);
  }
}

