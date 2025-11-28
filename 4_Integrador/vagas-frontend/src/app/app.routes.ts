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

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'app',
    component: LayoutComponent,
    children: [
      { path: 'dashboard', component: DashboardComponent },
      
      { path: 'usuarios', component: UsuarioListComponent },
      { path: 'usuarios/novo', component: UsuarioFormComponent },
      { path: 'usuarios/editar/:id', component: UsuarioFormComponent },
      
      { path: 'categorias', component: CategoriaListComponent },
      { path: 'categorias/novo', component: EmpresaFormComponent },
      { path: 'categorias/editar/:id', component: EmpresaFormComponent },
      
      // Rotas de empresas (compatibilidade)
      { path: 'empresas', redirectTo: 'categorias', pathMatch: 'full' },
      { path: 'empresas/cadastro', component: EmpresaFormComponent },
      { path: 'empresas/editar/:id', component: EmpresaFormComponent },
      
      { path: 'produtos', component: ProdutoListComponent },
      { path: 'produtos/novo', component: ProdutoFormComponent },
      { path: 'produtos/editar/:id', component: ProdutoFormComponent },
      
      { path: 'pedidos', component: PedidoListComponent },
      { path: 'pedidos/novo', component: PedidoFormComponent },
      { path: 'pedidos/detalhes/:id', component: PedidoFormComponent },
      
      { path: 'enderecos', component: EnderecoListComponent },
      { path: 'enderecos/cadastro', component: EnderecoFormComponent },
      { path: 'enderecos/novo', component: EnderecoFormComponent },
      { path: 'enderecos/editar/:id', component: EnderecoFormComponent },
      
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  }
];
