package br.mrenann.core.ui.strings

import br.mrenann.core.domain.model.OrderStatus
import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.PT, default = true)
internal val PtStrings = Strings(
    homeTab = HomeTabStrings(
        title = "Inicio",
        discover = "Descubra",
        search = "Pesquisar",
        categories = "Categorias",
        forYou = "Para você",
        more = "Mais",
        buyYourElectronics = "Compre seus eletronicos"

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
    cartScreen = CartScreenStrings(
        title = "Carrinho",
        subtotal = "Subtotal",
        deliveryFee = "Frete",
        hasPromoCode = "Cupom de promoção",
        promoAplied = "Promoção aplicada",
        checkoutFor = { "Comprar por $it" },
        preparingEverithingForYou = "Preparando tudo para você",
        deliveryMethod = "Método de entrega",
        chooseThisAddress = "Escolha este endereço",
        cartEmpty = "Carrinho vazio",
        free = "Gratis",
        discount = "Desconto",
        dontHaveMainAddress = "Você não tem endereço principal",
        pickUpAgency = "Retirar na Agência",
        sendToMyAddress = "Enviar para meu endereço",
        agency = "Agência",
        residence = "Casa",
        chooseDeliveryDate = "Escolha a data de entrega",
        continueBtn = "Continuar",
        delivery = "Entrega",
        payment = "Pagamento",
        youWillPay = "Você vai pagar",
        recommended = "Recomendado",
        card = "Cartão",
        confirmation = "Confirmar",
        confirmPurchase = "Confirmar compra",
        payWith = "Pagar com",
        daysAhedToDelivery = { daysAhead, dayOfWeek -> "$daysAhead dias ($dayOfWeek)" },
        oneDelivery = "1 entrega",
        full = "Full",
        toPosition = { "Para $it" },
        pix = "Pix",
        immediateApproval = "Aprovação imediata",
        products = { "Produtos ($it)" },
        payPixToEnsure = { "Pagar $it via Pix para garantir a sua compra" },
        copyCodeToPay = "Copie este código para pagar",
        pixGuideOne = "1. Acesse o seu aplicativo de Internet Banking ou de pagamento.",
        pixGuideTwo = "2. Escolha pagar via Pix.",
        pixGuideThree = "3. Cole o código abaixo:",
        pixCode = "00020126540014br.gov.bcb.pix0132pix.br@bankingecommerce.br123.gov",
        copyCode = "Copiar código",
        pixCredited = "💳 Pague e ele será creditado instantaneamente",
        payWithCard = { "Cartão **** $it" },
        cardCharged = { "Seu cartão será cobrado $it" },
        cardNumber = { "**** $it" },
        expNumber = { "Val $it" },
    ),
    authScreen = AuthScreenStrings(
        login = "Login"
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
            buttonProfile = "Pedidos",
            qtdProducts = { count ->
                val product = if (count == 1) "produto" else "produtos"
                "$count $product"
            },
            awaitingPayment = "Aguardando pagamento",
            paid = "Pago",
            onTheWay = "Em trânsito",
            unknownStatus = "Status desconhecido",
            paymentCancelled = "Pagamento cancelado",
            delivered = "Entregue",
            ordersStatusTitle = { status ->
                when (status) {
                    OrderStatus.AWAITING_PAYMENT -> "Aguardando pagamento"
                    OrderStatus.PAYMENT_CANCELLED -> "Pagamento cancelado"
                    OrderStatus.PAID -> "Pago"
                    OrderStatus.ON_THE_WAY -> "Em trânsito"
                    OrderStatus.DELIVERED -> "Entregue"
                    OrderStatus.UNKNOWN_STATUS -> "Status desconhecido"
                }
            },
            ordersStatusSubtitle = { status ->
                when (status) {
                    OrderStatus.AWAITING_PAYMENT -> "Seu pagamento ainda não foi recebido."
                    OrderStatus.PAYMENT_CANCELLED -> "O pagamento foi cancelado."
                    OrderStatus.PAID -> "Seu pedido foi pago e está sendo processado."
                    OrderStatus.ON_THE_WAY -> "Seu pedido está a caminho."
                    OrderStatus.DELIVERED -> "Seu pedido foi entregue com sucesso."
                    OrderStatus.UNKNOWN_STATUS -> "Não foi possível determinar o status do pedido."
                }
            }
        ),
        logout = "Sair"
    )
)