# Beloved

## 1. Proje Tanımı  
Beloved, kullanıcıların sevdiklerine en uygun hediyeyi zahmetsizce bulmalarını sağlayan yapay zeka destekli bir hediye platformudur. Akıllı öneri sistemi sayesinde hediye seçme sürecini hem kolay hem de keyifli hale getirir. Kişisel tercihler, özel günler ve ilişki türü gibi kriterleri dikkate alarak, her kullanıcıya özel öneriler sunar.

## 2. Proje Amacı  
Beloved e-ticaret platformunun temel amacı, kullanıcıların hediye seçme sürecini kolaylaştırarak özel günlerini daha anlamlı kılmak ve kişiler arasındaki duygusal bağları güçlendirmektir. Yapay zeka destekli öneri sistemi sayesinde kullanıcılar, sevdiklerinin ilgi alanlarına ve ilişki dinamiklerine uygun kişiselleştirilmiş hediye önerileri alabilir. Ayrıca, dijital hediye kartları oluşturma imkânı sunarak esnek ve modern bir hediyeleşme deneyimi sağlar.

## 3. Proje Kapsamı  
- Yapay zeka destekli hediye öneri sistemi  
- Kişiye özel filtreleme (ilgi alanı, ilişki türü, özel günler)  
- Dijital hediye kartı oluşturma  
- Kolay ve şık kullanıcı arayüzü  
- Online gönderim seçenekleri  

## 4. Kullanılan Teknolojiler  

### 4.1 Backend  
- **Spring Boot:** Uygulamanın temel sunucu tarafı ve RESTful servisler için  
- **FastAPI:** Yapay zeka öneri sistemi için hızlı ve asenkron bir framework

### 4.2 Frontend  
- **React:** Kullanıcı dostu, dinamik bir arayüz tasarımı için  
- **Bootstrap:** Responsive tasarım öğeleri ile şık bir arayüz  

### 4.3 Veritabanı  
- **PostgreSQL:** Güçlü ve ölçeklenebilir açık kaynaklı ilişkisel veritabanı sistemi

