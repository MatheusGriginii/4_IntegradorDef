import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MenuSuperiorComponent } from '../menu-superior/menu-superior.component';

@Component({
  selector: 'app-layout',
  imports: [RouterModule, MenuSuperiorComponent],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent {

}
