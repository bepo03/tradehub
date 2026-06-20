import { FormEvent, useEffect, useMemo, useState } from 'react';

type ProductStatus = 'SELLING' | 'RESERVED' | 'SOLD_OUT';
type ReservationStatus = 'REQUESTED' | 'ACCEPTED' | 'REJECTED' | 'CANCELED' | 'COMPLETED';
type Tab = 'products' | 'categories' | 'reservations';

type ApiError = {
  code: string;
  message: string;
  errors: { field: string; message: string }[];
};

type ApiResponse<T> = {
  success: boolean;
  data: T;
  error: ApiError | null;
};

type PageResponse<T> = {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
};

type Category = {
  id: number;
  name: string;
  createdAt: string;
  updatedAt?: string;
};

type ProductCategory = {
  id: number;
  name: string;
};

type Product = {
  id: number;
  title: string;
  description?: string;
  price: number;
  status: ProductStatus;
  category: ProductCategory;
  createdAt: string;
  updatedAt?: string;
};

type ReservationProduct = {
  id: number;
  title: string;
  price: number;
  status: ProductStatus;
};

type Reservation = {
  id: number;
  product: ReservationProduct;
  buyerName: string;
  buyerPhone?: string;
  message?: string;
  status: ReservationStatus;
  createdAt: string;
  updatedAt?: string;
};

type ProductForm = {
  categoryId: string;
  title: string;
  description: string;
  price: string;
};

const emptyProductForm: ProductForm = {
  categoryId: '',
  title: '',
  description: '',
  price: ''
};

const statusLabels: Record<ProductStatus, string> = {
  SELLING: '판매중',
  RESERVED: '예약중',
  SOLD_OUT: '판매완료'
};

const reservationStatusLabels: Record<ReservationStatus, string> = {
  REQUESTED: '요청',
  ACCEPTED: '수락',
  REJECTED: '거절',
  CANCELED: '취소',
  COMPLETED: '거래완료'
};

async function request<T>(url: string, options?: RequestInit): Promise<T> {
  const response = await fetch(url, {
    headers: {
      'Content-Type': 'application/json',
      ...(options?.headers ?? {})
    },
    ...options
  });

  if (response.status === 204) {
    return undefined as T;
  }

  const body = (await response.json()) as ApiResponse<T>;

  if (!response.ok || !body.success) {
    const validation = body.error?.errors?.map((error) => `${error.field}: ${error.message}`).join(', ');
    throw new Error(validation || body.error?.message || `요청 실패 (${response.status})`);
  }

  return body.data;
}

function formatPrice(price: number) {
  return `${price.toLocaleString('ko-KR')}원`;
}

function formatDate(value: string) {
  return value ? new Date(value).toLocaleString('ko-KR') : '-';
}

