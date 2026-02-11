import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OwnerPage } from './owner-page';
import { OwnerLayout } from './owner-layout/owner-layout';
import { Cashiers } from './cashiers/cashiers';
import { Products } from './products/products';

const routes: Routes = [
  {
    path: '',
    component: OwnerLayout,
    children: [
      { path: 'dashboard', component: OwnerPage },
      { path: 'products', component: Products },
      { path: 'categories', component: OwnerPage },
      { path: 'cashier', component: Cashiers },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OwnerRoutingModule {}
