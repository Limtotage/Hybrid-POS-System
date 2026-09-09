import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminPage } from './admin-page';
import { AdminLayout } from './admin-layout/admin-layout';
import { Cashiers } from './cashiers/cashiers';
import { Products } from './products/products';

const routes: Routes = [
  {
    path: '',
    component: AdminLayout,
    children: [
      { path: 'dashboard', component: AdminPage },
      { path: 'products', component: Products },
      { path: 'categories', component: AdminPage },
      { path: 'cashier', component: Cashiers },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
