package br.mrenann.core.ui.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.EN)
internal val EnStrings = Strings(
    homeTab = HomeTabStrings(
        title = "Home"
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
            buttonProfile = "Orders"
        ),
        logout = "Logout"
    )

)