## 5. UML Diyagramı
```mermaid
classDiagram
    class User {
        -id: Long
        -role: Role
        -firstName: string
        -lastName: string
        -password: string
        -email: string
        -createdAt: LocalDateTime

        +register(): void
        +login(): void
        +resetPassword(): void
        +logout(): void
    }

    class Notification{
        -id: Long
        -user: User
        -message: string

        +sendNotification(): void
    }
      User "1" --> "N..*" Notification : receives
    class Order{
        -id: Long
        -user: User
        -orderItems: List~OrderItem~
        -totalPrice: BigDecimal
        -orderDate: LocalDateTime

        +placeOrder(): void
        +getOrderDetails(): void
        +cancelOrder(): void
    }
    User "1" --> "N..*" Order: places
    class OrderItem{
        -id: Long
        -order: Order
        -product: Product
        -quantity: BigDecimal
    }
     Order "1" --> "N..*" OrderItem: include
     class Cart{
        -id: Long
        -user: User
        -items: List~CartItem~
        -status: OrderStatus
        -date: LocalDateTime

        +addItem(): void
        +removeItem(): void
        +calculateTotal(): BigDecimal
     }
     User "1" --> "1..*" Cart: owns
     class CartItem{
        -id: Long
        -product: Product
        -quantity: BigDecimal
        -cart: Cart
     }
     Cart "1" --> "N..*" CartItem: contains
     class Product{
        -id: Long
        -name: string
        -description: string
        -price: BigDecimal
        -stock: integer
        -imageUrl: string
        -category: Category

        +addToCart(): void
        +addToFavorites(): void
     }
    Product "1" --> "N..*" CartItem: refersTo
    Product "1" --> "N..*" OrderItem: refersTo
    class Review{
        -id: Long
        -user: User
        -product: Product
        -rating: integer
        -comment: string
        -creadetAt: LocalDateTime

        +addReview(): void
        +deleteReviw(): void
        +updateReview():  void
        +getReview(): void
    }
    Product "1" --> "N..*" Review: reviews
    User "1" --> "N..*" Review: writes
    class Category{
        -id: Long
        -name: string
        -products: Product

        +getProducts(): string
    }
    Category "1" --> "N..*" Product:belongsTo
    class Image{
        -id: Long
        -product: Product
        -url: string
        -createdAt: LocalDateTime

        +addPhoto(): void
        +updatePhoto(): void
    }
    Product "1" --> "N..*" Image:includes 
    class Favorite{
        -id: Long
        -user: User
        -product: Product

        +addFavorite(): void
        +removeFavorite(): void
        +getFavorite(): string
    }
    Product "1" --> "N..*" Favorite:markAsFavorite
    User  "1" --> "N..*" Favorite:favorites
    class Address {
        -id: Long
        -street: string
        -city: string
        -state: string
        -postalCode: string
        -country: string
    }
    User "1" --> "N..*" Address: owns
     class UserPreference {
        -id: long
        -favoriteCategories: List~category~
        -interests: List~string~
        -specialDays: List~specialday~
        -relationshipType: relationshiptype
    }
    class RelationshipType {
        -id: long
        -typeName: string
    }
    class SpecialDay {
        -id: long
        -name: string
        -date: LocalDateTime
    }
    UserPreference "0..*" -- "0..*" Category : favorites
    UserPreference "0..*" -- "0..*" SpecialDay
    UserPreference "1" -- "1" RelationshipType
    User "1" --> "1" UserPreference : hasPreferences
    class Recommendation {
        -id: Long
        -user: User
        -product: Product
        -score: Float
        -recommendedAt: LocalDateTime

        +generateRecommendation(): void
    }
    User "1" --> "0..*" Recommendation : gets
    Product "1" --> "0..*" Recommendation : recommended
```
## 6. ER Diyagramı
```mermaid
erDiagram
    USER {
        Long id PK
        Role role
        string firstName
        string lastName
        string password
        string email
        LocalDateTime createdAt
    }
    NOTIFICATION {
        Long id PK
        string message
        Long userId FK
    }
    ORDER {
        Long id PK
        Long userId FK
        BigDecimal totalPrice
        LocalDateTime orderDate
    }
    ORDER_ITEM {
        Long id PK
        Long orderId FK
        Long productId FK
        BigDecimal quantity
    }
    CART {
        Long id PK
        Long userId FK
        OrderStatus status
        LocalDateTime date
    }
    CART_ITEM {
        Long id PK
        Long cartId FK
        Long productId FK
        BigDecimal quantity
    }
    PRODUCT {
        Long id PK
        string name
        string description
        BigDecimal price
        integer stock
        string imageUrl
        Long categoryId FK
    }
    REVIEW {
        Long id PK
        Long userId FK
        Long productId FK
        integer rating
        string comment
        LocalDateTime createdAt
    }
    CATEGORY {
        Long id PK
        string name
    }
    IMAGE {
        Long id PK
        Long productId FK
        string url
        LocalDateTime createdAt
    }
    FAVORITE {
        Long id PK
        Long userId FK
        Long productId FK
    }
    ADDRESS {
        Long id PK
        string street
        string city
        string state
        string postalCode
        string country
        Long userId FK
    }
    USER_PREFERENCE {
        Long id PK
        Long userId FK
        Long relationshipTypeId FK
    }
    RELATIONSHIP_TYPE {
        Long id PK
        string typeName
    }
    SPECIAL_DAY {
        Long id PK
        string name
        LocalDateTime date
    }
    RECOMMENDATION {
        Long id PK
        Long userId FK
        Long productId FK
        Float score
        LocalDateTime recommendedAt
    }

    USER ||--o{ NOTIFICATION : receives
    USER ||--o{ ORDER : places
    ORDER ||--o{ ORDER_ITEM : includes
    USER ||--o{ CART : owns
    CART ||--o{ CART_ITEM : contains
    PRODUCT ||--o{ CART_ITEM : referredBy
    PRODUCT ||--o{ ORDER_ITEM : referredBy
    PRODUCT ||--o{ REVIEW : reviewedIn
    USER ||--o{ REVIEW : writes
    CATEGORY ||--o{ PRODUCT : contains
    PRODUCT ||--o{ IMAGE : has
    PRODUCT ||--o{ FAVORITE : markedBy
    USER ||--o{ FAVORITE : favorites
    USER ||--o{ ADDRESS : has
    USER ||--|| USER_PREFERENCE : has
    USER_PREFERENCE ||--|| RELATIONSHIP_TYPE : defines
    USER_PREFERENCE }|..|{ CATEGORY : favorites
    USER_PREFERENCE }|..|{ SPECIAL_DAY : specialDays
    USER ||--o{ RECOMMENDATION : gets
    PRODUCT ||--o{ RECOMMENDATION : recommendedIn
```
## 7. İş Planı
## Hafta 1: Proje Planlama ve Analiz  
- Projenin tanımı, amacı ve kapsamını belirleme  
- ER ve UML Class diyagramlarının oluşturulması  
- GitHub repolarının açılması ve temel yapıların hazırlanması  

