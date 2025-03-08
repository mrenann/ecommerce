package br.mrenann.core.ui.strings

data class Strings(
    val homeTab: HomeTabStrings,
    val favoriteTab: FavoriteTabStrings,
    val profileTab: ProfileTabStrings,
    val detailsScreen: DetailsScreenStrings,
    val cartScreen: CartScreenStrings,
    val authScreen: AuthScreenStrings,
)

data class AuthScreenStrings(
    val login: String,
)

data class CartScreenStrings(
    val title: String,
    val subtotal: String,
    val deliveryFee: String,
    val hasPromoCode: String,
    val promoAplied: String,
    val checkoutFor: (String) -> String,
    val preparingEverithingForYou: String,
    val deliveryMethod: String,
    val chooseThisAddress: String,
    val cartEmpty: String,
    val free: String,
    val discount: String,
    val dontHaveMainAddress: String,
    val pickUpAgency: String,
    val sendToMyAddress: String,
    val agency: String,
    val residence: String,
    val chooseDeliveryDate: String,
    val continueBtn: String,
    val delivery: String,
    val payment: String,
    val youWillPay: String,
    val recommended: String,
    val card: String,
    val confirmation: String,
    val confirmPurchase: String,
    val payWith: String,
    val daysAhedToDelivery: (Int, String) -> String,
    val oneDelivery: String,
    val full: String,
    val toPosition: (String) -> String,
    val pix: String,
    val immediateApproval: String,
    val products: (Int) -> String,
    val payPixToEnsure: (String) -> String,
    val copyCodeToPay: String,
    val pixGuideOne: String,
    val pixGuideTwo: String,
    val pixGuideThree: String,
    val pixCredited: String,
    val pixCode: String,
    val copyCode: String,
    val payWithCard: (String) -> String,
    val cardCharged: (String) -> String,
    val cardNumber: (String) -> String,
    val expNumber: (String) -> String,
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
    val discover: String,
    val search: String,
    val categories: String,
    val forYou: String,
    val more: String,
    val buyYourElectronics: String,
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
    val qtdProducts: (count: Int) -> String,
    val awaitingPayment: String,
    val paid: String,
    val onTheWay: String,
    val unknownStatus: String,
    val paymentCancelled: String,
    val delivered: String,
)