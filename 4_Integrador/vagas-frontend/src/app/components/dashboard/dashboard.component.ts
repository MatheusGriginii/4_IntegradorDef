import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DashboardService, DashboardStats } from '../../services/dashboard.service';
import { ProdutoService } from '../../services/produto.service';
import { Produto } from '../../models/produto';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  stats: DashboardStats = {
    totalProdutos: 0,
    totalCategorias: 0,
    totalClientes: 0,
    totalPedidosHoje: 0
  };
  
  produtosDestaque: Produto[] = [];
  carregando = true;

  constructor(
    private dashboardService: DashboardService,
    private produtoService: ProdutoService
  ) {}

  ngOnInit(): void {
    this.carregarDados();
  }

  carregarDados(): void {
    this.carregando = true;
    
    // Carregar estatísticas
    this.dashboardService.getStats().subscribe({
      next: (stats) => {
        this.stats = stats;
        console.log('Stats carregadas:', stats);
      },
      error: (error) => {
        console.error('Erro ao carregar stats:', error);
      }
    });

    // Carregar produtos em destaque (primeiros 3)
    this.produtoService.listarAtivos().subscribe({
      next: (produtos) => {
        this.produtosDestaque = produtos.slice(0, 3);
        this.carregando = false;
        console.log('Produtos destaque:', this.produtosDestaque);
      },
      error: (error) => {
        console.error('Erro ao carregar produtos:', error);
        this.carregando = false;
      }
    });
  }
}
