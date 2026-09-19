export interface SaleSummary {
  totalSales: number;
  totalRevenue: number;
  totalCash: number;
  totalCard: number;
}

export interface ProductSalesReport {
  productId: number;
  productName: string;
  barcode: string;
  soldQuantity: number;
  totalRevenue: number;
}

export interface TopSellingProduct {
  productId: number;
  productName: string;
  barcode: string;
  soldQuantity: number;
}

export interface SupplierReport {
  productId: number;
  productName: string;
  barcode: string;
  lastSupplyDate: string;
  suppliedQuantity: number;
  soldQuantity: number;
  remainingQuantity: number;
}
export interface SaleReport {
  id: number;
  saleId: number;
  cashRegisterId: number;
  totalAmount: number;
  cashPaid: number;
  cardPaid: number;
  paymentType: string;
  createdAt: string;
}
export interface StockMovement {
  id: number;
  productId: number;
  type: 'PURCHASE' | 'SALE' | 'ADJUSTMENT';
  quantity: number;
  createdAt: string;
}

