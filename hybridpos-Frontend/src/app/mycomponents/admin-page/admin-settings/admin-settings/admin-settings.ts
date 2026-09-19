import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AdminSettingsService, AdminRequest } from '../../../../services/Admin/admin-service';

@Component({
  selector: 'app-admin-settings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-settings.html',
  styleUrl: './admin-settings.css',
})
export class AdminSettings {
  isEditMode = false;
  saving = false;

  originalUsername = '';

  admin = {
    username: '',
    password: '',
  };

  constructor(private adminSettingsService: AdminSettingsService) {}

  ngOnInit(): void {
    this.loadAdmin();
  }

  loadAdmin(): void {
    const token = localStorage.getItem('token');

    if (!token) {
      return;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));

      this.admin.username = payload.sub || payload.username || '';

      this.originalUsername = this.admin.username;
    } catch (error) {
      console.error('Admin bilgileri alınamadı:', error);
    }
  }

  startEdit(): void {
    this.originalUsername = this.admin.username;

    this.admin.password = '';

    this.isEditMode = true;
  }

  cancelEdit(): void {
    this.admin.username = this.originalUsername;

    this.admin.password = '';

    this.isEditMode = false;
  }

  saveChanges(): void {
    const username = this.admin.username.trim();

    const password = this.admin.password.trim();

    if (!username) {
      alert('Username boş olamaz.');
      return;
    }

    const data: AdminRequest = {
      username,
    };

    if (password) {
      data.password = password;
    }

    this.saving = true;

    this.adminSettingsService.updateAdmin(data).subscribe({
      next: (response) => {
        // Yeni JWT'yi kaydet
        localStorage.setItem('token', response.token);

        this.admin.username = response.username;
        this.originalUsername = response.username;
        this.admin.password = '';
        this.isEditMode = false;
        this.saving = false;

        alert('Account successfully updated.');
      },

      error: (err) => {
        console.error('Admin hesabı güncellenemedi:', err);

        this.saving = false;

        alert('Account could not be updated.');
      },
    });
  }
}
