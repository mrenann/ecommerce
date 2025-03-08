# Modular Android E-Commerce App

This is a modular Android e-commerce app built with Kotlin, Jetpack Compose, and Firebase. It provides a seamless user experience for browsing products, adding favorites, making purchases, and securely managing payment and shipping details. The app utilizes a modular architecture to ensure scalability, maintainability, and optimal performance.

## Flow

https://github.com/user-attachments/assets/24c3162f-8341-4c14-940b-a912fe87624a

## Features

- **Product Catalog**: Browse a wide range of products and categories. The product data is fetched from a fake API (FakeAPI Platzi).
- **Favorites**: Users can save their favorite products to access them later. Data is stored securely in Firebase Firestore.
- **Cart & Purchases**: Complete purchase flow with simulated orders stored in Firestore.
- **Addresses & Payment Methods**: Secure storage of user payment and shipping information in Firebase Firestore.
- **Discount Coupons**: A coupon system for personalized promotions, with data stored in Firestore.
- **Authentication**: Secure user login and registration via Firebase Auth.
- **Local Persistence**: Room Database is used to cache products and favorites for offline access.
- **Modern UI**: A smooth, responsive, and modern UI powered by Jetpack Compose for a native Android experience.

## Tech Stack

- **Kotlin**: The primary programming language for the app.
- **Jetpack Compose**: For building the UI with a modern, declarative approach.
- **Voyager**: For navigation and seamless handling of app screens.
- **Lyricist**: For internationalization, supporting multiple languages.
- **Koin**: For dependency injection, making the app modular and easy to maintain.
- **Retrofit + OkHttp**: For handling API requests and responses.
- **Firebase**:
  - **Firestore**: For secure data storage (products, favorites, orders, user details).
  - **Auth**: For user authentication (login/registration).
- **Room Database**: For local caching of products and favorites for offline access.

## Architecture

The app follows a **modular architecture** to ensure scalability and maintainability:
- **Domain Layer**: Contains business logic and models.
- **Data Layer**: Responsible for handling data sources such as APIs and databases.
- **Presentation Layer**: Manages UI components using Jetpack Compose and includes ViewModels.
- **Navigation**: Handled using **Voyager** for clear and consistent navigation between screens.

## Usage

- Browse Products: Users can view products, filter them by category, and check out product details.
- Add to Favorites: Users can add products to their favorites, which are stored in Firestore for later viewing.
- Add to Cart and Purchase: Users can add products to their cart, apply discount coupons, and go through the purchase process.
- Authentication: Users can log in or register with Firebase Authentication to personalize their shopping experience.
