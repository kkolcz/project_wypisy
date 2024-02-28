import { Injectable } from '@angular/core';
import { IProduct } from '../models/product.model';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root',
})
export class ProductsService {
  // test = new Product(11, '23');

  // productsList = [
  //   { id: 0, name: 'product0' },
  //   { id: 1, name: 'product1' },
  //   { id: 2, name: 'product2' },
  //   { id: 3, name: 'product3' },
  //   { id: 4, name: 'product4' },
  // ];

  productsList = [
    // new Product(0, 'product0'),
    // new Product(1, 'product1'),
    // new Product(2, 'product2'),
    // new Product(3, 'product3'),
    // new Product(4, 'product4'),
  ];

  getProducts(): IProduct[] {
    // console.log(this.productsList);
    return this.productsList.slice();
  }

  getProduct(id: number): IProduct {
    const product = this.productsList.find((item) => item.id === id);

    return product;
  }

  setProducts() {}

  addProduct(product: IProduct): void {
    this.productsList.push(product);
    // console.log(this.productsList);
  }

  removeProduct() {}

  constructor() {}
}