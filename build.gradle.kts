plugins {
    id("java")
    id("application")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

group = "br.com.dio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {

    mainClass = "br.com.dio.Main"

    dependencies {

    // ✅ CORRETO: Apenas uma dependência para o Lombok no compileOnly
    compileOnly("org.projectlombok:lombok:1.18.36")

    // ✅ OBRIGATÓRIO: A mesma dependência para o processador de anotações
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    // Dependências de Teste
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}}



