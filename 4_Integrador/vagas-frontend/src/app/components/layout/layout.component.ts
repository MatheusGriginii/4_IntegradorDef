import { Component, OnInit, OnDestroy, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MenuSuperiorComponent } from '../menu-superior/menu-superior.component';

@Component({
  selector: 'app-layout',
  imports: [RouterModule, MenuSuperiorComponent],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent implements OnInit, OnDestroy {

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit() {
    // Adiciona classe ao body para compensar a navbar fixa (apenas no browser)
    if (isPlatformBrowser(this.platformId)) {
      document.body.classList.add('has-admin-navbar');
    }
  }

  ngOnDestroy() {
    // Remove a classe quando sair da área administrativa (apenas no browser)
    if (isPlatformBrowser(this.platformId)) {
      document.body.classList.remove('has-admin-navbar');
    }
  }
}
