#!/bin/bash

echo "🧪 Testing Fintech Backend APIs"
echo "================================"

BASE_URL="http://localhost:8080"

# Test 1: Registration
echo "1. Testing User Registration..."
REGISTER_RESPONSE=$(curl -s -X POST $BASE_URL/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "confirmPassword": "password123"
  }')

echo "Registration Response: $REGISTER_RESPONSE"

# Extract token from response
TOKEN=$(echo $REGISTER_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
echo "JWT Token: ${TOKEN:0:50}..."

# Test 2: Login
echo -e "\n2. Testing User Login..."
LOGIN_RESPONSE=$(curl -s -X POST $BASE_URL/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }')

echo "Login Response: $LOGIN_RESPONSE"

# Test 3: Protected API - Customer Profile
echo -e "\n3. Testing Customer Profile (with PII masking)..."
CUSTOMER_RESPONSE=$(curl -s -X GET $BASE_URL/api/customer/profile \
  -H "Authorization: Bearer $TOKEN")

echo "Customer Profile: $CUSTOMER_RESPONSE"

# Test 4: FD Calculator
echo -e "\n4. Testing FD Calculator..."
FD_RESPONSE=$(curl -s -X POST $BASE_URL/api/fd-calculator/calculate \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "principal": 100000,
    "tenure": 3,
    "interestRate": 7.0
  }')

echo "FD Calculation: $FD_RESPONSE"

# Test 5: Invalid Token
echo -e "\n5. Testing Invalid Token..."
INVALID_RESPONSE=$(curl -s -X GET $BASE_URL/api/customer/profile \
  -H "Authorization: Bearer invalid_token")

echo "Invalid Token Response: $INVALID_RESPONSE"

echo -e "\n✅ Backend Testing Complete!"

