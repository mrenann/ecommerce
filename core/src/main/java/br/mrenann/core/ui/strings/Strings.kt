package br.mrenann.core.ui.strings

data class Strings(
    val homeTab: HomeTabStrings,
    val favoriteTab: FavoriteTabStrings,
    val profileTab: ProfileTabStrings,
    val detailsScreen: DetailsScreenStrings
)

data class DetailsScreenStrings(
    val addToCart: String,
    val checkThisProduct: String,
    val removedFromFavorite: String,
    val addedToFavorite: String,
    val addedToCart: String,
    val unit: String,
    val productNotFound: String,
)

data class HomeTabStrings(
    val title: String,
)

data class FavoriteTabStrings(
    val title: String,
)

data class ProfileTabStrings(
    val title: String,
    val myAddresses: MyAddressesStrings,
    val myCards: MyCardsStrings,
    val orders: OrdersStrings,
    val logout: String
)

data class MyAddressesStrings(
    val buttonProfile: String,
    val addAddress: String,
    val editAddress: String,
    val removeAddress: String,
    val title: String,
    val mainAddress: String,
    val street: String,
    val number: String,
    val district: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val complement: String,
    val saveAddress: String,
)

data class MyCardsStrings(
    val buttonProfile: String,
    val title: String,
    val card: String,
    val exp: String,
    val saveCard: String,
    val addCard: String,
    val toRemove: String,
    val deleteCard: String,
    val sureToRemove: (String) -> String,
    val noCards: String,
    val cancel: String,
    val delete: String,
    val cardNumber: String,
    val expireDate: String,
    val cvv: String,
)

data class OrdersStrings(
    val buttonProfile: String,
)