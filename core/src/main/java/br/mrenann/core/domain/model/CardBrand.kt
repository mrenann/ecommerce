package br.mrenann.core.domain.model

enum class CardBrand(val regex: Regex = "".toRegex()) {
    VISA("^4[0-9]{6,}$".toRegex()),
    MASTERCARD("^5[1-5][0-9]{5,}$".toRegex()),
    AMEX("^3[47][0-9]{5,}$".toRegex()),
    ELO("^((((636368)|(438935)|(504175)|(451416)|(636297)|(5067)|(4576)|(4011)|(506699))[0-9]{0,10})|((509[0-9]{0,12})|((6504)|(6505)|(6506))[0-9]{0,10}))$".toRegex()),
    HIPERCARD("^(606282|3841)[0-9]{5,}$".toRegex()),
    DINERS("^3(?:0[0-5]|[68][0-9])[0-9]{4,}$".toRegex()),
    DISCOVER("^6(?:011|5[0-9]{2})[0-9]{3,}$".toRegex()),
    JCB("^(?:2131|1800|35\\d{3})\\d{11}$".toRegex()),
    UNKNOWN("".toRegex());

    companion object {
        fun fromCardNumber(cardNumber: String): CardBrand {
            val sanitized = cardNumber.replace("\\s".toRegex(), "")
            return entries.find { it.regex.matches(sanitized) } ?: UNKNOWN
        }
    }
}
