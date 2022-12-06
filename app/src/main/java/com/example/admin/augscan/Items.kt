package com.example.admin.augscan

class items {
    var itemNombreCliente: String? = null
        private set
    var itemUbicacion: String? = null
        private set
    var itemPedimento: String? = null
        private set
    var itemCodigoBarras: String? = null
        private set

    constructor() {}
    constructor(
        itemNombreCliente: String?,
        itemUbicacion: String?,
        itemPedimento: String?,
        itemCodigoBarras: String?
    ) {
        this.itemNombreCliente = itemNombreCliente
        this.itemUbicacion = itemUbicacion
        this.itemPedimento = itemPedimento
        this.itemCodigoBarras = itemCodigoBarras
    }
}