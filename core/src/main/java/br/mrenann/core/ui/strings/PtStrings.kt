package br.mrenann.core.ui.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.PT, default = true)
internal val PtStrings = Strings(
    homeTab = HomeTabStrings(
        title = "Inicio"
    ),
    favoriteTab = FavoriteTabStrings(
        title = "Favoritos"
    ),
    detailsScreen = DetailsScreenStrings(
        addToCart = "Adicionar ao carrinho",
        checkThisProduct = "Confira este produto",
        removedFromFavorite = "Produto removido dos favoritos",
        addedToFavorite = "Produto adicionado aos favoritos",
        addedToCart = "Adicionado ao carrinho",
        unit = "Unidade",
        productNotFound = "Produto não encontrado"
    ),
    profileTab = ProfileTabStrings(
        title = "Perfil",
        myAddresses = MyAddressesStrings(
            buttonProfile = "Meus endereços",
            addAddress = "Adicionar endereço",
            title = "Meus endereços",
            mainAddress = "Endereço principal",
            street = "Rua",
            number = "Número",
            district = "Bairro",
            city = "Cidade",
            state = "Estado",
            zipCode = "CEP",
            complement = "Complemento",
            saveAddress = "Salvar endereço",
            editAddress = "Editar endereço",
            removeAddress = "Remover endereço"
        ),
        myCards = MyCardsStrings(
            buttonProfile = "Meus cartões",
            title = "Cartões",
            exp = "Val",
            saveCard = "Salvar cartão",
            addCard = "Adicionar cartão",
            deleteCard = "Excluir cartão",
            sureToRemove = { "Tem certeza que deseja excluir o cartão com final $it?" },
            cancel = "Cancelar",
            delete = "Excluir",
            cardNumber = "Número do cartão",
            expireDate = "Validade (MM/AA)",
            cvv = "CVV",
            toRemove = "Para remover um cartão, arraste-o para cima",
            noCards = "Nenhum cartão cadastrado",
            card = "Cartão"
        ),
        orders = OrdersStrings(
            buttonProfile = "Pedidos"
        ),
        logout = "Sair"
    )
)