## Hafta 2: Temel Kurulumlar ve Tasarım  
- Backend (Spring Boot) ve frontend (React) projelerinin oluşturulması  
- PostgreSQL bağlantısının kurulması  
- UX/UI tasarımının belirlenmesi  
- Frontend’de anasayfa tasarımının yapılması  

## Hafta 3: Entity Tasarımı ve CRUD API Geliştirme  
- User, Notification, Order, OrderItem, Cart, CartItem, Product, Review, Category, Image, Favorite, Address, UserPreference, SpecialDay, RelationshipType, Recommendation entitylerinin oluşturulması  
- Controller, Service ve Repository katmanlarının hazırlanması  
- CRUD operasyonlarının geliştirilmesi:  
  - User: kayıt, giriş, listeleme, çıkış  
  - Product: ekleme, güncelleme, silme, listeleme  
  - Category: ekleme, güncelleme, silme, listeleme  
- API’ların Postman ile test edilmesi  

## Hafta 4: Kullanıcı Yönetimi ve Kimlik Doğrulama  
- Spring Security ve JWT ile kimlik doğrulama ve yetkilendirme  
- Kullanıcı kayıt ve giriş API’larının tamamlanması  
- Frontend’de kullanıcı kayıt ve giriş sayfalarının oluşturulması  
- Anasayfa tasarımının geliştirilmesi  

## Hafta 5: Kullanıcı Profili Geliştirme  
- Kullanıcı bilgilerini görüntüleme ve güncelleme API’larının hazırlanması  
- Frontend’de kullanıcı profil sayfasının oluşturulması  
- Kullanıcı profili için frontend ve backend entegrasyonunun tamamlanması  

## Hafta 6: Ürün, Kategori ve Sepet Yönetimi  
- Sepete ürün ekleme, silme ve listeleme API’larının geliştirilmesi  
- Sipariş oluşturma, silme ve görüntüleme API’larının hazırlanması  
- Ürün görüntüleme, listeleme ve filtreleme API’larının tamamlanması  

## Hafta 7: Sepet ve Sipariş Yönetimi Frontend  
- React üzerinde sepete ekleme, sepet görüntüleme ve sipariş oluşturma sayfalarının oluşturulması  
- Kullanıcı profilinde sipariş geçmişi sayfasının hazırlanması  
- Sepet ve sipariş yönetimi için frontend-backend entegrasyonunun tamamlanması  

## Hafta 8: Favori, Yorum ve Admin Paneli  
- Favorite ve Review API’larının oluşturulması  
- Admin API’larının geliştirilmesi (ürün/kategori/sipariş yönetimi, bildirim gönderme)  
- Frontend’de admin panel sayfalarının tamamlanması  
- Bildirim (Notification) API’larının geliştirilmesi ve entegrasyonu  
