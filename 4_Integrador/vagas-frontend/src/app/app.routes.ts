import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { LayoutComponent } from './components/layout/layout.component';
import { UsuarioListComponent } from './components/usuario/usuario-list/usuario-list.component';
import { UsuarioFormComponent } from './components/usuario/usuario-form/usuario-form.component';
import { CandidatoListComponent } from './components/candidato/candidato-list/candidato-list.component';
import { CandidatoFormComponent } from './components/candidato/candidato-form/candidato-form.component';
import { EmpresaListComponent } from './components/empresa/empresa-list/empresa-list.component';
import { EmpresaFormComponent } from './components/empresa/empresa-form/empresa-form.component';
import { VagaListComponent } from './components/vaga/vaga-list/vaga-list.component';
import { VagaFormComponent } from './components/vaga/vaga-form/vaga-form.component';
import { EnderecoListComponent } from './components/endereco/endereco-list/endereco-list.component';
import { EnderecoFormComponent } from './components/endereco/endereco-form/endereco-form.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'app',
    component: LayoutComponent,
    children: [
      { path: 'usuarios', component: UsuarioListComponent },
      { path: 'usuarios/novo', component: UsuarioFormComponent },
      { path: 'usuarios/editar/:id', component: UsuarioFormComponent },
      
      { path: 'candidatos', component: CandidatoListComponent },
      { path: 'candidatos/novo', component: CandidatoFormComponent },
      { path: 'candidatos/editar/:id', component: CandidatoFormComponent },
      
      { path: 'empresas', component: EmpresaListComponent },
      { path: 'empresas/novo', component: EmpresaFormComponent },
      { path: 'empresas/editar/:id', component: EmpresaFormComponent },
      
      { path: 'vagas', component: VagaListComponent },
      { path: 'vagas/novo', component: VagaFormComponent },
      { path: 'vagas/editar/:id', component: VagaFormComponent },
      
      { path: 'enderecos', component: EnderecoListComponent },
      { path: 'enderecos/novo', component: EnderecoFormComponent },
      { path: 'enderecos/editar/:id', component: EnderecoFormComponent },
      
      { path: '', redirectTo: 'usuarios', pathMatch: 'full' }
    ]
  }
];
