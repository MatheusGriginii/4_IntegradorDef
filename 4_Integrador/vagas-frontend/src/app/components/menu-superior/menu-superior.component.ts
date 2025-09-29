import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-menu-superior',
  imports: [RouterModule],
  templateUrl: './menu-superior.component.html',
  styleUrl: './menu-superior.component.scss'
})
export class MenuSuperiorComponent {
  constructor(private router: Router) {}

  logout() {
    Swal.fire({
      title: 'Confirmação',
      text: 'Deseja realmente sair do sistema?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sim, sair',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.router.navigate(['/login']);
      }
    });
  }
}
