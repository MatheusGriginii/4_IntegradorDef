import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { LayoutComponent } from './components/layout/layout.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UsuarioListComponent } from './components/usuario/usuario-list/usuario-list.component';
import { UsuarioFormComponent } from './components/usuario/usuario-form/usuario-form.component';
import { ClienteListComponent } from './components/candidato/candidato-list/candidato-list.component';
import { CandidatoFormComponent } from './components/candidato/candidato-form/candidato-form.component';
import { CategoriaListComponent } from './components/empresa/empresa-list/empresa-list.component';
import { EmpresaFormComponent } from './components/empresa/empresa-form/empresa-form.component';
import { ProdutoListComponent } from './components/vaga/vaga-list/vaga-list.component';
import { VagaFormComponent } from './components/vaga/vaga-form/vaga-form.component';
import { EnderecoListComponent } from './components/endereco/endereco-list/endereco-list.component';
import { EnderecoFormComponent } from './components/endereco/endereco-form/endereco-form.component';

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
      
      { path: 'clientes', component: ClienteListComponent },
      { path: 'clientes/novo', component: CandidatoFormComponent },
      { path: 'clientes/editar/:id', component: CandidatoFormComponent },
      
      { path: 'categorias', component: CategoriaListComponent },
      { path: 'categorias/novo', component: EmpresaFormComponent },
      { path: 'categorias/editar/:id', component: EmpresaFormComponent },
      
      { path: 'produtos', component: ProdutoListComponent },
      { path: 'produtos/novo', component: VagaFormComponent },
      { path: 'produtos/editar/:id', component: VagaFormComponent },
      
      { path: 'enderecos', component: EnderecoListComponent },
      { path: 'enderecos/novo', component: EnderecoFormComponent },
      { path: 'enderecos/editar/:id', component: EnderecoFormComponent },
      
      // Rotas antigas (redirecionamentos para compatibilidade)
      { path: 'candidatos', redirectTo: 'clientes', pathMatch: 'full' },
      { path: 'empresas', redirectTo: 'categorias', pathMatch: 'full' },
      { path: 'vagas', redirectTo: 'produtos', pathMatch: 'full' },
      
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  }
];
