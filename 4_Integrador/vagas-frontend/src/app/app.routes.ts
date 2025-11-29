import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { LayoutComponent } from './components/layout/layout.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UsuarioListComponent } from './components/usuario/usuario-list/usuario-list.component';
import { UsuarioFormComponent } from './components/usuario/usuario-form/usuario-form.component';
import { CategoriaListComponent } from './components/empresa/empresa-list/empresa-list.component';
import { EmpresaFormComponent } from './components/empresa/empresa-form/empresa-form.component';
import { ProdutoListComponent } from './components/produto/produto-list/produto-list.component';
import { ProdutoFormComponent } from './components/produto/produto-form/produto-form.component';
import { EnderecoListComponent } from './components/endereco/endereco-list/endereco-list.component';
import { EnderecoFormComponent } from './components/endereco/endereco-form/endereco-form.component';
import { PedidoListComponent } from './components/pedido/pedido-list/pedido-list.component';
import { PedidoFormComponent } from './components/pedido/pedido-form/pedido-form.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'app',
    component: LayoutComponent,
    canActivate: [AuthGuard], // Protege todo o layout
    children: [
      { path: 'dashboard', component: DashboardComponent },
      
      // Rotas de usuários - apenas ADMIN pode criar/editar/deletar
      { 
        path: 'usuarios', 
        component: UsuarioListComponent
      },
      { 
        path: 'usuarios/novo', 
        component: UsuarioFormComponent,
        canActivate: [AuthGuard],
        data: { perfis: ['ADMIN'] }
      },
      { 
        path: 'usuarios/editar/:id', 
        component: UsuarioFormComponent,
        canActivate: [AuthGuard],
        data: { perfis: ['ADMIN', 'GERENTE'] }
      },
      
      // Rotas de categorias - ADMIN e GERENTE
      { 
        path: 'categorias', 
        component: CategoriaListComponent
      },
      { 
        path: 'categorias/novo', 
        component: EmpresaFormComponent,
        canActivate: [AuthGuard],
        data: { perfis: ['ADMIN', 'GERENTE'] }
      },
      { 
        path: 'categorias/editar/:id', 
        component: EmpresaFormComponent,
        canActivate: [AuthGuard],
        data: { perfis: ['ADMIN', 'GERENTE'] }
      },
      
      // Rotas de empresas (compatibilidade)
      { path: 'empresas', redirectTo: 'categorias', pathMatch: 'full' },
      { 
        path: 'empresas/cadastro', 
        component: EmpresaFormComponent,
        canActivate: [AuthGuard],
        data: { perfis: ['ADMIN', 'GERENTE'] }
      },
      { 
        path: 'empresas/editar/:id', 
        component: EmpresaFormComponent,
        canActivate: [AuthGuard],
        data: { perfis: ['ADMIN', 'GERENTE'] }
      },
      
      // Rotas de produtos - ADMIN e GERENTE podem criar/editar
      { 
        path: 'produtos', 
        component: ProdutoListComponent
      },
      { 
        path: 'produtos/novo', 
        component: ProdutoFormComponent,
        canActivate: [AuthGuard],
        data: { perfis: ['ADMIN', 'GERENTE'] }
      },
      { 
        path: 'produtos/editar/:id', 
        component: ProdutoFormComponent,
        canActivate: [AuthGuard],
        data: { perfis: ['ADMIN', 'GERENTE'] }
      },
      
      // Rotas de pedidos - todos autenticados podem acessar
      { 
        path: 'pedidos', 
        component: PedidoListComponent
      },
      { 
        path: 'pedidos/novo', 
        component: PedidoFormComponent
      },
      { 
        path: 'pedidos/detalhes/:id', 
        component: PedidoFormComponent
      },
      
      // Rotas de endereços - todos autenticados podem acessar
      { 
        path: 'enderecos', 
        component: EnderecoListComponent
      },
      { 
        path: 'enderecos/cadastro', 
        component: EnderecoFormComponent
      },
      { 
        path: 'enderecos/novo', 
        component: EnderecoFormComponent
      },
      { 
        path: 'enderecos/editar/:id', 
        component: EnderecoFormComponent
      },
      
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  }
];
