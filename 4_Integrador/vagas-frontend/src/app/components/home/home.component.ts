import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  
  featuredProducts = [
    {
      nome: 'Pão Francês',
      descricao: 'Quentinho e crocante, direto do forno',
      preco: 0.75,
      imagem: 'assets/pao-frances.jpg'
    },
    {
      nome: 'Bolo de Chocolate',
      descricao: 'Receita artesanal da casa',
      preco: 45.00,
      imagem: 'assets/bolo-chocolate.jpg'
    },
    {
      nome: 'Croissant',
      descricao: 'Folhado e amanteigado',
      preco: 8.50,
      imagem: 'assets/croissant.jpg'
    }
  ];

  features = [
    {
      icon: 'fa-clock',
      titulo: 'Produtos Frescos',
      descricao: 'Produzidos diariamente com ingredientes selecionados'
    },
    {
      icon: 'fa-heart',
      titulo: 'Receitas Tradicionais',
      descricao: 'Sabor caseiro que você conhece e ama'
    },
    {
      icon: 'fa-award',
      titulo: 'Qualidade Garantida',
      descricao: 'Compromisso com excelência há mais de 20 anos'
    },
    {
      icon: 'fa-truck',
      titulo: 'Entrega Rápida',
      descricao: 'Receba seus produtos fresquinhos em casa'
    }
  ];

  testimonials = [
    {
      nome: 'Maria Silva',
      comentario: 'Os pães da Adorela são os melhores da região! Compro todos os dias.',
      avatar: 'M'
    },
    {
      nome: 'João Santos',
      comentario: 'A qualidade é incomparável. Recomendo os bolos para qualquer ocasião!',
      avatar: 'J'
    },
    {
      nome: 'Ana Costa',
      comentario: 'Atendimento excelente e produtos sempre frescos. Adorei!',
      avatar: 'A'
    }
  ];

  constructor(private router: Router) {}

  navegarParaLogin(): void {
    this.router.navigate(['/login']);
  }
}