export default function App() {
  const [tab, setTab] = useState<Tab>('products');
  const [products, setProducts] = useState<PageResponse<Product> | null>(null);
  const [categories, setCategories] = useState<Category[]>([]);
  const [reservations, setReservations] = useState<Reservation[]>([]);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [editingProductId, setEditingProductId] = useState<number | null>(null);
  const [productForm, setProductForm] = useState<ProductForm>(emptyProductForm);
  const [categoryName, setCategoryName] = useState('');
  const [editingCategory, setEditingCategory] = useState<Category | null>(null);
  const [filters, setFilters] = useState({ keyword: '', categoryId: '', status: '' });
  const [page, setPage] = useState(0);
  const [reservationForm, setReservationForm] = useState({
    productId: '',
    buyerName: '',
    buyerPhone: '',
    message: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const sellingProducts = useMemo(
    () => products?.content.filter((product) => product.status === 'SELLING') ?? [],
    [products]
  );

  useEffect(() => {
    void loadInitialData();
  }, []);

  useEffect(() => {
    void loadProducts();
  }, [page]);

  async function run(action: () => Promise<void>) {
    setLoading(true);
    setError('');
    try {
      await action();
    } catch (caught) {
      setError(caught instanceof Error ? caught.message : '요청 처리 중 오류가 발생했습니다.');
    } finally {
      setLoading(false);
    }
  }

  async function loadInitialData() {
    await run(async () => {
      const [categoryData, reservationData] = await Promise.all([
        request<Category[]>('/api/categories'),
        request<Reservation[]>('/api/reservations')
      ]);
      setCategories(categoryData);
      setReservations(reservationData);
      await loadProducts();
    });
  }

  async function loadProducts(nextPage = page) {
    const params = new URLSearchParams();
    params.set('page', String(nextPage));
    params.set('size', '10');
    if (filters.keyword.trim()) params.set('keyword', filters.keyword.trim());
    if (filters.categoryId) params.set('categoryId', filters.categoryId);
    if (filters.status) params.set('status', filters.status);

    const data = await request<PageResponse<Product>>(`/api/products?${params.toString()}`);
    setProducts(data);
  }

  async function reloadProductsFromFirstPage() {
    setPage(0);
    await loadProducts(0);
  }

  function resetProductForm() {
    setProductForm(emptyProductForm);
    setEditingProductId(null);
  }

  function startProductEdit(product: Product) {
    setEditingProductId(product.id);
    setProductForm({
      categoryId: String(product.category.id),
      title: product.title,
      description: product.description ?? '',
      price: String(product.price)
    });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  async function submitProduct(event: FormEvent) {
    event.preventDefault();
    await run(async () => {
      const payload = {
        categoryId: Number(productForm.categoryId),
        title: productForm.title,
        description: productForm.description,
        price: Number(productForm.price)
      };

      if (editingProductId) {
        await request<Product>(`/api/products/${editingProductId}`, {
          method: 'PUT',
          body: JSON.stringify(payload)
        });
      } else {
        await request<Product>('/api/products', {
          method: 'POST',
          body: JSON.stringify(payload)
        });
      }

      resetProductForm();
      await reloadProductsFromFirstPage();
    });
  }

  async function deleteProduct(productId: number) {
    await run(async () => {
      await request<void>(`/api/products/${productId}`, { method: 'DELETE' });
      if (selectedProduct?.id === productId) setSelectedProduct(null);
      await loadProducts();
    });
  }

  async function selectProduct(productId: number) {
    await run(async () => {
      setSelectedProduct(await request<Product>(`/api/products/${productId}`));
    });
  }

  async function submitCategory(event: FormEvent) {
    event.preventDefault();
    await run(async () => {
      if (editingCategory) {
        await request<Category>(`/api/categories/${editingCategory.id}`, {
          method: 'PUT',
          body: JSON.stringify({ name: categoryName })
        });
      } else {
        await request<Category>('/api/categories', {
          method: 'POST',
          body: JSON.stringify({ name: categoryName })
        });
      }

      setCategoryName('');
      setEditingCategory(null);
      setCategories(await request<Category[]>('/api/categories'));
    });
  }

  async function deleteCategory(categoryId: number) {
    await run(async () => {
      await request<void>(`/api/categories/${categoryId}`, { method: 'DELETE' });
      setCategories(await request<Category[]>('/api/categories'));
    });
  }

  async function submitReservation(event: FormEvent) {
    event.preventDefault();
    await run(async () => {
      await request<Reservation>('/api/reservations', {
        method: 'POST',
        body: JSON.stringify({
          productId: Number(reservationForm.productId),
          buyerName: reservationForm.buyerName,
          buyerPhone: reservationForm.buyerPhone,
          message: reservationForm.message
        })
      });

      setReservationForm({ productId: '', buyerName: '', buyerPhone: '', message: '' });
      setReservations(await request<Reservation[]>('/api/reservations'));
      await loadProducts();
    });
  }

  async function changeReservationStatus(reservationId: number, action: 'accept' | 'reject' | 'cancel' | 'complete') {
    await run(async () => {
      await request<Reservation>(`/api/reservations/${reservationId}/${action}`, { method: 'PATCH' });
      setReservations(await request<Reservation[]>('/api/reservations'));
      await loadProducts();
      if (selectedProduct) {
        setSelectedProduct(await request<Product>(`/api/products/${selectedProduct.id}`));
      }
    });
  }

  return (
    <main className="app">
      <header className="topbar">
        <div>
          <h1>TradeHub</h1>
          <p>상품, 카테고리, 거래 예약 관리</p>
        </div>
        <div className="status">{loading ? '처리 중' : '준비됨'}</div>
      </header>

      <nav className="tabs" aria-label="주요 화면">
        <button className={tab === 'products' ? 'active' : ''} onClick={() => setTab('products')}>상품</button>
        <button className={tab === 'categories' ? 'active' : ''} onClick={() => setTab('categories')}>카테고리</button>
        <button className={tab === 'reservations' ? 'active' : ''} onClick={() => setTab('reservations')}>예약</button>
      </nav>

      {error && <div className="alert">{error}</div>}

      {tab === 'products' && (
        <section className="grid two">
          <div className="panel">
            <h2>{editingProductId ? '상품 수정' : '상품 등록'}</h2>
            <form onSubmit={submitProduct} className="form">
              <label>
                카테고리
                <select
                  value={productForm.categoryId}
                  onChange={(event) => setProductForm({ ...productForm, categoryId: event.target.value })}
                  required
                >
                  <option value="">선택</option>
                  {categories.map((category) => (
                    <option key={category.id} value={category.id}>{category.name}</option>
                  ))}
                </select>
              </label>
              <label>
                상품명
                <input
                  value={productForm.title}
                  onChange={(event) => setProductForm({ ...productForm, title: event.target.value })}
                  maxLength={100}
                  required
                />
              </label>
              <label>
                설명
                <textarea
                  value={productForm.description}
                  onChange={(event) => setProductForm({ ...productForm, description: event.target.value })}
                  maxLength={1000}
                  required
                />
              </label>
              <label>
                가격
                <input
                  type="number"
                  min="0"
                  value={productForm.price}
                  onChange={(event) => setProductForm({ ...productForm, price: event.target.value })}
                  required
                />
              </label>
              <div className="actions">
                <button type="submit">{editingProductId ? '수정' : '등록'}</button>
                {editingProductId && <button type="button" className="secondary" onClick={resetProductForm}>취소</button>}
              </div>
            </form>
          </div>

          <div className="panel">
            <h2>상품 검색</h2>
            <div className="filters">
              <input
                placeholder="상품명"
                value={filters.keyword}
                onChange={(event) => setFilters({ ...filters, keyword: event.target.value })}
              />
              <select value={filters.categoryId} onChange={(event) => setFilters({ ...filters, categoryId: event.target.value })}>
                <option value="">전체 카테고리</option>
                {categories.map((category) => (
                  <option key={category.id} value={category.id}>{category.name}</option>
                ))}
              </select>
              <select value={filters.status} onChange={(event) => setFilters({ ...filters, status: event.target.value })}>
                <option value="">전체 상태</option>
                <option value="SELLING">판매중</option>
                <option value="RESERVED">예약중</option>
                <option value="SOLD_OUT">판매완료</option>
              </select>
              <button onClick={() => void run(reloadProductsFromFirstPage)}>조회</button>
            </div>
            <div className="list">
              {products?.content.map((product) => (
                <article key={product.id} className="item">
                  <div>
                    <strong>{product.title}</strong>
                    <span>{product.category.name} · {statusLabels[product.status]} · {formatPrice(product.price)}</span>
                  </div>
                  <div className="row-actions">
                    <button className="secondary" onClick={() => void selectProduct(product.id)}>상세</button>
                    <button className="secondary" onClick={() => startProductEdit(product)}>수정</button>
                    <button className="danger" onClick={() => void deleteProduct(product.id)}>삭제</button>
                  </div>
                </article>
              ))}
              {products?.content.length === 0 && <p className="empty">상품이 없습니다.</p>}
            </div>
            {products && (
              <div className="pager">
                <button disabled={products.first} onClick={() => setPage((value) => Math.max(0, value - 1))}>이전</button>
                <span>{products.page + 1} / {Math.max(products.totalPages, 1)}</span>
                <button disabled={products.last} onClick={() => setPage((value) => value + 1)}>다음</button>
              </div>
            )}
            {selectedProduct && (
              <div className="detail">
                <h3>{selectedProduct.title}</h3>
                <p>{selectedProduct.description}</p>
                <dl>
                  <div><dt>카테고리</dt><dd>{selectedProduct.category.name}</dd></div>
                  <div><dt>상태</dt><dd>{statusLabels[selectedProduct.status]}</dd></div>
                  <div><dt>가격</dt><dd>{formatPrice(selectedProduct.price)}</dd></div>
                  <div><dt>등록일</dt><dd>{formatDate(selectedProduct.createdAt)}</dd></div>
                </dl>
              </div>
            )}
          </div>
        </section>
      )}

      {tab === 'categories' && (
        <section className="grid two">
          <div className="panel">
            <h2>{editingCategory ? '카테고리 수정' : '카테고리 등록'}</h2>
            <form onSubmit={submitCategory} className="form">
              <label>
                카테고리명
                <input
                  value={categoryName}
                  onChange={(event) => setCategoryName(event.target.value)}
                  maxLength={50}
                  required
                />
              </label>
              <div className="actions">
                <button type="submit">{editingCategory ? '수정' : '등록'}</button>
                {editingCategory && (
                  <button type="button" className="secondary" onClick={() => {
                    setEditingCategory(null);
                    setCategoryName('');
                  }}>취소</button>
                )}
              </div>
            </form>
          </div>
          <div className="panel">
            <h2>카테고리 목록</h2>
            <div className="list">
              {categories.map((category) => (
                <article key={category.id} className="item">
                  <div>
                    <strong>{category.name}</strong>
                    <span>{formatDate(category.createdAt)}</span>
                  </div>
                  <div className="row-actions">
                    <button className="secondary" onClick={() => {
                      setEditingCategory(category);
                      setCategoryName(category.name);
                    }}>수정</button>
                    <button className="danger" onClick={() => void deleteCategory(category.id)}>삭제</button>
                  </div>
                </article>
              ))}
              {categories.length === 0 && <p className="empty">카테고리가 없습니다.</p>}
            </div>
          </div>
        </section>
      )}

      {tab === 'reservations' && (
        <section className="grid two">
          <div className="panel">
            <h2>예약 요청</h2>
            <form onSubmit={submitReservation} className="form">
              <label>
                상품
                <select
                  value={reservationForm.productId}
                  onChange={(event) => setReservationForm({ ...reservationForm, productId: event.target.value })}
                  required
                >
                  <option value="">선택</option>
                  {sellingProducts.map((product) => (
                    <option key={product.id} value={product.id}>{product.title} · {formatPrice(product.price)}</option>
                  ))}
                </select>
              </label>
              <label>
                구매자 이름
                <input
                  value={reservationForm.buyerName}
                  onChange={(event) => setReservationForm({ ...reservationForm, buyerName: event.target.value })}
                  maxLength={50}
                  required
                />
              </label>
              <label>
                구매자 연락처
                <input
                  value={reservationForm.buyerPhone}
                  onChange={(event) => setReservationForm({ ...reservationForm, buyerPhone: event.target.value })}
                  maxLength={30}
                  required
                />
              </label>
              <label>
                메시지
                <textarea
                  value={reservationForm.message}
                  onChange={(event) => setReservationForm({ ...reservationForm, message: event.target.value })}
                  maxLength={500}
                />
              </label>
              <button type="submit">예약 생성</button>
            </form>
          </div>
          <div className="panel">
            <h2>예약 목록</h2>
            <div className="list">
              {reservations.map((reservation) => (
                <article key={reservation.id} className="item reservation">
                  <div>
                    <strong>{reservation.product.title}</strong>
                    <span>{reservation.buyerName} · {reservationStatusLabels[reservation.status]}</span>
                  </div>
                  <div className="row-actions">
                    <button className="secondary" onClick={() => void changeReservationStatus(reservation.id, 'accept')}>수락</button>
                    <button className="secondary" onClick={() => void changeReservationStatus(reservation.id, 'reject')}>거절</button>
                    <button className="secondary" onClick={() => void changeReservationStatus(reservation.id, 'cancel')}>취소</button>
                    <button onClick={() => void changeReservationStatus(reservation.id, 'complete')}>완료</button>
                  </div>
                </article>
              ))}
              {reservations.length === 0 && <p className="empty">예약이 없습니다.</p>}
            </div>
          </div>
        </section>
      )}
    </main>
  );
}
