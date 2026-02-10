import { ShopService } from './../../services/shop/shop-service';
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Auth } from '../../services/Auth/auth';
import { FormsModule, NgForm } from '@angular/forms';


@Component({
  selector: 'app-owner-page',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './owner-page.html',
  styleUrls: ['./owner-page.css'],
})
export class OwnerPage {
  constructor(
    private auth: Auth,
    private ShopService: ShopService,
    private router: Router,
  ) {}

  shop = {
    name: '',
    address: '',
  };
  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
  createShop(form: NgForm) {
    this.ShopService.createShop(this.shop).subscribe({
      next: () => {
        alert('Shop başarılıyla Eklendi.');
        form.resetForm();
      },
      error: (err) => {
        alert('Kayıt hatası');
        console.error(err);
      },
    });
  }
}
