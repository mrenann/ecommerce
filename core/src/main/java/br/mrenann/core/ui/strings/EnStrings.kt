package br.mrenann.core.ui.strings

import br.mrenann.core.domain.model.OrderStatus
import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.EN)
internal val EnStrings = Strings(
    homeTab = HomeTabStrings(
        title = "Home",
        discover = "Discover",
        search = "Search",
        categories = "Categories",
        forYou = "For you",
        more = "More",
        buyYourElectronics = "Buy your electronics"
    ),
    favoriteTab = FavoriteTabStrings(
        title = "Favorites"
    ),
    detailsScreen = DetailsScreenStrings(
        addToCart = "Add to Cart",
        checkThisProduct = "Check this product",
        removedFromFavorite = "Product removed from favorites",
        addedToFavorite = "Product added to favorites",
        addedToCart = "Added to cart",
        unit = "Unit",
        productNotFound = "Product not found"
    ),
    authScreen = AuthScreenStrings(
        login = "Login"
    ),
    cartScreen = CartScreenStrings(
        title = "Cart",
        subtotal = "Subtotal",
        deliveryFee = "Delivery Fee",
        hasPromoCode = "Has Promo Code",
        promoAplied = "Promo Applied",
        checkoutFor = { "Checkout for $it" },
        preparingEverithingForYou = "Preparing everything for you...",
        deliveryMethod = "Delivery Method",
        chooseThisAddress = "Choose this address",
        cartEmpty = "Cart Empty",
        free = "Free",
        discount = "Discount",
        dontHaveMainAddress = "You don't have main address",
        pickUpAgency = "Pick up at Ecommerce Agency",
        sendToMyAddress = "Send to my address",
        agency = "Ecommerce Agency",
        residence = "Residence",
        chooseDeliveryDate = "Choose delivery date",
        continueBtn = "Continue",
        delivery = "Delivery",
        payment = "Payment",
        youWillPay = "You will pay",
        recommended = "Recommended",
        card = "Card",
        confirmation = "Confirmation",
        confirmPurchase = "Confirm purchase",
        payWith = "Pay with",
        daysAhedToDelivery = { daysAhead, dayOfWeek -> "$daysAhead days ($dayOfWeek)" },
        oneDelivery = "1 delivery",
        full = "Full",
        toPosition = { "To $it" },
        pix = "Pix",
        immediateApproval = "Immediate approval",
        products = { "Products ($it)" },
        payPixToEnsure = { "Pay $it via Pix to ensure your purchase" },
        copyCodeToPay = "Copy this code to pay",
        pixGuideOne = "1. Access your Internet Banking or payment app.",
        pixGuideTwo = "2. Choose to pay via Pix.",
        pixGuideThree = "3. Paste the following code:",
        pixCredited = "💳 Pay and it will be credited instantly",
        pixCode = "00020126540014br.gov.bcb.pix0132pix.br@bankingecommerce.br123.gov",
        copyCode = "Copy code",
        payWithCard = { "Card **** $it" },
        cardCharged = { "Your Card will be charged $it" },
        cardNumber = { "**** $it" },
        expNumber = { "Exp $it" },
    ),
    profileTab = ProfileTabStrings(
        title = "Profile",
        myAddresses = MyAddressesStrings(
            buttonProfile = "My addresses",
            addAddress = "Add address",
            title = "My addresses",
            mainAddress = "Main address",
            street = "Street",
            number = "Number",
            district = "District",
            city = "City",
            state = "State",
            zipCode = "Zip code",
            complement = "Complement",
            saveAddress = "Save address",
            editAddress = "Edit address",
            removeAddress = "Remove address"
        ),
        myCards = MyCardsStrings(
            buttonProfile = "My cards",
            title = "Cards",
            exp = "Exp",
            saveCard = "Save card",
            addCard = "Add card",
            toRemove = "Remove",
            deleteCard = "To remove a card, drag it up",
            cancel = "Cancel",
            delete = "Delete",
            cardNumber = "Card number",
            expireDate = "Expire date (MM/AA)",
            cvv = "CVV",
            sureToRemove = { "Are you sure you want to remove the card ending in $it?" },
            noCards = "No cards registered",
            card = "Card"
        ),
        orders = OrdersStrings(
            buttonProfile = "Orders",
            qtdProducts = { count ->
                val product = if (count == 1) "product" else "products"
                "$count $product"
            },
            awaitingPayment = "Awaiting payment",
            paid = "Paid",
            onTheWay = "On the way",
            unknownStatus = "Unknown status",
            paymentCancelled = "Payment cancelled",
            delivered = "Delivered",
            ordersStatusTitle = { status ->
                when (status) {
                    OrderStatus.AWAITING_PAYMENT -> "Awaiting Payment"
                    OrderStatus.PAYMENT_CANCELLED -> "Payment Cancelled"
                    OrderStatus.PAID -> "Paid"
                    OrderStatus.ON_THE_WAY -> "On the Way"
                    OrderStatus.DELIVERED -> "Delivered"
                    OrderStatus.UNKNOWN_STATUS -> "Unknown Status"
                }
            },
            ordersStatusSubtitle = { status ->
                when (status) {
                    OrderStatus.AWAITING_PAYMENT -> "Your payment has not been received yet."
                    OrderStatus.PAYMENT_CANCELLED -> "The payment has been cancelled."
                    OrderStatus.PAID -> "Your order has been paid and is being processed."
                    OrderStatus.ON_THE_WAY -> "Your order has been shipped."
                    OrderStatus.DELIVERED -> "Your order has been successfully delivered."
                    OrderStatus.UNKNOWN_STATUS -> "We couldn't determine the order status."
                }
            }
        ),
        logout = "Logout"
    )

)