# Roadmap CRUD RBAC Spring Boot

## 1. Permission CRUD (Pertama)

Buat **Permission** terlebih dahulu karena **Role bergantung pada Permission**.

### Contoh Data Permission

```text
user:create
user:update
user:delete

news:create
news:update
news:delete
news:read
````

### Endpoint API

| Method | Endpoint            | Fungsi                  |
| ------ | ------------------- | ----------------------- |
| POST   | `/permission`      | Tambah permission       |
| GET    | `/permission`      | Ambil semua permission  |
| GET    | `/permission/{id}` | Ambil detail permission |
| PATCH    | `/permission/{id}` | Update permission       |
| DELETE | `/permission/{id}` | Hapus permission        |

### Contoh Request Body

```json
{
  "name": "news:create",
  "description": "Can create news"
}
```

### Struktur

```text
PermissionRepository
PermissionService
PermissionController
```

### Checklist

* [x] Create permission
* [x] Get all permission
* [x] Get permission by id
* [x] Update permission
* [x] Delete permission
* [x] Validation duplicate name

---

## 2. Role CRUD (Kedua)

Setelah Permission selesai.

Karena **Role memiliki kumpulan Permission**.

### Relasi

```text
Role
 └── Set<Permission>
```

### Contoh Data Role

#### ROLE_ADMIN

```text
user:create
user:update
user:delete
news:create
news:update
news:delete
news:read
```

#### ROLE_EDITOR

```text
news:create
news:update
news:read
```

### Endpoint API

| Method | Endpoint      | Fungsi            |
| ------ | ------------- | ----------------- |
| POST   | `/roles`      | Tambah role       |
| GET    | `/roles`      | Ambil semua role  |
| GET    | `/roles/{id}` | Ambil detail role |
| PUT    | `/roles/{id}` | Update role       |
| DELETE | `/roles/{id}` | Hapus role        |

### Contoh Request Body

```json
{
  "name": "ROLE_EDITOR",
  "description": "Editor role",
  "permissionsIds": [1, 2, 3]
}
```

### Flow Service

```text
ambil permission by id
↓
masukkan ke role
↓
save role
```

### Struktur

```text
RoleRepository
RoleService
RoleController
```

### Checklist

* [ ] Create role
* [ ] Get all role
* [ ] Get role by id
* [ ] Update role
* [ ] Delete role
* [ ] Assign permission ke role
* [ ] Validation duplicate role

---

## 3. User CRUD (Ketiga)

Setelah Role selesai.

Karena **User bergantung pada Role**.

### Relasi

```text
User
 └── Set<Role>
```

### Endpoint API

| Method | Endpoint      | Fungsi            |
| ------ | ------------- | ----------------- |
| POST   | `/users`      | Tambah user       |
| GET    | `/users`      | Ambil semua user  |
| GET    | `/users/{id}` | Ambil detail user |
| PUT    | `/users/{id}` | Update user       |
| DELETE | `/users/{id}` | Hapus user        |

### Contoh Request Body

```json
{
  "username": "raul",
  "email": "raul@gmail.com",
  "password": "123456",
  "roleIds": [1]
}
```

### Flow Service

```text
ambil role berdasarkan id
↓
hash password
↓
save user
```

### Struktur

```text
UserRepository
UserService
UserController
```

### Checklist

* [ ] Create user
* [ ] Get all user
* [ ] Get user by id
* [ ] Update user
* [ ] Delete user
* [ ] Assign role ke user
* [ ] Hash password BCrypt
* [ ] Validation duplicate email

---

# Struktur Folder

```text
src/main/java/com/example/app
│
├── controllers
│   ├── PermissionController
│   ├── RoleController
│   └── UserController
│
├── services
│   ├── PermissionService
│   ├── RoleService
│   └── UserService
│
├── repositories
│   ├── PermissionRepository
│   ├── RoleRepository
│   └── UserRepository
│
├── models
│   ├── User
│   ├── Role
│   └── Permission
│
├── dto
│   ├── permission
│   ├── role
│   └── user
│
└── configs
```

---

# Urutan Implementasi yang Disarankan

```text
Entity
↓
CRUD Permission
↓
CRUD Role
↓
CRUD User
↓
Seeder Data
↓
JWT Authentication
↓
Spring Security
↓
RBAC Authorization
↓
@PreAuthorize Testing
```

---

# Seeder Data Awal

## Permission

```text
user:create
user:update
user:delete

role:create
role:update
role:delete

permission:create
permission:update
permission:delete
```

## Role

### ROLE_ADMIN

Semua permission.

### ROLE_USER

```text
user:read
```

---

# Authorization Example

### Berdasarkan Role

```java
@PreAuthorize("hasRole('ADMIN')")
```

### Berdasarkan Permission

```java
@PreAuthorize("hasAuthority('user:create')")
```

## Best Practice

Gunakan:

```java
hasAuthority()
```

untuk granular access control dibanding hardcode role di banyak tempat.