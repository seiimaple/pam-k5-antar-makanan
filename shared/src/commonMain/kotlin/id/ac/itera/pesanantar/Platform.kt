package id.ac.itera.pesanantar

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform