import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import {
  SaleSummary,
  ProductSalesReport,
  TopSellingProduct,
  SupplierReport
} from './report-models';

@Injectable({
  providedIn: 'root',
})
export class ReportService {

  private readonly baseUrl = 'http://localhost:8080/api/reports';

  constructor(private http: HttpClient) {}

  getAllSales(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/sales`);
  }

  getSaleSummary(): Observable<SaleSummary> {
    return this.http.get<SaleSummary>(`${this.baseUrl}/summary`);
  }

  getTodaySummary(): Observable<SaleSummary> {
    return this.http.get<SaleSummary>(
      `${this.baseUrl}/summary/today`
    );
  }

  getMonthSummary(): Observable<SaleSummary> {
    return this.http.get<SaleSummary>(
      `${this.baseUrl}/summary/month`
    );
  }

  getYearSummary(): Observable<SaleSummary> {
    return this.http.get<SaleSummary>(
      `${this.baseUrl}/summary/year`
    );
  }

  getTodaysProductSales(): Observable<ProductSalesReport[]> {
    return this.http.get<ProductSalesReport[]>(
      `${this.baseUrl}/products/today`
    );
  }

  getMonthlyProductSales(): Observable<ProductSalesReport[]> {
    return this.http.get<ProductSalesReport[]>(
      `${this.baseUrl}/products/month`
    );
  }

  getYearlyProductSales(): Observable<ProductSalesReport[]> {
    return this.http.get<ProductSalesReport[]>(
      `${this.baseUrl}/products/year`
    );
  }

  getTopSellingProductsToday(): Observable<TopSellingProduct[]> {
    return this.http.get<TopSellingProduct[]>(
      `${this.baseUrl}/products/top/today`
    );
  }

  getTopSellingProductsMonth(): Observable<TopSellingProduct[]> {
    return this.http.get<TopSellingProduct[]>(
      `${this.baseUrl}/products/top/month`
    );
  }

  getTopSellingProductsYear(): Observable<TopSellingProduct[]> {
    return this.http.get<TopSellingProduct[]>(
      `${this.baseUrl}/products/top/year`
    );
  }

  getTopSellingProductsAllTime(): Observable<TopSellingProduct[]> {
    return this.http.get<TopSellingProduct[]>(
      `${this.baseUrl}/products/top/all`
    );
  }

  getAllSupplierReports(): Observable<SupplierReport[]> {
    return this.http.get<SupplierReport[]>(
      `${this.baseUrl}/supplier`
    );
  }

  getSoldQuantitySinceLastPurchase(
    productId: number
  ): Observable<SupplierReport> {

    return this.http.get<SupplierReport>(
      `${this.baseUrl}/supplier/${productId}`
    );
  }
}
