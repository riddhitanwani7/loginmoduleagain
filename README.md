# Fintech Login Module - Full Stack Application

A comprehensive Spring Boot + Angular full-stack fintech application with secure authentication and integrated modules.

## 🚀 Features

### Backend (Spring Boot)
- ✅ **Secure Authentication**: JWT-based authentication with BCrypt password encryption
- ✅ **User Management**: Registration and login with role-based access (USER/ADMIN)
- ✅ **Session Management**: 5-minute idle timeout with auto-logout
- ✅ **API Security**: Protected endpoints with JWT validation
- ✅ **PII Masking**: Sensitive data masking (AADHAR, phone numbers)
- ✅ **CORS Configuration**: Configured for Angular frontend

### Frontend (Angular 17)
- ✅ **Modern UI**: Bootstrap-based responsive design
- ✅ **Authentication**: Login and registration with credential encryption
- ✅ **Dashboard**: Single sign-on access to all modules
- ✅ **Module Integration**: Customer, Product-Pricing, FD Calculator, Fixed Deposit, Reports
- ✅ **Idle Timeout**: 5-minute auto-logout with warning
- ✅ **Localization**: Hindi language support for login screen

### Integrated Modules
1. **Customer Module**: Profile management with PII masking
2. **Product & Pricing Module**: Product catalog and pricing information
3. **FD Calculator Module**: Fixed deposit interest calculation
4. **Fixed Deposit Module**: FD creation, listing, and management
5. **Reports Module**: Account statements, FD summaries, tax certificates

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17+
- **Database**: MySQL 8.0
- **Security**: Spring Security + JWT
- **Build Tool**: Maven
- **Dependencies**: Spring Web, Spring Data JPA, Spring Security, Validation, Lombok, BCrypt, JWT

### Frontend
- **Framework**: Angular 17
- **Styling**: Bootstrap 5 + SCSS
- **HTTP Client**: Angular HttpClient with interceptors
- **State Management**: Services with RxJS
- **Build Tool**: Angular CLI
- **Dependencies**: crypto-js, ng-idle

## 📋 Prerequisites

- Java 17 or higher
- Node.js 18 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- Angular CLI 17 or higher

## 🚀 Quick Start

### Backend Setup

1. **Clone and navigate to project root**
   ```bash
   cd "login module again"
   ```

2. **Configure MySQL Database**
   - Create a MySQL database named `fintech_db`
   - Update database credentials in `src/main/resources/application.properties`:
     ```properties
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **Run Spring Boot Application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   - Backend will be available at: `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd fintech-frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start Angular development server**
   ```bash
   npm start
   ```
   - Frontend will be available at: `http://localhost:4200`

## 🔐 Default Credentials

### Test User (Auto-created)
- **Username**: `testuser`
- **Email**: `test@example.com`
- **Password**: `password123`
- **Role**: `USER`

### Admin User (Auto-created)
- **Username**: `admin`
- **Email**: `admin@example.com`
- **Password**: `admin123`
- **Role**: `ADMIN`

## 📡 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Token refresh
- `POST /api/auth/logout` - User logout

### Customer Module
- `GET /api/customer/profile` - Get customer profile
- `PUT /api/customer/profile` - Update customer profile
- `GET /api/customer/accounts` - Get customer accounts

### Product & Pricing Module
- `GET /api/product-pricing/products` - Get products
- `GET /api/product-pricing/pricing` - Get pricing information

### FD Calculator Module
- `POST /api/fd-calculator/calculate` - Calculate FD
- `GET /api/fd-calculator/rates` - Get FD rates

### Fixed Deposit Module
- `POST /api/fixed-deposit/create` - Create FD
- `GET /api/fixed-deposit/list` - List FDs
- `POST /api/fixed-deposit/premature-closure/{fdId}` - Close FD

### Reports Module
- `GET /api/reports/account-statement` - Generate account statement
- `GET /api/reports/fd-summary` - Generate FD summary
- `GET /api/reports/tax-certificate` - Generate tax certificate

## 🔒 Security Features

1. **Password Encryption**: BCrypt with salt + hashing
2. **JWT Tokens**: 5-minute expiration with refresh capability
3. **CORS Protection**: Configured for Angular frontend
4. **PII Masking**: Sensitive data masked before UI transmission
5. **Role-based Access**: USER/ADMIN role authorization
6. **Idle Timeout**: Automatic logout after 5 minutes of inactivity

## 🌐 Localization

The application supports Hindi localization for the login screen. To enable:

1. Hindi translations are available in `src/i18n/messages.hi.json`
2. The login component uses Angular i18n for Hindi text
3. Locale is configured in `main.ts` with `LOCALE_ID`

## 🚀 Deployment

### Backend Deployment
```bash
mvn clean package
java -jar target/fintech-login-module-0.0.1-SNAPSHOT.jar
```

### Frontend Deployment
```bash
ng build --prod
# Deploy dist/fintech-frontend/ to your web server
```

## 🧪 Testing

### Backend Testing
```bash
mvn test
```

### Frontend Testing
```bash
ng test
```

## 📝 Configuration

### Backend Configuration (`application.properties`)
- Database connection settings
- JWT secret and expiration
- CORS configuration
- Logging levels

### Frontend Configuration
- API base URL in services
- Idle timeout settings
- Encryption secret key

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `application.properties`

2. **CORS Error**
   - Ensure backend CORS is configured for `http://localhost:4200`

3. **JWT Token Expired**
   - Tokens expire after 5 minutes
   - Refresh token is automatically used

4. **Angular Build Error**
   - Run `npm install` to ensure all dependencies are installed
   - Check Node.js version compatibility

## 📄 License

This project is licensed under the MIT License.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📞 Support

For support and questions, please contact the development team.

---

**Built with ❤️ using Spring Boot + Angular**
