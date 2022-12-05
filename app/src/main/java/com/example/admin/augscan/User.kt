package com.example.admin.augscan

class User {
    var Nombre: String? = null
    var email: String? = null

    constructor() {}
    constructor(name: String?, email: String?) {
        Nombre = name
        this.email = email
    }
}